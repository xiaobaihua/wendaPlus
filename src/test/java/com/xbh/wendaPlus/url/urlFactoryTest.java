package com.xbh.wendaPlus.url;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class urlFactoryTest {

    @Test
    void production() throws UnsupportedEncodingException {
        String str = "大众养生网";
        UrlFactory factory = new UrlFactory();
        factory.production("胃疼", str);

    }
}