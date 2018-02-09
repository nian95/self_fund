package com.common;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.AllPredicate;

import java.util.*;

/**
 * Created by nian on 2018/1/20.
 */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotNullOrEmptyArray(T[] array) {
        return array != null && array.length != 0;
    }

    public static <T> List<T> mergeAll(List... lists) {
        List<T> mergedList = new ArrayList();
        int i = 0;

        for(int len = lists.length; i < len; ++i) {
            List<T> list = lists[i];
            if(list != null && !list.isEmpty()) {
                int j = 0;

                for(int lenj = list.size(); j < lenj; ++j) {
                    T obj = list.get(j);
                    if(obj != null) {
                        mergedList.add(obj);
                    }
                }
            }
        }

        return mergedList;
    }

    public static <T> List<T> asList(T... objs) {
        if(objs == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<T> list = new ArrayList();
            Collections.addAll(list, objs);
            return list;
        }
    }

    public static <K, V> Map<K, V> makeMap(K key, V value) {
        Map<K, V> map = new HashMap();
        map.put(key, value);
        return map;
    }

    public static <K, V> Map<K, V> makeMap(K key1, V value1, K key2, V value2) {
        Map<K, V> map = new HashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

//    public static <T> boolean contains(T[] array, T val) {
//        Object[] var2 = array;
//        int var3 = array.length;
//
//        for(int var4 = 0; var4 < var3; ++var4) {
//            T t = var2[var4];
//            if(val.equals(t)) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public static String join(Object[] words, String seperator) {
        StringBuilder sb = new StringBuilder();
        if(words != null) {
            for(int i = 0; i < words.length; ++i) {
                sb.append(words[i]);
                if(i < words.length - 1) {
                    sb.append(seperator);
                }
            }
        }

        return sb.toString();
    }

    public static String join(Collection<?> collection, String seperator) {
        Object[] objs = new Object[collection.size()];
        collection.toArray(objs);
        return join(objs, seperator);
    }

//    public static <T> T find(Collection<T> collection, String propertyName, Object propertyValue) {
//        return org.apache.commons.collections.CollectionUtils.find(collection, new BeanPropertyValueEqualsPredicate(propertyName, propertyValue));
//    }
//
//    public static <T> T find(Collection<T> collection, Map<String, Object> propertyValueMap) {
//        Predicate[] predicates = new BeanPropertyValueEqualsPredicate[propertyValueMap.size()];
//        Set<String> propertyNameSet = propertyValueMap.keySet();
//        int index = 0;
//
//        for(Iterator var5 = propertyNameSet.iterator(); var5.hasNext(); ++index) {
//            String propertyName = (String)var5.next();
//            predicates[index] = new BeanPropertyValueEqualsPredicate(propertyName, propertyValueMap.get(propertyName));
//        }
//
//        Predicate allPredicate = new AllPredicate(predicates);
//        return org.apache.commons.collections.CollectionUtils.find(collection, allPredicate);
//    }

    public static <T, PropType> Collection<PropType> collect(Collection<T> collection, String propertyName) {
        Transformer transformer = new BeanToPropertyValueTransformer(propertyName);
        return org.apache.commons.collections.CollectionUtils.collect(collection, transformer);
    }

    public static <T> T[] copy(T[] sourceArray) {
        return sourceArray == null?null:Arrays.copyOf(sourceArray, sourceArray.length);
    }

    public static boolean equalsList(List<String> list1, List<String> list2) {
        if(list1.size() != list2.size()) {
            return false;
        } else {
            int i = 0;

            while(true) {
                if(i >= list1.size()) {
                    return true;
                }

                if(list1.get(i) == null) {
                    if(list2.get(i) != null) {
                        break;
                    }
                } else if(!((String)list1.get(i)).equals(list2.get(i))) {
                    break;
                }

                ++i;
            }

            return false;
        }
    }

//    public static <T> List<T> findByPropertyValue(Collection<T> collection, final String propertyName, final Object value) {
//        return (List)org.apache.commons.collections.CollectionUtils.select(collection, new Predicate() {
//            public boolean evaluate(Object object) {
//                Object obj = BeanUtils.getProperty(object, propertyName);
//                return value.equals(obj);
//            }
//        });
//    }
}
