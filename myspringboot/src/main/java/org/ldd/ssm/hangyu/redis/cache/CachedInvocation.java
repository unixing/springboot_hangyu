package org.ldd.ssm.hangyu.redis.cache;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class CachedInvocation {

    private Object key;
    private final Object targetBean;
    private final Method targetMethod;
    private Object[] arguments;

    public CachedInvocation(Object key, Object targetBean, Method targetMethod, Object[] arguments) {
        this.key = key;
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        if (arguments != null && arguments.length != 0) {
            this.arguments = Arrays.copyOf(arguments, arguments.length);
        }
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Object getTargetBean() {
        return targetBean;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object getKey() {
        return key;
    }

    /**
     * 必须重写equals和hashCode方法，否则放到set集合里没法去重
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CachedInvocation that = (CachedInvocation) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}