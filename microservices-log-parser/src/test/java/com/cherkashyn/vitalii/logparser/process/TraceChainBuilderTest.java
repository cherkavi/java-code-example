package com.cherkashyn.vitalii.logparser.process;

import com.cherkashyn.vitalii.logparser.input.Trace;
import com.cherkashyn.vitalii.logparser.input.TraceReader;
import com.cherkashyn.vitalii.logparser.output.TraceChain;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;

import static org.junit.Assert.*;

public class TraceChainBuilderTest {

    @Test
    public void buildClassicRoute(){

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
                .setDestination("000").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service100")
                .setSource("000")
                .setDestination("100").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service200")
                .setSource("000")
                .setDestination("200").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service210")
                .setSource("200")
                .setDestination("210").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service220")
                .setSource("200")
                .setDestination("220").build());

        // when
        TraceChain root = new TraceChainBuilder().buildRoute(bucket);

        // then
        assertEquals("service000", root.getName());
        assertEquals("null", root.getSource());
        assertEquals("000", root.getDestination());
        assertEquals(2, root.getChildren().size());

        TraceChain service100 = getByName(root.getChildren(), "service100");
        assertNotNull(service100);
        assertEquals(0, service100.getChildren().size());

        TraceChain service200 = getByName(root.getChildren(), "service200");
        assertNotNull(service200);
        assertEquals(2, service200.getChildren().size());

    }


    @Test(expected = TraceChainBuilder.HeadNotFoundException.class)
    public void routeWithoutHead(){
        // given
        String bucketId = "01";
        Bucket bucket = new Bucket(bucketId);

        //                          --> [service100: 000->100]
        //                         /
        //                         ---> [service200: 000->200]-----> [service210: 200->210]
        //                                                    \
        //                                                     ----> [service220: 200->220]

        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service100")
                .setSource("000")
                .setDestination("100").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service200")
                .setSource("000")
                .setDestination("200").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service210")
                .setSource("200")
                .setDestination("210").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service220")
                .setSource("200")
                .setDestination("220").build());

        // when
        new TraceChainBuilder().buildRoute(bucket);

        // then
        Assert.fail();
    }

    @Test(expected = TraceChainBuilder.LostChainFoundException.class)
    public void buildRouteWithDetachedChains(){
        // given
        //                     --> service100
        //                    /
        //  null--->service000--->            ----> service210
        //                                    \
        //                                     ---> service220
        String bucketId = "01";
        Bucket bucket = new Bucket(bucketId);
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service000")
                .setSource("null")
                .setDestination("000").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service100")
                .setSource("000")
                .setDestination("100").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service210")
                .setSource("200")
                .setDestination("210").build());
        bucket.add(Trace.TraceBuilder.create().setId(bucketId)
                .setName("service220")
                .setSource("200")
                .setDestination("220").build());

        // when
        new TraceChainBuilder().buildRoute(bucket);

        // then
        fail();
    }

    @Test
    public void buildWithEmptyBucket(){
        String bucketId = "01";
        Bucket bucket = new Bucket(bucketId);

        // when
        TraceChain root = new TraceChainBuilder().buildRoute(bucket);

        // then
        assertNull(root);
    }


    private TraceChain getByName(Collection<TraceChain> list, String name){
        return list.stream().filter(each -> name.equals(each.getName())).findFirst().orElse(null);
    }


    @Test
    public void buildFromFile(){
        // given
        final TraceChain[] rootArray = {null};
        FullBucketListener listener = bucket -> rootArray[0] = TraceChainBuilder.buildRoute(bucket);

        File inputFile = new File(this.getClass().getResource("/infinite-log.txt").getFile());

        // when
        try(BucketHolder bucketHolder = new BucketHolder(1, listener);
            TraceReader traceIterator = new TraceReader(inputFile)){
            while(traceIterator.hasNext()){
                bucketHolder.add(traceIterator.next().getTrace());
            }
        }

        // then
        assertNotNull(rootArray[0]);
        TraceChain root = rootArray[0];
        assertEquals(2, root.getChildren().size());

        TraceChain traceRootService3 = getByName(root.getChildren(), "service3");
        assertEquals(2, traceRootService3.getChildren().size());

        TraceChain traceRootService9 = getByName(root.getChildren(), "service9");
        assertEquals(1, traceRootService9.getChildren().size());

    }


}