package com.qzsoft.tah.util;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author aq
 * @version 1.0 2021/7/3
 */
public class BeanParseUtils {

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static <T> T populate(Class<T> cls,Map<String,Object> inMap){
        if(inMap == null){
            return  null;
        }
        try{
            Map<String,Object> newMap = Maps.newConcurrentMap();
            T obj = cls.newInstance();
            Set<String> keySet = inMap.keySet();
            keySet.forEach(key->{
                String newKey = lineToHump(key);
                newMap.put(newKey,inMap.get(key));
            });
            BeanUtils.populate(obj,newMap);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static void copyProperties(Object dest, Object orig){
        try{
            BeanUtils.copyProperties(dest,orig);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
