package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    String mMessage = null;
    Exception mError = null;
    CountDownLatch latch = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        latch = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        latch.countDown();
    }

    public void testAsyncTaskResult() throws InterruptedException {

        EndpointsAsyncTask task = new EndpointsAsyncTask(getApplication());
        task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String message, Exception e) {
                mMessage = message;
                mError = e;
                latch.countDown();
            }
        }).execute();
        latch.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mMessage));
    }

}