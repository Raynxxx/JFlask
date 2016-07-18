package com.rayn.jflask.test.orm;

import com.rayn.jflask.test.model.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseModelTest
 * Created by Raynxxx on 2016/07/18.
 */
public class BaseModelTest {

    @Test
    public void testBaseModelToString() {
        User user = new User();
        Assert.assertEquals(String.format("<Model \"%s\">",
                user.getClass().getSimpleName()), user.toString());
    }
}
