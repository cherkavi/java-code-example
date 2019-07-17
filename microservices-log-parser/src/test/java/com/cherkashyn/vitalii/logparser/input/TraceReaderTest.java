package com.cherkashyn.vitalii.logparser.input;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class TraceReaderTest {
    private File LOG_FILE;
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Before
    public void before(){
        LOG_FILE = new File(this.getClass().getResource("/small-log.txt").getFile());
    }

    @Test
    public void readFile(){
        // given
        Trace lastTrace = null;
        ReadException lastException = null;

        // when
        Iterator<ReaderResult> traceIterator = new TraceReader(LOG_FILE);
        int traceCounter = 0;
        while(traceIterator.hasNext()){
            ReaderResult nextResult = traceIterator.next();
            if(nextResult.isTrace()) {
                traceCounter++;
                lastTrace = nextResult.getTrace();
            }else
                lastException = nextResult.getTraceException();
        }

        // then
        assertEquals(389, traceCounter);
        // 2013-10-23T10:13:04.344Z 2013-10-23T10:13:05.194Z uvnipcq3 service5 null->lgopn2tu
        assertNotNull(lastTrace);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals("2013-10-23T10:13:04.344Z", sdf.format(lastTrace.getStart()));
        assertEquals("2013-10-23T10:13:05.194Z", sdf.format(lastTrace.getEnd()));
        assertEquals("uvnipcq3", lastTrace.getId());
        assertEquals("service5", lastTrace.getName());
        assertEquals("null", lastTrace.getSource());
        assertEquals("lgopn2tu", lastTrace.getDestination());

        assertNull(lastException);
    }


}