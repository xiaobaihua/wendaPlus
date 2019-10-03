package com.xbh.wendaPlus.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * 写入器接口
 */
public  interface IWriter extends Closeable {

    /**
     * 写入文件
     * @param resultList 需要写入Excel的数据
     * @param clazz 写入类型
     */
    void write(List resultList, Class clazz) throws NoSuchFieldException, IllegalAccessException, IOException;

    /**
     * 设置资源
     * @param file
     */
    void setOutputResource(File file) throws FileNotFoundException;
}
