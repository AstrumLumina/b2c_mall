package com.wzw.b2cmall.minio.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class MyUrlUtil {

    //从 url 中获取 relative url
    public static String getRelativeUrlFromUrl(String urlString){
        int hostEndIndex=urlString.indexOf("://");
        if (hostEndIndex==-1){
            hostEndIndex=0;
        }else {
            hostEndIndex+=3;
        }
        int startIndex = urlString.indexOf('/', hostEndIndex);
        if (startIndex == -1 ) {
            return "";
        }
        return urlString.substring(startIndex, urlString.length());
    }
    //从 relativeurl 中获取 relative path
    public static String getRelativePathFromRelativeUrl(String relativeUrl){
        int relativePathEndIndex = relativeUrl.indexOf("?");
        if (relativePathEndIndex==-1){
            relativePathEndIndex=relativeUrl.length();
        }
        return relativeUrl.substring(0, relativePathEndIndex);
    }
    //从 url 中获取 relative path
    public static String getRelativePathFromUrl(String urlString){
        String relativeUrl = MyUrlUtil.getRelativeUrlFromUrl(urlString);
        return MyUrlUtil.getRelativePathFromRelativeUrl(relativeUrl);
    }
    //从 url 中获取 objectName
    public static String getObjectNameFromUrl(String urlString){
        return MyUrlUtil.getObjectNameFromRelaPathOrRelaUrl(MyUrlUtil.getRelativeUrlFromUrl(urlString));
    }
    //从 relativeurl 中获取 objectName
    //从 relative path 中获取 objectName
    public static String getObjectNameFromRelaPathOrRelaUrl(String relativeUrl){
        int objectNameStratedIndex=relativeUrl.indexOf("/",1)+1;
        int objectNameEndInde=relativeUrl.indexOf("?");
        if (objectNameEndInde==-1){
            objectNameEndInde=relativeUrl.length();
        }
        return relativeUrl.substring(objectNameStratedIndex, objectNameEndInde);
    }
    //从 url 中获取 urlpath
    public static String getUrlPathFromUrl(String urlString){
        int urlPathLength=urlString.indexOf("?");
        return urlString.substring(0, urlPathLength);
    }

}
