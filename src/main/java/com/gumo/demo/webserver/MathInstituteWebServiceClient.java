package com.gumo.demo.webserver;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author
 *
 */
@Slf4j
@Component
public class MathInstituteWebServiceClient {

    private Client client;

    private String lastGuid;

    @Value("${math.institute.wsdl:http://110.88.153.177:901/Convergence/webservice/ConvergenceService?wsdl}")
    private String wsdl;

    @Value("${math.institute.wsdl.service.target:http://110.88.153.177:901/Convergence/webservice/ConvergenceService}")
    private String wsdlServiceTarget;

    @Value("${math.institute.username:Ytlf_sun}")
    private String userName;

    @Value("${math.institute.passWord:Ytlf@2022}")
    private String passWord;

    @Value("${math.institute.retry.limit:3}")
    private Integer retryLimit;

    @PostConstruct
    public void init() {
        initClinet();
        refreshLogin();
    }

    private void initClinet() {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        //wsdl 为实际接口地址
        org.apache.cxf.endpoint.Client client = dcf.createClient(wsdl);
        client.getConduit().getTarget().getAddress().setValue(wsdlServiceTarget);
        this.client =  client;
    }

    private synchronized void refreshLogin() {
        String guid = loginByAccount(userName, passWord);
        log.info("refreshLogin time: {}, new value: {}", LocalDateTime.now().toString(), guid);
    }

    public String loginByAccount(String userName, String passWord) {
        try {
            Object[] guidObjects = client.invoke("LoginByAccount", userName, passWord);
            lastGuid = guidObjects[0].toString();
            return lastGuid;
        } catch (Exception e) {
            log.error("loginByAccount error: {}", e);
            e.printStackTrace();
        }
        return "";
    }

    public void pushRow(String catalogId, List<? extends Row> rows) {
        Row[] newRows = rows.toArray(new Row[ rows.size()]);
        doRetryPushRow(catalogId, MathInstituteXmlUtil.toXml(newRows));
    }

    public void pushRow(String catalogId, Row row) {
        doRetryPushRow(catalogId, MathInstituteXmlUtil.toXml((new Row[] {row})));
    }

    private void doRetryPushRow(String catalogId, String xmlStr) {
        int retry = 0;
        Boolean success = pushXml(catalogId, xmlStr);
        while (!success &&  retry < retryLimit) {
            retry ++;
            refreshLogin();
            success = pushXml(catalogId,xmlStr);
            log.info("push xml data retry {} times, lastGuid: {}", retry, lastGuid);
        }
    }

    private Boolean pushXml(String catalogId, String xmlStr) {
        try {
            Object[] objects = client.invoke("pushXml", lastGuid, catalogId, xmlStr);
            String resposeXml = objects[0].toString();
            Response response = MathInstituteXmlUtil.fromXml(resposeXml, Response.class);
            return Boolean.valueOf(response.getFlag());
        } catch (Exception e) {
            log.error("pushXml error: {}", e);
            e.printStackTrace();
        }
        return false;
    }
}
