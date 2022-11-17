package com.gumo.demo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 */
public class LoadJarTest {

    @Test
    public void test() {

//        try {
//            // 定义第三方jar文件的路径，此处是相对路径，当然也可以写绝对路径，也可以是远程服务器路径。
//            String path = "/adapterClassLoader/lib/adapter-0.0.1-SNAPSHOT.jar";
//            JarFile jarFile = new JarFile(new File(path));
//            //获取这个jar的URL 固定写法
//            URL url = new URL("file:" + path);
//            //定义ClassLoader 并将URL给ClassLoader,这个URL是个数组
//            ClassLoader loader = new URLClassLoader(new URL[] { url });
//            Enumeration<JarEntry> es = jarFile.entries();
//            //然后我们需要遍历这次entry节点，获取到每一个类，然后进行操作
//            while (es.hasMoreElements()) {
//                JarEntry jarEntry = es.nextElement();
//                String name = jarEntry.getName();
//                if (name != null && name.endsWith(".class")) { // 只解析了.class文件
//                    JVMClass<?> c = loader.loadClass(name.replace("/", ".").substring(0, name.length() - 6));
//                    Object object = c.getDeclaredConstructor().newInstance();
//                    //此处为什么可以进行类型转换，因为我们将接口定义的jar引入到了项目的依赖中。否则是无法进行转换的，我们只有通过其他的方式进行方法调用。
//                    AdapterService adapterService = (AdapterService) object;
//                    //调用接口定义的方法
//                    adapterService.eatMeat();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @SneakyThrows
    public static void main(String[] args) {
//        String path = "D:/Maven_Repo/repository/org/apache/commons/commons-lang3/3.0/commons-lang3-3.0.jar";
//        loadJar(path);
//        Class<?> aClass = Class.forName("org.apache.commons.lang3.StringUtils");
//        Object instance = aClass.newInstance();
//        Object strip = aClass.getDeclaredMethod("strip", String.class, String.class).invoke(instance, "[1,2,3,4,5,6,2,3,5,1]", "[]");
//        System.out.println(strip);

        String path = "D:/文档/相关项目/ifaas-antiepidemic-core-1.0.jar";
        loadJar(path);
        Class<?> aClass = Class.forName("com.intellif.core.sm4.SM4Utils");
        Object instance = aClass.newInstance();
        Object strip = aClass.getDeclaredMethod("strEncode", String.class, String.class).invoke(instance, "admin", "测试");
        System.out.println(strip);
    }

    public static void loadJar(String jarPath) {
        File jarFile = new File(jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        Method method = null;
        try {
            // 通过反射调用addURL方法将工程类和依赖jar加载到当前jvm中
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        //获取方法的访问权限以便写回
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            // 将当前类路径加入到类加载器中
            method.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
    }

}
