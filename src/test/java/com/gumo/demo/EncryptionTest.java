package com.gumo.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.filter.ValueNodes;
import org.elasticsearch.common.collect.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

@SpringBootTest
public class EncryptionTest {

    /**
     * 非对称加密
     * @throws Exception
     */
    @Test
    public void RSATest() throws Exception {
        // 生成RSA密钥对
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        keygen.initialize(2048, random);
        KeyPair keyPair = keygen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 将公钥发送给客户端
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Public key: " + publicKeyString);

        // 客户端输入密码，并使用公钥进行加密
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter password: ");
        String password = reader.readLine();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedPassword = cipher.doFinal(password.getBytes());
        String encryptedPasswordString = Base64.getEncoder().encodeToString(encryptedPassword);
        System.out.println("Encrypted password: " + encryptedPasswordString);

        // 服务器使用私钥解密密码，并校验是否正确
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedPassword = cipher.doFinal(Base64.getDecoder().decode(encryptedPasswordString));
        String decryptedPasswordString = new String(decryptedPassword);
        System.out.println("Decrypted password: " + decryptedPasswordString);

        // 校验密码是否正确
        if (password.equals(decryptedPasswordString)) {
            System.out.println("Password is correct!");
        } else {
            System.out.println("Password is incorrect!");
        }
    }

    public static void main(String[] args) throws Exception {
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKtNXQet9kUZKt3SwBNcC0ZK3exeAwrFXwy6MwWI/ws0jDtx08LGh78ydoQ5VMw8MudZ5mnzXQtkFr1Zmj0M5z9bz8f5Td1S+zjPFuJSvHa54IajgQD+nPMjl7d9IgBJrYFvMdOojs6voxibzGv/zPMFSZJZmtdoLsYg6gnV2QTpAgMBAAECgYAHu/t6UElxPzynPU+80AgWSWURugDrKJQtD+8jzdauG9ZFDNOh1LLmWBHv0GLawdEOjwmPORS3YfKA5B3Jqd568+ts0a45fXIfzcJtxmUwMSVeEwmLVIKEFWE14xSEKYt/iVH08Qgdi5WDSMvmlQmOonV6DvFblE7U0Uro3w22/QJBANzTtoEpitkcxozrfWNozTvfFrxJFUgVCp9zuxjaBgLPj/MRvvEAOUxYox6gt70pbNsDAgMVrWpg4vonUAM8gkMCQQDGlkMIno3atqCkFc49Xf4/qnhCnK4FyugXtgBYpzXhf/ruSIwyOzgQOxIOPdb1XkDdWoETLMlLuCk36hOK7PdjAkB1Ll+FcN7+UTfHZdtW2jE2WS6+YWXKp2fIr2gUSvWvnic8DCmvx7egHNUKlqyJ06axHYp+yo0IiztHFX9vCDfzAkEApgD2CD6CVlJT9tZDkQkBgDcAUK6oyi7T1P5PS8x7PgW9vXLnvxXBx8+ZSE5pYrNnhaIiQ+UsYB+SVStXKsrsAQJATyWKNa/Rd55Q7zFV35XWNoTSzPnu3nvb+9rF8m22NKXwyRlPVt8CX8+QaTOFmdbkN8n5NHIxR0rbLEnDupp0ow==";
        String str = "{\"abilityCode\":\"kfpt_syfw\",\"app_id\":\"989841413043769345\",\"biz_content\":{\"cid\":\"880075500000002\",\"p\":{\"requestType\":\"DATA\",\"dataItems\":[{\"operateType\":\"READ\",\"attributes\":{\"visitorName\":\"张三\",\"carNo\":\"粤-12345\",\"visitorTel\":\"13800138000\"}}],\"attributes\":{\"areaCode\":\"0020200529\",\"visitorType\":0,\"visitorContent\":\"测试访客邀约\",\"timeDesc\":\"{\\\"sd\\\":\\\"2023-08-18 18:27:59\\\",\\\"ed\\\":\\\"2023-08-18 18:27:59\\\"}\",\"parkCode\":\"0020200529\",\"personCode\":\"64983832\",\"businesserCode\":\"880075500000002\"},\"serviceId\":\"3c.visitor.invite\"},\"v\":\"2\"},\"charset\":\"utf-8\",\"format\":\"form\",\"method\":\"3c.visitor.invite\",\"projectCode\":\"0020200529\",\"sign\":\"ThlfeSICmHwlez6h3iiFPwEUS/+IBDB8wYrbpG6lPllgFSkYrAdTQDs+Fu6NGQuQq4PN3GDDxm9X4ev+k/Rw693RtOSmC4FhXlMZITRn2odp013WSHf9cED5gDflUAyHOM1LeK4XvO4dAWnI/8pOlpMYE7IRnXeRgy9e0j6if3c=\",\"sign_type\":\"md5\",\"timestamp\":1692354569969}";
        JSONObject body = JSONObject.parseObject(str, Feature.OrderedField);
        String sign = buildSignJson(body, privateKey);

        System.out.println(sign);

    }



    public static String buildSignJson(JSONObject jsonObject, String secret) {
        // 1. 筛选并排序
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        for (String key : jsonObject.keySet()) {
            sortedMap.put(key, jsonObject.get(key));
        }

        // 2. 拼接
        String preparedString = sortedMap.entrySet().stream()
                .filter(e -> !e.getKey().equals("sign") && !(e.getValue() instanceof byte[]))
                .map(e -> e.getKey() + "=" + (e.getValue() == null ? "null" : e.getValue()))
                .collect(Collectors.joining("&"));

        // 3. 使用MD5withRSA算法签名
        try {
            byte[] data = preparedString.getBytes(StandardCharsets.UTF_8);
            return sign(data, secret);
        } catch (Exception e) {
            throw new RuntimeException("签名生成出错: ", e);
        }
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       数据
     * @param privateKey 私钥(BASE64编码)
     * @return String
     * @throws Exception 异常
     */
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static String sign(byte[] data, String privateKey) throws Exception {
        // 根据Base64编码的私钥生成PrivateKey对象
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.getEncoder().encodeToString(signature.sign());
    }
}
