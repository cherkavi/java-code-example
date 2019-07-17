package com.cherkashyn.vitalii.logparser.process;

import com.cherkashyn.vitalii.logparser.input.Trace;

import java.util.HashSet;
import java.util.Set;

public class Bucket {
    private final Set<Trace> traces = new HashSet<>();
    private final String id;

    public Bucket(String id){
        this.id = id;
    }

    public void add(Trace trace){
        this.traces.add(trace);
    }

    Set<Trace> get(){
        return this.traces;
    }

    String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "traces=" + traces +
                ", id='" + id + '\'' +
                '}';
    }
}
