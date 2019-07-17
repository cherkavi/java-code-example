package com.cherkashyn.vitalii.logparser.process;

import com.cherkashyn.vitalii.logparser.input.ReaderResult;
import com.cherkashyn.vitalii.logparser.input.Trace;
import com.cherkashyn.vitalii.logparser.input.TraceReader;
import com.cherkashyn.vitalii.logparser.process.Bucket;
import com.cherkashyn.vitalii.logparser.process.BucketHolder;
import com.cherkashyn.vitalii.logparser.process.FullBucketListener;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BucketHolderTest {

    @Test
    public void bucketHolderWithOneBucket(){
        // given
        final List<Bucket> surrogateContainer = new ArrayList<>();
        FullBucketListener listener = bucket -> surrogateContainer.add(bucket);
        int oneBucketTraceSize = 3;
        String bucketId = "test-id";

        BucketHolder bucketHolder = new BucketHolder(1, listener);

        // when
        for(int index=0;index<oneBucketTraceSize; index++)
            bucketHolder.add(Trace.TraceBuilder.create().setId(bucketId).build());
        bucketHolder.add(Trace.TraceBuilder.create().setId("2").build());

        // then
        Assert.assertEquals(1, surrogateContainer.size());
        Assert.assertEquals(bucketId, surrogateContainer.get(0).getId());
        Assert.assertEquals(oneBucketTraceSize, surrogateContainer.get(0).get().size());
    }

    @Test
    public void checkCloseContainer() throws Exception {
        // given
        final List<Bucket> surrogateContainer = new ArrayList<>();
        FullBucketListener listener = bucket -> surrogateContainer.add(bucket);
        int traceCount = 10;
        int bucketHolderLimit = 5;

        BucketHolder bucketHolder = new BucketHolder(bucketHolderLimit, listener);

        // when
        for(int index=0;index<traceCount; index++){
            bucketHolder.add(Trace.TraceBuilder.create().setId(Integer.toString(index)).build());
            bucketHolder.add(Trace.TraceBuilder.create().setId(Integer.toString(index)).build());
        }
        // then
        Assert.assertEquals(bucketHolderLimit, surrogateContainer.size());
        for(Bucket bucket: surrogateContainer)
            assertTrue(bucket.get().size()==2);

        // when
        bucketHolder.close();
        // then
        Assert.assertEquals(traceCount, surrogateContainer.size());
    }

    @Test
    public void checkSizeOfContainer() throws Exception {
        // given
        final List<Bucket> surrogateContainer = new ArrayList<>();
        FullBucketListener listener = bucket -> surrogateContainer.add(bucket);
        int traceCount = 10;
        int bucketHolderLimit = 5;

        BucketHolder bucketHolder = new BucketHolder(bucketHolderLimit, listener);

        // when
        for(int index=0;index<traceCount; index++){
            bucketHolder.add(Trace.TraceBuilder.create().setId(Integer.toString(index)).build());
        }
        for(int index=0;index<traceCount; index++){
            bucketHolder.add(Trace.TraceBuilder.create().setId(Integer.toString(index)).build());
        }
        bucketHolder.close();

        // then
        Assert.assertEquals(traceCount*2, surrogateContainer.size());
        for(Bucket bucket: surrogateContainer)
            assertTrue(bucket.get().size()==1);
    }


    @Test
    public void readFromFile(){
        // given
        final List<Bucket> surrogateContainer = new ArrayList<>();
        FullBucketListener listener = bucket -> surrogateContainer.add(bucket);

        File inputFile = new File(this.getClass().getResource("/infinite-log.txt").getFile());

        // when
        try(BucketHolder bucketHolder = new BucketHolder(1, listener);
            TraceReader traceIterator = new TraceReader(inputFile)){
            while(traceIterator.hasNext()){
                bucketHolder.add(traceIterator.next().getTrace());
            }
        }

        // then
        assertEquals(1, surrogateContainer.size());
        assertEquals(8, surrogateContainer.get(0).get().size());
    }



}