package com.tfl.billing;

import org.junit.Assert;
import org.junit.Test;

public class UtilityTest {

    @Test
    public void noonStringTimeToFloatTime() {
        Assert.assertEquals(12.0,Utility.stringTimeToFloatTime("12:00:00"),0);
    }

    @Test
    public void midnightStringTimeToFloatTime() {
        Assert.assertEquals(0.0,Utility.stringTimeToFloatTime("00:00:00"),0);
    }

    @Test
    public void thirtyMinutesStringTimeToPointFiveInFloatFormat() {
        Assert.assertEquals(13.5,Utility.stringTimeToFloatTime("13:30:00"),0);
    }

    @Test
    public void stringTimeToFloatTime() throws Exception {

    }

    @Test
    public void dateTimeToFloatTime() throws Exception {

    }

}