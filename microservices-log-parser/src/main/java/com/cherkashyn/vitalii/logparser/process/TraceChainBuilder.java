package com.cherkashyn.vitalii.logparser.process;

import com.cherkashyn.vitalii.logparser.input.Trace;
import com.cherkashyn.vitalii.logparser.output.TraceChain;

import java.util.*;
import java.util.stream.Collectors;

public class TraceChainBuilder {

    public static class BuildException extends RuntimeException{
        public BuildException() {
        }

        public BuildException(String message) {
            super(message);
        }
    }

    public static class HeadNotFoundException extends BuildException {

    }

    public static class LostChainFoundException extends BuildException{
        public LostChainFoundException(String name) {
            super(name);
        }
    }



    public static TraceChain buildRoute(Bucket bucket) throws BuildException {
        if(Objects.isNull(bucket) || bucket.get().size()==0)
            return null;
        Trace head = findTraceBySourceName(bucket.get(), "null");
        Set<TraceChain> allChains = bucket.get().stream().map(TraceChain::new).collect(Collectors.toSet());
        List<Trace> notProcessedElements = new ArrayList<>(bucket.get());

        trace: while(notProcessedElements.size()>0){
            Trace nextTrace = notProcessedElements.remove(0);
            if(nextTrace.equals(head))
                continue trace;

            for(TraceChain eachChain : allChains){
                if(nextTrace.getSource().equals(eachChain.getDestination())){
                    eachChain.addChild(getTraceChainSourceDestination(allChains, nextTrace.getName(), nextTrace.getSource(), nextTrace.getDestination()));
                    continue trace;
                }
            }
            throw new LostChainFoundException(nextTrace.toString());
        }
        return findTraceChainBySourceName(allChains, "null");
    }

    private static TraceChain getTraceChainSourceDestination(Set<TraceChain> allChains, String name, String source, String destination) {
        return allChains.stream()
                .filter(tc->name.equals(tc.getName()) && source.equals(tc.getSource()) && destination.equals(tc.getDestination()))
                .findFirst()
                .orElseThrow(()->new LostChainFoundException(source + " -> " +destination));
    }

    private static TraceChain findTraceChainBySourceName(Set<TraceChain> traces, String sourceName) {
        return traces.stream()
                .filter(t->sourceName.equals(t.getSource()))
                .findFirst().orElseThrow(HeadNotFoundException::new);
    }

    private static Trace findTraceBySourceName(Set<Trace> traces, String source) {
        return traces.stream()
                .filter(t->source.equals(t.getSource()))
                .findFirst()
                .orElseThrow(HeadNotFoundException::new);
    }

}
