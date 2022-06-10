package com.gumo.demo.aspect;


import com.gumo.demo.annotation.FillOrgId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 接口类切面
 * </p>
 *
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    /**
     * 解析注解@FillOrgId 将注解定义的信息 在controller方法入参中通过机构id获取子机构id并设置入参对象中
     *
     * @param joinPoint
     * @param fillOrgId
     */
    @Before("@annotation(fillOrgId) && execution(* com.gumo.demo.*Controller.*(..))")
    public void fillOrgIds(JoinPoint joinPoint, FillOrgId fillOrgId) {
        try {
            if (Objects.isNull(fillOrgId)) {
                log.warn("annotation:@FillOrgId is null");
                return;
            }
            //1.获取注解的字段参数和需要设置机构信息的对象class
            String getFiledName = fillOrgId.getFiled();
            String setFiledName = fillOrgId.setFiled();
            Class<?> oClass = fillOrgId.type();
            if (StringUtils.isEmpty(getFiledName) || StringUtils.isEmpty(setFiledName)) {
                return;
            }
            for (Object arg : joinPoint.getArgs()) {
                if (arg.getClass() == oClass) {
                    //1.设置属性原则，优先设置本类属性，不存在向父类查找
                    setFieldValue(arg, getFiledName, setFiledName);
                }
            }
        } catch (Exception e) {
            log.warn("Aop Method fillOrgIds err!", e);
        }
    }

    private void setFieldValue(Object arg, String getFiledName, String setFiledName) throws IllegalAccessException {
        if (Objects.isNull(arg)) {
            return;
        }
        List<Field> allField = getAllField(arg);
        List<String> childOrgIds = null;
        for (Field getFiled : allField) {
            if (getFiled.getName().equals(getFiledName)) {
                getFiled.setAccessible(true);
                Object getFiledValue = getFiled.get(arg);
                if (Objects.nonNull(getFiledValue) && (getFiledValue instanceof String)) {
//                    childOrgIds = OrgCache.getChildOrgIds((String) getFiledValue);
                }
            }
        }
        if (Objects.isNull(childOrgIds)) {
            return;
        }
        for (Field setFiled : allField) {
            if (setFiled.getName().equals(setFiledName) && setFiled.getType() == List.class) {
                setFiled.setAccessible(true);
                setFiled.set(arg, childOrgIds);
            }
        }
    }

    private static List<Field> getAllField(Object model) {
        Class<?> clazz = model.getClass();
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
