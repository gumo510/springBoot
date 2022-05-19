package com.gumo.demo.lock.aspect;


import com.gumo.demo.lock.DistributedLockCallback;
import com.gumo.demo.lock.DistributedLockTemplate;
import com.gumo.demo.lock.annonation.DistributedLock;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 */
@Aspect
@Component
public class DistributedLockAspect {

    private final DistributedLockTemplate lockTemplate;

    @Autowired
    public DistributedLockAspect(DistributedLockTemplate lockTemplate) {
        this.lockTemplate = lockTemplate;
    }

    @Around(value = "@annotation(distributedLock)")
    public Object doAround(ProceedingJoinPoint pjp, DistributedLock distributedLock) throws Throwable {
        Object[] arguments = pjp.getArgs();

        final String lockName = getLockName(distributedLock, arguments);

        return lock(pjp, distributedLock, lockName);
    }

    @AfterThrowing(value = "@annotation(com.intellif.bus.od.lock.annonation.DistributedLock)", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        throw new RuntimeException(ex);
    }

    public String getLockName(DistributedLock annotation, Object[] args) {
        String lockName = annotation.lockName(),
                param = annotation.param();

        if (isEmpty(lockName)) {
            if (args.length > 0) {
                if (isNotEmpty(param)) {
                    Object arg;
                    if (annotation.argNum() > 0) {
                        arg = args[annotation.argNum() - 1];
                    } else {
                        arg = args[0];
                    }
                    lockName = String.valueOf(getParam(arg, param));
                } else if (annotation.argNum() > 0) {
                    lockName = args[annotation.argNum() - 1].toString();
                } else if (annotation.argNums().length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int argNum : annotation.argNums()) {
                        sb.append(args[argNum - 1].toString()).append(",");
                    }
                    lockName = sb.substring(0, sb.length() - 2);
                }
            }
        }

        if (isNotEmpty(lockName)) {
            String preLockName = annotation.lockNamePre(),
                    postLockName = annotation.lockNamePost(),
                    separator = annotation.separator();

            StringBuilder lName = new StringBuilder();
            if (isNotEmpty(preLockName)) {
                lName.append(preLockName).append(separator);
            }
            lName.append(lockName);
            if (isNotEmpty(postLockName)) {
                lName.append(separator).append(postLockName);
            }

            lockName = lName.toString();

            return lockName;
        }

        throw new IllegalArgumentException("Can't get or generate lockName accurately!");
    }

    /**
     * 从方法参数获取数据
     *
     * @param param
     * @param arg   方法的参数数组
     * @return
     */
    public Object getParam(Object arg, String param) {
        if (isNotEmpty(param) && arg != null) {
            try {
                return PropertyUtils.getProperty(arg, param);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + "没有属性" + param + "或未实现get方法。", e);
            } catch (Exception e) {
                throw new RuntimeException("", e);
            }
        }
        return null;
    }

    public Object lock(ProceedingJoinPoint pjp, DistributedLock annotation, final String lockName) {
        boolean fairLock = annotation.fairLock();

        boolean tryLock = annotation.tryLock();

        if (tryLock) {
            return tryLock(pjp, annotation, lockName, fairLock);
        } else {
            return lock(pjp, annotation, lockName, fairLock);
        }
    }

    public Object lock(ProceedingJoinPoint pjp, DistributedLock annotation, final String lockName, boolean fairLock) {
        return lockTemplate.lock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, annotation.leaseTime(), annotation.timeUnit(), fairLock);
    }

    public Object tryLock(ProceedingJoinPoint pjp, DistributedLock annotation, final String lockName, boolean fairLock) {

        long waitTime = annotation.waitTime(),
                leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();

        return lockTemplate.tryLock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, waitTime, leaseTime, timeUnit, fairLock);
    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    private boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
}
