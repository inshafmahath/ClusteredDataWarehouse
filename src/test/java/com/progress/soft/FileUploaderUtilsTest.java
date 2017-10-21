package com.progress.soft;

import com.progress.soft.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by inshaf on 10/21/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FileUploaderUtilsTest {

    @Test
    public void test_utils() {

        Assert.assertEquals(false, FileUploaderUtils.isAmountValid("Test"));

        Assert.assertEquals(false, FileUploaderUtils.isTimeStampValid("Test"));

        Assert.assertEquals(true, FileUploaderUtils.validateNullAndEmpty("USD", "AED", "2017-10-21 08:52:23", "34.50", "test.csv"));

    }
}
