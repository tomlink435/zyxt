package com.gk.study.common;

/**
 * 通过ThreadLocal保存用户信息
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() { return threadLocal.get(); }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
