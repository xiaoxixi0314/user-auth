package com.github.xiaoxixi.auth.utils;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class Md5UtilsTest {

    @Test
    public void encypt() {
        try{
        System.out.println(Md5Utils.encrypt("111111"));
        }catch (Exception e) {

        }
    }

}