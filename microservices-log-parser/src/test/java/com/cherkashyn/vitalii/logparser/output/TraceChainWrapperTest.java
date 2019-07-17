package com.cherkashyn.vitalii.logparser.output;

import com.cherkashyn.vitalii.logparser.input.Trace;
import com.cherkashyn.vitalii.logparser.process.Bucket;
import com.cherkashyn.vitalii.logparser.process.TraceChainBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TraceChainWrapperTest {

    @Test
    public void checkOutput() throws JsonProcessingException {
        // given
        String bucketId = "01";
        Bucket bucket = new Bucket(bucketId);
        //                          --> [service100: 000->100]
        //                         /
        //  [service000: null->000]---> [service200: 000->200]-----> [service210: 200->210]
        //                                                    \
        //                                                     ----> [service220: 200->220]

        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service000")
                .setSource("null")
                .setStart(new Date())
                .setEnd(new Date())
                .setDestination("000").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service100")
                .setSource("000")
                .setStart(new Date())
                .setEnd(new Date())
                .setDestination("100").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service200")
                .setSource("000")
                .setStart(new Date())
                .setEnd(new Date())
                .setDestination("200").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service210")
                .setSource("200")
                .setStart(new Date())
                .setEnd(new Date())
                .setDestination("210").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service220")
                .setSource("200")
                .setStart(new Date())
                .setEnd(new Date())
                .setDestination("220").build());

        // when
        TraceChain root = new TraceChainBuilder().buildRoute(bucket);
        String json = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"))
                .writeValueAsString(new TraceChainWrapper(root));

        // then
        System.out.println(json);
    }

}