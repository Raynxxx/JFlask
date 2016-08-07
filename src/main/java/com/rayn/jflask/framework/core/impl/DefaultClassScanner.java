package com.rayn.jflask.framework.core.impl;

import com.rayn.jflask.framework.Constants;
import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * DefaultClassScanner
 * Created by Raynxxx on 2016/05/21.
 */
public class DefaultClassScanner implements ClassScanner {

    private static final Logger logger = LoggerFactory.getLogger(ClassScanner.class);

    /**
     * 取得指定包下的所有类
     */
    @Override
    public List<Class<?>> getClassList(final String packageName) {
        return new ClassScannerSupport(packageName) {
            @Override
            public boolean accept(Class<?> clazz) {
                String className = clazz.getName();
                String pkgName = className.substring(0, className.lastIndexOf("."));
                return pkgName.startsWith(packageName);
            }
        }.getClassList();
    }

    /**
     * 取得指定包下含有指定注解的所有类
     */
    @Override
    public List<Class<?>> getClassListByAnnotation(final String packageName, final Class<? extends Annotation> annotation) {
        return new ClassScannerSupport(packageName) {
            @Override
            public boolean accept(Class<?> clazz) {
                String className = clazz.getName();
                String pkgName = className.substring(0, className.lastIndexOf("."));
                return pkgName.startsWith(packageName) && clazz.isAnnotationPresent(annotation);
            }
        }.getClassList();
    }

    /**
     * 取得指定包下, 指定父类或者接口的所有类
     */
    @Override
    public List<Class<?>> getClassListBySuper(String packageName, final Class<?> superClass) {
        return new ClassScannerSupport(packageName) {
            @Override
            public boolean accept(Class<?> clazz) {
                String className = clazz.getName();
                String pkgName = className.substring(0, className.lastIndexOf("."));
                return pkgName.startsWith(packageName) && superClass.isAssignableFrom(clazz) &&
                        !superClass.equals(clazz);
            }
        }.getClassList();
    }
}
