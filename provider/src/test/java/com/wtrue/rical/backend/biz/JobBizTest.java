package com.wtrue.rical.backend.biz;

import com.wtrue.rical.backend.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/2/5 4:13 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class JobBizTest {

    @Autowired
    private IJobBiz jobBiz;

    @Test
    public void addJob() {
        jobBiz.addJob();
    }
}