package com.cherkashyn.vitalii.logparser.input;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ReaderResultTest {

    @Test
    public void readingTrace(){
        // given
        String value = "2013-10-23T10:12:36.458Z 2013-10-23T10:12:36.461Z ks2vhl7f service8 yxyyayym->nqipswiu";

        // when
        ReaderResult result = ReaderResult.parse(value);

        // then
        Assert.assertTrue(result.isTrace());
        Assert.assertEquals("ks2vhl7f", result.getTrace().getId());
        Assert.assertEquals("service8", result.getTrace().getName());
        Assert.assertEquals("yxyyayym", result.getTrace().getSource());
        Assert.assertEquals("nqipswiu", result.getTrace().getDestination());

        Assert.assertNotNull(result.getTrace().getStart());
        Date rootStartDate = result.getTrace().getStart();

        Assert.assertEquals(2013, rootStartDate.toInstant().atOffset(ZoneOffset.UTC).getYear());
        Assert.assertEquals(10, rootStartDate.toInstant().atOffset(ZoneOffset.UTC).getMonthValue());
        Assert.assertEquals(23, rootStartDate.toInstant().atOffset(ZoneOffset.UTC).getDayOfMonth());
        Assert.assertEquals(10, rootStartDate.toInstant().atOffset(ZoneOffset.UTC).getHour());
        Assert.assertEquals(12, rootStartDate.toInstant().atOffset(ZoneOffset.UTC).getMinute());
        Assert.assertEquals(36, rootStartDate.toInstant().atOffset(ZoneOffset.UTC).getSecond());
    }

    @Test
    public void brokenLine(){
        // given
        String value = "2013-10-23T10:12:36.458Z 2013-10-23T10:12:36.461Z ks2vhl7f service8 yxyyayym";

        // when
        ReaderResult result = ReaderResult.parse(value);

        // then
        Assert.assertFalse(result.isTrace());
    }

    @Test
    public void wrongDate(){
        // given
        String value = "2013-10-23T10:12:36Z 2013-10-23T10:12:36 ks2vhl7f service8 yxyyayym->nqipswiu";

        // when
        ReaderResult result = ReaderResult.parse(value);

        // then
        Assert.assertFalse(result.isTrace());
    }

    @Test
    public void extraValuesIntoString(){
        // given
        String value = "2013-10-23T10:12:36.458Z 2013-10-23T10:12:36.461Z ks2vhl7f service8 yxyyayym->nqipswiu another string";

        // when
        ReaderResult result = ReaderResult.parse(value);

        // then
        Assert.assertFalse(result.isTrace());
    }


}