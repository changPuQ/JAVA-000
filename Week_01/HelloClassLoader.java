package com.jg.kanban;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author levi
 * @date 2020-10-21
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class helloClass = new HelloClassLoader().findClass("hello");
            Object hellObject = helloClass.newInstance();
            Method hellMethod = helloClass.getMethod("hello");
            hellMethod.invoke(hellObject);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            //程序异常
            e.printStackTrace();
        }
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File(this.getClass().getResource("/Hello.xlass").getPath());
        int length = (int) f.length();
        byte[] bytes = new byte[length];
        try (FileInputStream fileInputStream = new FileInputStream(f)) {
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
