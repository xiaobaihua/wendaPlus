package com.xbh.wendaPlus;

import com.xbh.wendaPlus.spider.SpiderController;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        SpiderController controller = new SpiderController();
        controller.start(args);
    }
}
