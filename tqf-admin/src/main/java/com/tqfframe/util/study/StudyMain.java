package com.tqfframe.util.study;

import java.io.*;

/**
 * Created by Tang-QiFeng on 2019/3/9
 */
public class StudyMain {
    public static void main(String[] args) throws IOException {
        File f = new File("D://资料//bbb.txt"); // 定位文件位置
        char[] ch = new char[100]; //定义一个数组，用来存放读入的数据
        Reader read = new FileReader(f); //创建字符输入流连接到文件
        int count = read.read(ch); //读操作
        read.close(); //关闭流
        System.out.println("读入的长度为：" + count);
        System.out.println("内容为" + new String(ch, 0, count));

    }
}

