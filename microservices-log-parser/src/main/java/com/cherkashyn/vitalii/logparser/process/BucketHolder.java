package com.cherkashyn.vitalii.logparser.process;

import com.cherkashyn.vitalii.logparser.input.Trace;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class BucketHolder implements Closeable{
    private final LinkedList<Bucket> container;
    private final List<FullBucketListener> listeners = new ArrayList<>();
    private final int limit;

    public BucketHolder(int size, FullBucketListener ... listener){
        this.container = new LinkedList<>();
        if(listener!=null)
            Stream.of(listener).forEach(this::addListener);
        this.limit = size;
    }

    public void addListener(FullBucketListener listener){
        this.listeners.add(listener);
    }

    public void add(Trace trace){
        getBucketById(trace.getId()).add(trace);
        if(this.container.size()>limit)
            removeFirst();
    }

    public void close(){
        while(!this.container.isEmpty())
            removeFirst();
    }

    private void removeFirst() {
        emit(this.container.removeFirst());
    }

    private void emit(Bucket bucket) {
        for(FullBucketListener eachListener:this.listeners)
            eachListener.processBucket(bucket);
    }

    private Bucket getBucketById(String id) {
        for(Bucket eachBucket:container)
            if(eachBucket.getId().equals(id))
                return eachBucket;

        Bucket returnValue = new Bucket(id);
        this.container.addLast(returnValue);
        return returnValue;
    }

}
