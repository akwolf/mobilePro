package com.jnzy.mdm.util.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jnzy.mdm.util.AppUtil;
import com.jnzy.mdm.util.StringUtil;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * wk createTime 2015-12-30
 * dozer是一种JavaBean的映射工具，类似于apache的BeanUtils。
 * 但是dozer更强大，它可以灵活的处理复杂类型之间的映射。
 * 不但可以进行简单的属性映射、复杂的类型映射、双向映射、递归映射等，并且可以通过XML配置文件进行灵活的配置。
 */
public class ObjectMapper {
    private static DozerBeanMapper dozer = new DozerBeanMapper();


    /**
     * 转化添加图片域名.
     *
     * @param needAddDomain
     * @param sourceMap
     * @return
     */
    private static Map addDomain2SomeOnes(List<String> needAddDomain, Map sourceMap) {
        if (null == sourceMap)
            return null;
        //先把图片的域名加上
        if (null != needAddDomain && needAddDomain.size() > 0) {
            for (String key : needAddDomain) {
                String imgPath = sourceMap.get(key) + "";
                sourceMap.put(key, StringUtil.addHttp2UriStart(imgPath));
            }
        }
        return sourceMap;
    }


    /**
     * 改变字段的属性.
     *
     * @param sourceMap
     * @param onlyContainsFields
     * @return
     */
    private static Map onlyContainsFields(Map sourceMap, String... onlyContainsFields) {
        if (null == onlyContainsFields || onlyContainsFields.length == 0) {
            return sourceMap;
        } else {
            Map newMap = Maps.newHashMap();
            for (String onlyContainsField : onlyContainsFields) {
                String valuesContainsField= sourceMap.get(onlyContainsField)+"";
                if(StringUtil.isBlank(valuesContainsField)){
                    newMap.put(onlyContainsField, "");
                }else{
                    newMap.put(onlyContainsField, valuesContainsField);
                }
            }
            return newMap;
        }
    }

    /**
     * 基于Dozer转换对象的类型.
     *
     * @param source           user
     * @param destinationClass Map.class
     * @param <T>
     * @return {}
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 把对象转成{},并且可以除去某些属性
     *
     * @param source       一个对象
     * @param ignoreFields 忽略的字段
     * @return {}
     */
    public static Map map(Object source, String... ignoreFields) {
        Map sourceMap = map(source, Map.class);
        if (null != ignoreFields && ignoreFields.length > 0) {
            for (String ignoreField : ignoreFields) {
                sourceMap.remove(ignoreField);
            }
        }
        return sourceMap;
    }

    /**
     * 把对象转成{},并且可以除去某些属性
     *
     * @param source
     * @param needAddDomain
     * @param ignoreFields
     * @return
     */
    public static Map map(Object source, List<String> needAddDomain, String... ignoreFields) {
        Map sourceMap = map(source, Map.class);
        addDomain2SomeOnes(needAddDomain, sourceMap);
        if (null != ignoreFields && ignoreFields.length > 0) {
            for (String ignoreField : ignoreFields) {
                sourceMap.remove(ignoreField);
            }
        }
        return sourceMap;
    }

    /**
     * 把对象转成{},并且只包含某些属性
     *
     * @param source             一个对象
     * @param onlyContainsFields 只包含的字段
     * @return {}
     */
    public static Map mapOnlyCont(Object source, String... onlyContainsFields) {
        Map sourceMap = map(source, Map.class);
        return onlyContainsFields(sourceMap, onlyContainsFields);
    }

    /**
     * 把对象转成{},并且只包含某些属性
     *
     * @param source
     * @param needAddDomain
     * @param onlyContainsFields
     * @return
     */
    public static Map mapOnlyCont(Object source, List<String> needAddDomain, String... onlyContainsFields) {
        Map sourceMap = map(source, Map.class);
        addDomain2SomeOnes(needAddDomain, sourceMap);
        return onlyContainsFields(sourceMap, onlyContainsFields);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     *
     * @param sourceList       一个对象的集合
     * @param destinationClass Map.class
     * @param <T>
     * @return [{},{},{}]
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 这个方法可以把一个集合转成一个[{},{}]的形式,并且可以去掉某些字段.
     *
     * @param sourceList   一个对象的集合
     * @param ignoreFields 忽略对象的某些字段.
     * @return [{},{}]
     */
    public static List mapList(Collection<?> sourceList, String... ignoreFields) {
        List<Map> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            Map sourceMap = map(sourceObject, Map.class);
            if (null != ignoreFields && ignoreFields.length > 0) {
                for (String ignoreField : ignoreFields) {
                    sourceMap.remove(ignoreField);
                }
            }
            destinationList.add(sourceMap);
        }
        return destinationList;
    }


    /**
     * 这个方法可以把一个集合转成一个[{},{}]的形式,并且可以去掉某些字段.
     *
     * @param sourceList
     * @param needAddDomain 需要加域名的地方
     * @param ignoreFields
     * @return
     */
    public static List mapList(Collection<?> sourceList, List<String> needAddDomain, String... ignoreFields) {
        List<Map> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            Map sourceMap = map(sourceObject, Map.class);
            //先把图片的域名加上
            addDomain2SomeOnes(needAddDomain, sourceMap);
            if (null != ignoreFields && ignoreFields.length > 0) {
                for (String ignoreField : ignoreFields) {
                    sourceMap.remove(ignoreField);
                }
            }
            destinationList.add(sourceMap);
        }
        return destinationList;
    }


    /**
     * 这个方法可以把一个集合转成一个[{},{}]的形式,并且只包含某些字段.
     *
     * @param sourceList         一个对象的集合
     * @param onlyContainsFields 只包含的字段
     * @return [{},{}]
     */
    public static List mapListOnlyCont(Collection<?> sourceList, String... onlyContainsFields) {
        List<Map> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            Map sourceMap = map(sourceObject, Map.class);
            destinationList.add(onlyContainsFields(sourceMap, onlyContainsFields));
        }
        return destinationList;
    }

    /**
     * 里面还有图片的
     *
     * @param sourceList
     * @param needAddDomain
     * @param onlyContainsFields
     * @return
     */
    public static List mapListOnlyCont(Collection<?> sourceList, List<String> needAddDomain, String... onlyContainsFields) {
        List<Map> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            Map sourceMap = map(sourceObject, Map.class);
            //先把图片的域名加上
            addDomain2SomeOnes(needAddDomain, sourceMap);
            destinationList.add(onlyContainsFields(sourceMap, onlyContainsFields));
        }
        return destinationList;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
    }
}