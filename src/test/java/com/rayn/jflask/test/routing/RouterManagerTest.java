package com.rayn.jflask.test.routing;

import com.rayn.jflask.framework.routing.RouterManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RouterManagerTest
 * Created by Raynxxx on 2016/07/13.
 */
public class RouterManagerTest {

    @Test
    public void testGetDynamicRegexPath() {
        String regex = RouterManager.getDynamicRegexPath("/user/<name>/<id>");
        Matcher matcher = Pattern.compile(regex).matcher("/user/rayn/5");
        Assert.assertTrue(matcher.matches());
        Assert.assertEquals("rayn", matcher.group(1));
        Assert.assertEquals("5", matcher.group(2));
    }
}
