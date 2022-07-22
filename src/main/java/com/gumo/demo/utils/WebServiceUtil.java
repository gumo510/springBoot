package com.gumo.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

@Slf4j
public class WebServiceUtil {

    /**
     * testwebservice接口测试方法
     *
     * @param userName  用户名
     * @param passWord  密码
     * @param catalogId 目录id
     * @param xmlStr    xml格式参数
     * @param wsdl      接口wsdl
     */
    public static void testWebService(String userName, String passWord, String catalogId, String xmlStr, String wsdl) {
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            //wsdl 为实际接口地址
            org.apache.cxf.endpoint.Client client = dcf.createClient(wsdl);
            client.getConduit().getTarget().getAddress().setValue("http://110.88.153.177:901/Convergence/webservice/ConvergenceService");
            //LoginByAccount 为用户身份验证接口方法名称，userName为用户名，passWord为密码
            Object[] guidObjects = client.invoke("LoginByAccount", userName, passWord);
            System.out.println("获取guid：" + guidObjects[0].toString());
            //pushXml 为政务数据推送接口方法名称，参数个数按照接口定义进行传参，返回一个Object数组
            Object[] objects = client.invoke("pushXml", guidObjects[0].toString(), catalogId, xmlStr);
            //输出调用结果，Object数组第一条数据为返回结果
            System.out.println("调用结果:" + objects[0].toString());
        } catch (Exception e) {
            log.error("晋江政务数据推送异常：", e);
        }
    }

    public static String testLoginByAccount(String userName, String passWord, String wsdl) {
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            //wsdl 为实际接口地址
            org.apache.cxf.endpoint.Client client = dcf.createClient(wsdl);
            //LoginByAccount 为用户身份验证接口方法名称，userName为用户名，passWord为密码
            Object[] guidObjects = client.invoke("LoginByAccount", userName, passWord);
            log.info("testLoginByAccount_guid：", guidObjects[0]);
            return guidObjects[0].toString();
        } catch (Exception e) {
            log.error("晋江政务数据推送身份验证异常：", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String userName = "Ytlf_sun";
        String passWord = "Ytlf@2022";
        String catalogId = "WEB11";
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><table><row type=\"add\"><id name=\"主键\" isattachment=\"false\">1</id><name name=\"姓名\" isattachment=\"false\">王天文</name><cid_type name=\"证件类型\" isattachment=\"false\">0</cid_type><cid name=\"证件号码\" isattachment=\"false\">430723199304158395</cid><phone name=\"电话号码\" isattachment=\"false\">18825642541</phone><sex name=\"性别\" isattachment=\"false\">男</sex><face_url name=\"档案图片\" isattachment=\"false\">[/test/1.png</face_url><create_time name=\"创建时间\" isattachment=\"false\">2022-07-18 16:17:00</create_time><update_time name=\"更新时间\" isattachment=\"false\">2022-07-18 16:17:00</update_time></row></table>";
        String wsdl = "http://110.88.153.177:901/Convergence/webservice/ConvergenceService?wsdl";
        testWebService(userName, passWord, catalogId, xmlStr, wsdl);
    }
}