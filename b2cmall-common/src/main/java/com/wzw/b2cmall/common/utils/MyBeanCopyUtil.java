package com.wzw.b2cmall.common.utils;

import lombok.Getter;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MyBeanCopyUtil {
    public static Map<BeanCopierCacheMapKey,BeanCopier> beanCopierCacheConcurrentMap=new ConcurrentHashMap<>();


    @Getter
    public static class BeanCopierCacheMapKey{
        private Class resourceClazz;
        private Class desClazz;



        public BeanCopierCacheMapKey(Class resourceClazz, Class desClazz) {
            this.resourceClazz = resourceClazz;
            this.desClazz = desClazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeanCopierCacheMapKey that = (BeanCopierCacheMapKey) o;
            return Objects.equals(resourceClazz, that.resourceClazz) && Objects.equals(desClazz, that.desClazz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(resourceClazz, desClazz);
        }

    }

    public static  void defaultBeanCopy(Object resource,Object des){
        // 实现对象将属性复制
        BeanCopierCacheMapKey beanCopierCacheMapKey = new BeanCopierCacheMapKey(resource.getClass(), des.getClass());
        BeanCopier beanCopier = beanCopierCacheConcurrentMap.get(beanCopierCacheMapKey);
        if (beanCopier==null){
            beanCopier=BeanCopier.create(beanCopierCacheMapKey.getResourceClazz(), beanCopierCacheMapKey.getDesClazz(), false);
            beanCopierCacheConcurrentMap.put(beanCopierCacheMapKey,beanCopier);
        }
        beanCopier.copy(resource, des, null);
    }
    public static  void defaultBeanCopy(Object resource,Object des,@NotNull Converter converter){
        // 实现对象将属性复制
        BeanCopierCacheMapKey beanCopierCacheMapKey = new BeanCopierCacheMapKey(resource.getClass(), des.getClass());
        BeanCopier beanCopier = beanCopierCacheConcurrentMap.get(beanCopierCacheMapKey);
        if (beanCopier==null){
            beanCopier=BeanCopier.create(beanCopierCacheMapKey.getResourceClazz(), beanCopierCacheMapKey.getDesClazz(), true);
            beanCopierCacheConcurrentMap.put(beanCopierCacheMapKey,beanCopier);
        }
        beanCopier.copy(resource, des, converter);
    }

    //后来突然想到 BeanCopier底层已经帮我们做了一层缓存了,这里其实没有必要在做缓存了
    public static <T,V> void defaultBeanCopy(Class<? super T> resourceClazz,Class<? super V> desClazz,T resource,V des){
        // 实现对象将属性复制
        BeanCopierCacheMapKey beanCopierCacheMapKey = new BeanCopierCacheMapKey(resourceClazz, desClazz);
        BeanCopier beanCopier = beanCopierCacheConcurrentMap.get(beanCopierCacheMapKey);
        if (beanCopier==null){
            beanCopier=BeanCopier.create(resourceClazz, desClazz, false);
            beanCopierCacheConcurrentMap.put(beanCopierCacheMapKey,beanCopier);
        }
        beanCopier.copy(resource, des, null);
    }

    public static <T,V> void coustumBeanCopy(Class< ? super T> resourceClazz, Class< ? super V> desClazz, T resource, V des,@NotNull Converter converter){
        // 实现对象将属性复制
        BeanCopierCacheMapKey beanCopierCacheMapKey = new BeanCopierCacheMapKey(resourceClazz, desClazz);
        BeanCopier beanCopier = beanCopierCacheConcurrentMap.get(beanCopierCacheMapKey);
        if (beanCopier!=null){
            beanCopier=BeanCopier.create(resourceClazz, desClazz, true);
            beanCopierCacheConcurrentMap.put(beanCopierCacheMapKey,beanCopier);
        }
        beanCopier.copy(resource, des, converter);
    }
}
