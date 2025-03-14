package org.kg.conversion.tools;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 公共工具：包括以下方法
 * 1.检查文件夹是否存在
 * 2.检查
 * 3.
 * */
public class PublicTools {
    private static final Logger logger = LogManager.getLogger(PublicTools.class);
    /**
     *  检查文件夹是否存在
     *
     * @param path 输入路径
     * */
    public static boolean checkFilePath(String path){
        File file = new File(path);
        return file.isDirectory();
    }
    /**
     * 获取krc结尾的文件
     *
     * @param path 输入路径
     * */
    public static List<File> getMusicFiles(String path) {
        if (!checkFilePath(path)){
            logger.warn("未找到文件夹："+path+"，请检查");
            return null;
        }
        List<File> list = new CopyOnWriteArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        if (files.length == 0){
            logger.warn(path+"文件夹为空");
        } else {
            // 过滤所有以kgm结尾的文件
            for (File f : files) {
                if (f.getName().endsWith(".kgm")){
                    list.add(f);
                }
            }
        }
        return list;
    }
    /**
     * 获取krc结尾的文件
     *
     * @param path 输入路径
     * */
    public static List<File> getKrcFiles(String path) {
        if (!checkFilePath(path)){
            logger.warn("未找到文件夹："+path+"，请检查");
            return null;
        }
        List<File> list = new CopyOnWriteArrayList<>();
        File file = new File(path);
        List<File> files = Arrays.asList(file.listFiles());
        if (files.size() == 0){
            logger.warn(path+"文件夹为空");
        } else {
            // 过滤所有以krc结尾的文件
            files.stream().forEach(f -> {
                if (f.getName().endsWith(".krc"))
                    list.add(f);
                });
        }
        return list;
    }
}
