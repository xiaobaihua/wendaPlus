package com.xbh.wendaPlus.io.excelIO;

import com.xbh.wendaPlus.io.IReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * excel 读取
 */

public interface IExcelReader<T> extends IReader<T> {

    /**
     * 根据指定的文件读取
     * @param file excel文件
     * @param clazz 需要封装到类型
     * @return list结果集
     */
    List<T> readToList(File file, Class clazz);

    /**
     *  读取自己维护的文件输入流
     * @param clazz 需要封装到类型
     * @return list结果集
     */
    List<T> readToList(Class clazz) throws Exception;
}
