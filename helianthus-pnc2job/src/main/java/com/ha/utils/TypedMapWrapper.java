package com.ha.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: shuiqing
 * DateTime: 17/7/10 下午1:55
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class TypedMapWrapper<K, V> {
    private Map<K, V> map;

    public TypedMapWrapper(Map<K, V> map) {
        this.map = map;
    }

    public String getString(K key) {
        return getString(key, null);
    }

    public String getString(K key, String defaultVal) {
        Object obj = map.get(key);
        if (obj == null) {
            return defaultVal;
        }
        if (obj instanceof String) {
            return (String) obj;
        }

        return obj.toString();
    }

    public Boolean getBool(K key, Boolean defaultVal) {
        Object obj = map.get(key);
        if (obj == null) {
            return defaultVal;
        }

        return (Boolean) obj;
    }

    public Integer getInt(K key) {
        return getInt(key, -1);
    }

    public Integer getInt(K key, Integer defaultVal) {
        Object obj = map.get(key);
        if (obj == null) {
            return defaultVal;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof String) {
            return Integer.valueOf((String) obj);
        } else {
            return defaultVal;
        }
    }

    public Long getLong(K key) {
        return getLong(key, -1L);
    }

    public Long getLong(K key, Long defaultVal) {
        Object obj = map.get(key);
        if (obj == null) {
            return defaultVal;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        } else if (obj instanceof Integer) {
            return Long.valueOf((Integer) obj);
        } else if (obj instanceof String) {
            return Long.valueOf((String) obj);
        } else {
            return defaultVal;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getStringCollection(K key) {
        Object obj = map.get(key);
        return (Collection<String>) obj;
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getStringCollection(K key,
                                                  Collection<String> defaultVal) {
        Object obj = map.get(key);
        if (obj == null) {
            return defaultVal;
        }

        return (Collection<String>) obj;
    }

    @SuppressWarnings("unchecked")
    public <C> Collection<C> getCollection(K key) {
        Object obj = map.get(key);
        if (obj instanceof Collection) {
            return (Collection<C>) obj;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <L> List<L> getList(K key) {
        Object obj = map.get(key);
        if (obj instanceof List) {
            return (List<L>) obj;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <L> List<L> getList(K key, List<L> defaultVal) {
        Object obj = map.get(key);
        if (obj instanceof List) {
            return (List<L>) obj;
        }
        return defaultVal;
    }

    public Object getObject(K key) {
        return map.get(key);
    }

    public Map<K, V> getMap() {
        return map;
    }

    @SuppressWarnings("unchecked")
    public <S, T> Map<S, T> getMap(K key) {
        return (Map<S, T>) map.get(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }
}
