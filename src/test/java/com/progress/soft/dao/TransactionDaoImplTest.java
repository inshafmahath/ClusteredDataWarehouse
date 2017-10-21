package com.progress.soft.dao;

import com.progress.soft.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by inshaf on 10/21/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TransactionDaoImplTest {

    @Autowired
    @Qualifier("TransactionDaoImpl")
    TransactionDao TransactionDaoImpl;

    @Test
    public void test_transaction_service_impl() {

        assertThat(TransactionDaoImpl, instanceOf(TransactionDao.class));



    }

}

