package org.rayn.jflask.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.rayn.jflask.framework.InstanceFactory;
import org.rayn.jflask.framework.core.ClassScanner;

import java.util.List;

/**
 * ClassScannerTest
 * Created by Raynxxx on 2016/06/03.
 */
public class ClassScannerTest {

    @Test
    public void testMain() {
        ClassScanner classScanner = InstanceFactory.getClassScanner();
        List<Class<?>> classList = classScanner.getClassList("org.rayn.jflask.test.core");
        Assert.assertTrue(classList.contains(ClassScannerTest.class));
    }
}
