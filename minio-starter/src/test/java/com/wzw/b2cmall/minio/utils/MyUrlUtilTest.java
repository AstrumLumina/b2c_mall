package com.wzw.b2cmall.minio.utils;


import org.junit.jupiter.api.Test;

public class MyUrlUtilTest {

    private static String testUrl="http://10.0.0.208:9000/fhwei/fa/dg/a/fa?dffa=fafa&dfa=";

    @Test
    public void getRelativeUrlFromUrl() {
        System.out.println("getRelativeUrlFromUrl");
        System.out.println(MyUrlUtil.getRelativeUrlFromUrl(testUrl));
    }

    @Test
    public void getRelativePathFromRelativeUrl() {
        System.out.println("getRelativePathFromRelativeUrl");
        System.out.println(MyUrlUtil.getRelativePathFromRelativeUrl(MyUrlUtil.getRelativeUrlFromUrl(testUrl)));
    }

    @Test
    public void getRelativePathFromUrl() {
        System.out.println("getRelativePathFromUrl");
        System.out.println(MyUrlUtil.getRelativePathFromUrl(testUrl));
    }

    @Test
    public void getObjectNameFromUrl() {
        System.out.println("getObjectNameFromUrl");
        System.out.println(MyUrlUtil.getObjectNameFromUrl(testUrl));
    }

    @Test
    public void getObjectNameFromRelaPathOrRelaUrl() {
        System.out.println("getObjectNameFromRelaPathOrRelaUrl");
        System.out.println(MyUrlUtil.getObjectNameFromRelaPathOrRelaUrl(MyUrlUtil.getRelativeUrlFromUrl(testUrl)));
    }

    @Test
    public void getUrlPathFromUrl() {
        System.out.println("getUrlPathFromUrl");
        System.out.println(MyUrlUtil.getUrlPathFromUrl(testUrl));
    }
}