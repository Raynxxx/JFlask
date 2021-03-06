package com.rayn.jflask.framework.core.impl;

import com.rayn.jflask.framework.core.ClassScanner;
import com.rayn.jflask.framework.util.ClassUtil;
import com.rayn.jflask.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ClassScannerSupport
 * Created by Raynxxx on 2016/08/05.
 */
public abstract class ClassScannerSupport {

    private static final Logger logger = LoggerFactory.getLogger(ClassScanner.class);

    private final String packageName;

    private static List<Class<?>> cacheClassList;

    public ClassScannerSupport(String packageName) {
        this.packageName = packageName;
        if (cacheClassList == null) {
            scanClass(this.packageName);
        }
    }

    public List<Class<?>> getClassList() {
        if (cacheClassList != null) {
            return filter(cacheClassList);
        }
        scanClass(this.packageName);
        return filter(cacheClassList);
    }

    private void scanClass(String packageName) {
        logger.info(String.format("[JFlask][ClassScanner] 启动扫描 <= %s", this.packageName));
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
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                addClass(classList, className);
            } else {
                String subPackagePath = filename;
                String subPackageName = filename;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + filename;
                }
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + filename;
                }
                addClass(classList, subPackagePath, subPackageName);
            }
        }
    }

    private void addClass(List<Class<?>> classList, String className) {
        logger.debug("[JFlask][ClassScanner] 加入缓存 class {}", className);
        Class<?> clazz = ClassUtil.loadClass(className, false);
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
