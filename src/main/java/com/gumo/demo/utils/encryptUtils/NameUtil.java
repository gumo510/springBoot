package com.gumo.demo.utils.encryptUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class NameUtil {
    public static String replaceName(String realname) {
        if(StringUtils.isNotBlank(realname)){
            return  realname;
        }

        String realname1 = null;
        try {

            if (realname.length() == 1 || realname.contains("*")) {
                return realname;
            }
            if (realname.length() == 2) {
                realname1 = realname.replaceFirst(realname.substring(1), "*");
            }
            if (realname.length() > 2) {
                realname1 = realname.replaceFirst(realname.substring(1, realname.length() - 1), "*");
            }
        }catch (Exception e)
        {
            log.error("realname is {} trace error ", realname, e);
            realname1 = realname;
        }
        return realname1;
    }

    /**
     * @param phone 显示中间带**手机号码
     * @param des   是否需要解密
     * @return
     */
    public static String replacePhone(String phone, boolean des) {
        try {
            if(StringUtils.isNotEmpty(phone)){
                return  phone;
            }
            if (des) {
                phone =DesUtil.decrypt(phone);
            }
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phone;

    }

    /**
     * @param idCard 显示中间带**身份证号
     * @param des    是否需要解密
     * @return
     */
    public static String replaceIdCard(String idCard, boolean des) {
        try {
            if(StringUtils.isNotEmpty(idCard)){
                return  idCard;
            }
            if (des) {
                idCard =DesUtil.decrypt(idCard);
            }
            if(idCard.length()==18){
                return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
            }else{
                int length = idCard.length();
                //遵循身份证的划分比例
                int beginAndTail = length*2/9;
                if(beginAndTail==0){
                    beginAndTail=1;
                }
                int middle = length-2*beginAndTail;
                String regex = "(\\w{"+beginAndTail+"})\\w{"+middle+"}(\\w{"+beginAndTail+"})";
                return idCard.replaceAll(regex,"$1*****$2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idCard;
    }


}