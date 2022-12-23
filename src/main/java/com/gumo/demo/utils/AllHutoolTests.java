package com.gumo.demo.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.webservice.SoapClient;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gumo.demo.controller.SwaggerController;
import com.gumo.demo.entity.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Hutool是一个小而全的Java工具类库  列举了平时常用的16个工具类样例
 * 官网地址: https://www.hutool.cn/
 * @author gumo
 */
@Slf4j
public class AllHutoolTests {

    /**
     * Convert 类型转换工具类，用于各种类型数据的转换
     */
    public static void convert(){
        //转换为字符串
        int a = 1;
        String aStr = Convert.toStr(a);
        //转换为指定类型数组
        String[] b = {"1", "2", "3", "4"};
        Integer[] bArr = Convert.toIntArray(b);
        //转换为日期对象
        String dateStr = "2017-05-06";
        Date date = Convert.toDate(dateStr);
        //转换为列表
        String[] strArr = {"a", "b", "c", "d"};
        List<String> strList = Convert.toList(String.class, strArr);
    }

    /**
     * DateUtil 日期时间工具类，定义了一些常用的日期时间操作方法。
     * JDK自带的Date和Calendar对象真心不好用，有了它操作日期时间就简单多了！
     */
    public static void dateUtil(){
        //Date、long、Calendar之间的相互转换
        //当前时间
        Date date = DateUtil.date();
        //Calendar转Date
        date = DateUtil.date(Calendar.getInstance());
        //时间戳转Date
        date = DateUtil.date(System.currentTimeMillis());
        //自动识别格式转换
        String dateStr = "2017-03-01";
        date = DateUtil.parse(dateStr);
        //自定义格式化转换
        date = DateUtil.parse(dateStr, "yyyy-MM-dd");
        //格式化输出日期
        String format = DateUtil.format(date, "yyyy-MM-dd");
        //获得年的部分
        int year = DateUtil.year(date);
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        //获取某天的开始、结束时间
        Date beginOfDay = DateUtil.beginOfDay(date);
        Date endOfDay = DateUtil.endOfDay(date);
        //计算偏移后的日期时间
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        //计算日期时间之间的偏移量
        long betweenDay = DateUtil.between(date, newDate, DateUnit.DAY);
    }

    /**
     * JSON解析工具类，可用于对象与JSON之间的互相转化。
     */
    public static void jsonUtil(){
        User brand = new User();
        brand.setId(1);
        brand.setUserName("gumo");
        brand.setPassWord("123456");
        //对象转化为JSON字符串
        String jsonStr = JSONUtil.parse(brand).toString();
        log.info("jsonUtil parse:{}", jsonStr);
        //JSON字符串转化为对象
        User brandBean = JSONUtil.toBean(jsonStr, User.class);
        log.info("jsonUtil toBean:{}", brandBean);
        List<User> brandList = new ArrayList<>();
        brandList.add(brand);
        String jsonListStr = JSONUtil.parse(brandList).toString();
        //JSON字符串转化为列表
        brandList = JSONUtil.toList(new JSONArray(jsonListStr), User.class);
        log.info("jsonUtil toList:{}", brandList);

        //JsontoXml: Json转Xml
        JSONObject put = JSONUtil.createObj()
                .set("aaa", "你好")
                .set("键2", "test");
        // <aaa>你好</aaa><键2>test</键2>
        String xml = JSONUtil.toXmlStr(put);
        System.out.println("xml:" + xml);

        //XmltoJson: xml转Json
        String xmlStr = "<sfzh>123</sfzh><sfz>456</sfz><name>aa</name><gender>1</gender>";
        JSONObject json = JSONUtil.parseFromXml(xmlStr);
        json.get("sfzh");
        json.get("name");
        System.out.println("json:" + json );
    }

    /**
     * StrUtil: 字符串工具类，定义了一些常用的字符串操作方法。StrUtil比StringUtil名称更短，用起来也更方便！
     */
    public static void strUtil(){
        //判断是否为空字符串
        String str = "test";
        StrUtil.isEmpty(str);
        StrUtil.isNotEmpty(str);
        //去除字符串的前后缀
        StrUtil.removeSuffix("a.jpg", ".jpg");
        StrUtil.removePrefix("a.jpg", "a.");
        //格式化字符串
        String template = "这只是个占位符:{}";
        String str2 = StrUtil.format(template, "我是占位符");
        log.info("/strUtil format:{}", str2);
    }

    /**
     * 字段验证器，可以对不同格式的字符串进行验证，比如邮箱、手机号、IP等格式。
     */
    public static void validator() {
        //判断是否为邮箱地址
        boolean result = Validator.isEmail("macro@qq.com");
        log.info("Validator isEmail:{}", result);
        //判断是否为手机号码
        result = Validator.isMobile("18911111111");
        log.info("Validator isMobile:{}", result);
        //判断是否为IPV4地址
        result = Validator.isIpv4("192.168.3.101");
        log.info("Validator isIpv4:{}", result);
        //判断是否为汉字
        result = Validator.isChinese("你好");
        log.info("Validator isChinese:{}", result);
        //判断是否为身份证号码（18位中国）
        result = Validator.isCitizenId("123456");
        log.info("Validator isCitizenId:{}", result);
        //判断是否为URL
        result = Validator.isUrl("http://www.baidu.com");
        log.info("Validator isUrl:{}", result);
        //判断是否为生日
        result = Validator.isBirthday("2020-02-01");
        log.info("Validator isBirthday:{}", result);
    }

    /**
     * 摘要算法工具类，支持MD5、SHA-256、Bcrypt等算法。
     */
    public static void digestUtil() {
        String password = "123456";
        //计算MD5摘要值，并转为16进制字符串
        String result = DigestUtil.md5Hex(password);
        log.info("DigestUtil md5Hex:{}", result);
        //计算SHA-256摘要值，并转为16进制字符串
        result = DigestUtil.sha256Hex(password);
        log.info("DigestUtil sha256Hex:{}", result);
        //生成Bcrypt加密后的密文，并校验
        String hashPwd = DigestUtil.bcrypt(password);
        boolean check = DigestUtil.bcryptCheck(password,hashPwd);
        log.info("DigestUtil bcryptCheck:{}", check);
    }

    /**
     * ClassPath单一资源访问类，可以获取classPath下的文件，在Tomcat等容器下，classPath一般是WEB-INF/classes。
     */
    public static void classPathResource() throws IOException {
        //获取定义在src/main/resources文件夹中的配置文件
        ClassPathResource resource = new ClassPathResource("generator.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        log.info("/classPath:{}", properties);
    }

    /**
     * Java反射工具类，可用于反射获取类的方法及创建对象。
     */
    public static void reflectUtil(){
        //获取某个类的所有方法
        Method[] methods = ReflectUtil.getMethods(User.class);
        //获取某个类的指定方法
        Method method = ReflectUtil.getMethod(User.class, "getId");
        //使用反射来创建对象
        User pmsBrand = ReflectUtil.newInstance(User.class);
        //反射执行对象的方法
        ReflectUtil.invoke(pmsBrand, "setId", 1);
    }

    /**
     * 数字处理工具类，可用于各种类型数字的加减乘除操作及类型判断。
     */
    public static void numberUtil(){
        double n1 = 1.234;
        double n2 = 1.234;
        double result;
        //对float、double、BigDecimal做加减乘除操作
        result = NumberUtil.add(n1, n2);
        result = NumberUtil.sub(n1, n2);
        result = NumberUtil.mul(n1, n2);
        result = NumberUtil.div(n1, n2);
        //保留两位小数
        BigDecimal roundNum = NumberUtil.round(n1, 2);
        String n3 = "1.234";
        //判断是否为数字、整数、浮点数
        NumberUtil.isNumber(n3);
        NumberUtil.isInteger(n3);
        NumberUtil.isDouble(n3);
    }

    /**
     * JavaBean工具类，可用于Map与JavaBean对象的互相转换以及对象属性的拷贝。
     */
    public static void beanUtil(){
        User brand = new User();
        brand.setId(1);
        brand.setUserName("gumo");
        brand.setPassWord("123456");
        //Bean转Map
        Map<String, Object> map = BeanUtil.beanToMap(brand);
        log.info("beanUtil bean to map:{}", map);
        //Map转Bean
        User mapBrand = BeanUtil.mapToBean(map, User.class, false);
        log.info("beanUtil map to bean:{}", mapBrand);
        //Bean属性拷贝
        User copyBrand = new User();
        BeanUtil.copyProperties(brand, copyBrand);
        log.info("beanUtil copy properties:{}", copyBrand);
    }

    /**
     * 集合操作的工具类，定义了一些常用的集合操作。
     */
    public static void collUtil(){
        //数组转换为列表
        String[] array = new String[]{"a", "b", "c", "d", "e"};
        List<String> list = CollUtil.newArrayList(array);
        //join：数组转字符串时添加连接符号
        String joinStr = CollUtil.join(list, ",");
        log.info("collUtil join:{}", joinStr);
        //将以连接符号分隔的字符串再转换为列表
        List<String> splitList = StrUtil.split(joinStr, ',');
        log.info("collUtil split:{}", splitList);
        //创建新的Map、Set、List
        HashSet<Object> newHashSet = CollUtil.newHashSet();
        ArrayList<Object> newList = CollUtil.newArrayList();
        //判断列表是否为空
        CollUtil.isEmpty(list);
    }

    /**
     * Map操作工具类，可用于创建Map对象及判断Map是否为空。
     */
    public static void mapUtil(){
        //将多个键值对加入到Map中
        Map<Object, Object> map = MapUtil.of(new String[][]{
                {"key1", "value1"},
                {"key2", "value2"},
                {"key3", "value3"}
        });
        //判断Map是否为空
        MapUtil.isEmpty(map);
        MapUtil.isNotEmpty(map);
    }

    /**
     * 注解工具类，可用于获取注解与注解中指定的值。
     */
    public static void annotationUtil(){
        //获取指定类、方法、字段、构造器上的注解列表
        Annotation[] annotationList = AnnotationUtil.getAnnotations(SwaggerController.class, false);
        log.info("annotationUtil annotations:{}", annotationList);
        //获取指定类型注解
        Api api = AnnotationUtil.getAnnotation(SwaggerController.class, Api.class);
        log.info("annotationUtil api value:{}", api.description());
        //获取指定类型注解的值
        Object annotationValue = AnnotationUtil.getAnnotationValue(SwaggerController.class, RequestMapping.class);
    }

    /**
     * 加密解密工具类，可用于MD5加密。
     */
    public static void secureUtil(){
        //MD5加密
        String str = "123456";
        String md5Str = SecureUtil.md5(str);
        log.info("secureUtil md5:{}", md5Str);
    }

    /**
     * Http请求工具类，可以发起GET/POST等请求。
     */
    public void httpUtil(){
        String response = HttpUtil.get("http://localhost:8080/hutool/covert");
        log.info("HttpUtil get:{}", response);
    }

    public static void main(String[] args) {

        JSONObject put = JSONUtil.createObj()
                .set("aaa", "你好")
                .set("键2", "test");

        // <aaa>你好</aaa><键2>test</键2>
        String xml = JSONUtil.toXmlStr(put);
        System.out.println("xml:" + xml);

        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<QueryExpression xmlns=\"http://www.powerrich.com.cn/QueryResObj\">\n" +
                "<Conditions>\n" +
                "<Condition>\n" +
                "<ResIndexName>WBJID</ResIndexName>\n" +
                "<Operation>=</Operation>\n" +
                "<RightValue>KJ3a76HA</RightValue>\n" +
                "</Condition>\n" +
                "<Condition>\n" +
                "<ResIndexName>QQXX</ResIndexName>\n" +
                "<Operation>=</Operation>\n" +
                "<RightValue>\n" +
                "572b1ef58b3ffd41b4624be75ea31cfb39237f18ff8c37b0f29ff780322ff704577c620313d17245\n" +
                "5282443eeb0d9cb5287820062de1ae3e3f73dcd92739ad43052655d50ef696f52407b5d6a3dadd1d\n" +
                "f11729dd6d2a67f963c554249698f2165d244e9e01328fc530545e3f0816adbf9c6c76d0b233422a\n" +
                "7a887872d12b84e5f4d75cd013bfc666b3103310e495f7b4e4c08263ff0e22ab958b5e9463964c3a\n" +
                "4d38db9cf464682a0409e065353f50a84e7a51ad2e048d380d72cabc3ffbf813\n" +
                "</RightValue>\n" +
                "</Condition>\n" +
                "</Conditions>\n" +
                "</QueryExpression>";
        JSONObject json = JSONUtil.parseFromXml(xmlStr);
        json.get("sfzh");
        json.get("name");
        System.out.println("json:" + json );

        // 新建客户端
        SoapClient client = SoapClient.create("http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx")
                // 设置要请求的方法，此接口方法前缀为web，传入对应的命名空间
                .setMethod("web:getCountryCityByIp", "http://WebXml.com.cn/")
                // 设置参数，此处自动添加方法的前缀：web
                .setParam("theIpAddress", "218.21.240.106");

        // 发送请求，参数true表示返回一个格式化后的XML内容
        // 返回内容为XML字符串，可以配合XmlUtil解析这个响应
        log.info(client.send(true));
    }
}
