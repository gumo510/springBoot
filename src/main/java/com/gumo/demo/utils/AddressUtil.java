package com.gumo.demo.utils;


import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressUtil {
    /**
     * 解析地址
     *
     * @param address
     * @return
     */
    public static Map<String, String> addressResolution(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        String regex = "(?<city>.*?市)(?<area>.*?区|.*?县)?(?<street>.*?街道|.*?镇)?(?<community>.*?社区)?(?<home>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String city = null, area = null, street = null, community = null, home = null;
        List<Map<String, String>> table = new ArrayList<Map<String, String>>();
        Map<String, String> row = null;
        while (m.find()) {
            row = new LinkedHashMap<String, String>();
            city = m.group("city");
            row.put("city", city == null ? "暂无" : city.trim());
            area = m.group("area");
            row.put("area", area == null ? "暂无" : area.trim());
            street = m.group("street");
            row.put("street", street == null ? "暂无" : street.trim());
            community = m.group("community");
            row.put("community", community == null ? "暂无" : community.trim());
            home = m.group("home");
            row.put("home", home == null ? "暂无" : home.trim());
            table.add(row);
        }
        if (table.size() > 0) {
            return table.get(0);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(addressResolution("深圳市大鹏区葵涌街道溪涌社区欧新三巷九号一楼"));
        System.out.println(addressResolution("深圳市罗湖区翠竹街道翠竹社区水贝20C2"));
        System.out.println(addressResolution("深圳市罗湖区翠竹街道翠竹社区水贝市20C2"));
        System.out.println(addressResolution("深圳市罗湖区翠竹街道翠竹社区水贝市20C2"));
        System.out.println(addressResolution("深圳市宝安区新安街道建安社区广东省深圳市宝安区新安街道建安社区建安一路292号汉宝大厦403"));
        System.out.println(addressResolution("深圳市龙华区福城街道桔榶社区龙华区福城街道桔榶社区百丽名苑8栋2601"));
        System.out.println(addressResolution("深圳市龙岗区平湖街道-"));
        System.out.println(addressResolution("惠州市惠阳区大亚湾澳头街道太东天地花园"));
        System.out.println(addressResolution("惠州市惠城区汝湖镇惠民大厦6号C栋"));
        System.out.println(addressResolution("常德市汉寿县百禄桥镇新华村新塘组7号"));
        System.out.println(addressResolution(""));
        System.out.println(addressResolution(null));
    }
}
