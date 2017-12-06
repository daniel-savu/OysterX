package com.tfl.billing;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.fail;

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
    public void stringTimeToFloatTimeMethodTakesWrongInputStringTimeThrowsWrongTimeSeparatorException() {
        try {
            Utility.stringTimeToFloatTime("12;00:00");
            fail("WrongTimeSeparatorException not thrown");
        } catch(Exception e) {
            Assert.assertTrue(e.getClass().getCanonicalName().equals("com.tfl.billing.WrongTimeSeparatorException"));
        }

    }

    @Test
    public void dateTimeToFloatTime() {
        Date date = new Date(DateFormatter.format("13:00:00"));
        Assert.assertEquals(13.0,Utility.dateTimeToFloatTime(date),0);
    }

    @Test
    public void thirtyMinutesDateTimeToPointFiveInFloatTime() {
        Date date = new Date(DateFormatter.format("13:30:00"));
        Assert.assertEquals(13.5,Utility.dateTimeToFloatTime(date),0);
    }

}