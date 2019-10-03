package com.xbh.wendaPlus.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Administrator
 * 读取器接口
 */
public interface IReader<T> extends Closeable {

    /**
     * 读取文件
     * @param url
     * @return 最终结果由map
     */
    T reader(File url) throws Exception;

    /**
     * 设置资源
     * @param file
     */
    void setInputResource(File file) throws IOException;
}
