package com.gumo.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class FileHttpTest {
    public static void main(String[] args) {
        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart("base64");

//            byte[] faceBytes = Base64Utils.decodeFromString(isolatedWorkPersonVo.getFace());
//            InputStream inputStream = new ByteArrayInputStream(faceBytes);
//            MultipartFile faceFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

        Map<String,String> headerMap = new HashMap();
        headerMap.put("token", "token111");
        Map<String, String> otherParams = new HashMap();
        otherParams.put("type","0");
//        String result = HttpUtil.postResultMultipartFile("url", multipartFile, headerMap, otherParams);
//        log.info("IsolatedWorkPersonServiceImpl_uploadImageStruct, dataBankJson = {}, result = {}", JSON.toJSONString(dataFaceJson), result);
//        JSONObject response = JSONObject.parseObject(result);
    }
}
