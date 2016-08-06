package com.rayn.jflask.test.core;

import com.rayn.jflask.framework.annotation.ioc.Bean;
import com.rayn.jflask.framework.core.ClassHelper;
import org.junit.Assert;
import org.junit.Test;
import com.rayn.jflask.framework.InstanceFactory;
import com.rayn.jflask.framework.core.ClassScanner;

import java.util.List;

/**
 * ClassScannerTest
 * Created by Raynxxx on 2016/06/03.
 */
public class ClassScannerTest {

    public void test() {
        System.out.println("Hello");
    }

    @Test
    public void testMain() {
        List<Class<?>> classList = ClassHelper.getClassList();
        Assert.assertTrue(classList.contains(Bean.class));
    }
}
