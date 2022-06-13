package com.gumo.demo.lock;

/**
 * <p>
 * 分布式锁回调接口
 * </p>
 *
 */
public interface DistributedLockCallback<T> {
    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     *
     * @return
     */
    T process();

    /**
     * 得到分布式锁名称
     *
     * @return
     */
    String getLockName();
}
