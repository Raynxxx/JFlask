package org.rayn.jflask.framework.core.impl;

import org.rayn.jflask.framework.core.ClassScanner;
import org.rayn.jflask.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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

    private static final Logger logger = LoggerFactory.getLogger(DefaultClassScanner.class);

    private static List<Class<?>> cacheClassList;

    /**
     * 取得指定包下的所有类
     */
    @Override
    public List<Class<?>> getClassList(final String packageName) {
        return new ScannerHelper(packageName) {
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
    public List<Class<?>> getClassListByAnnotation(String packageName, final Class<? extends Annotation> annotation) {
        return new ScannerHelper(packageName) {
            @Override
            public boolean accept(Class<?> clazz) {
                return clazz.isAnnotationPresent(annotation);
            }
        }.getClassList();
    }

    /**
     * 取得指定包下, 指定父类或者接口的所有类
     */
    @Override
    public List<Class<?>> getClassListBySuper(String packageName, final Class<?> superClass) {
        return new ScannerHelper(packageName) {
            @Override
            public boolean accept(Class<?> clazz) {
                return superClass.isAssignableFrom(clazz) && !superClass.equals(clazz);
            }
        }.getClassList();
    }


    private abstract class ScannerHelper {

        private String packageName;

        ScannerHelper(String packageName) {
            this.packageName = packageName;
        }

        List<Class<?>> getClassList() {
            if (cacheClassList != null) {
                return filter(cacheClassList);
            }
            cacheClassList = new ArrayList<>();
            try {
                // 从包内获取所有资源的URL
                Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace(".", "/"));
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    if (url != null) {
                        // 获取此资源URL协议 (file/jar)
                        String protocol = url.getProtocol();
                        if (protocol.equals("file")) {
                            String packagePath = url.getPath().replace("%20", " ");
                            // 子过程搜索文件或文件夹的 class
                            addClass(cacheClassList, packagePath, packageName);
                        } else if (protocol.equals("jar")) {
                            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                            JarFile jarFile = jarURLConnection.getJarFile();
                            Enumeration<JarEntry> jarEntries = jarFile.entries();
                            // 遍历 jar 文件的所有 class
                            while (jarEntries.hasMoreElements()) {
                                JarEntry jarEntry = jarEntries.nextElement();
                                String jarEntryName = jarEntry.getName();
                                if (jarEntryName.endsWith(".class")) {
                                    String className = jarEntryName
                                            .substring(0, jarEntryName.lastIndexOf("."))
                                            .replace("/", ".");
                                    addClass(cacheClassList, className);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("加载类错误:", e);
            }
            return filter(cacheClassList);
        }

        private void addClass(List<Class<?>> classList, String packagePath, String packageName) {
            File[] files = new File(packagePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
                }
            });
            for (File file : files) {
                String filename = file.getName();
                if (file.isFile()) {
                    String className = filename.substring(0, filename.lastIndexOf("."));
                    if (!className.isEmpty()) {
                        className = packageName + "." + className;
                    }
                    addClass(classList, className);
                } else {
                    if (!filename.isEmpty()) {
                        String subPackagePath = packagePath + "/" + filename;
                        String subPackageName = packageName + "." + filename;
                        addClass(classList, subPackagePath, subPackageName);
                    }
                }
            }
        }

        private void addClass(List<Class<?>> classList, String className) {
            Class<?> clazz = ClassUtil.loadClass(className, false);
            /*
            if (accept(clazz)) {
                classList.add(clazz);
            }
            */
            classList.add(clazz);
        }

        private List<Class<?>> filter(List<Class<?>> classList) {
            List<Class<?>> tmp = new ArrayList<>();
            for (Class<?> clazz : classList) {
                if (accept(clazz)) {
                    tmp.add(clazz);
                }
            }
            return tmp;
        }

        public abstract boolean accept(Class<?> clazz);
    }





}
