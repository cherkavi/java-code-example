package com.cherkashyn.vitalii.logparser.output;

import com.cherkashyn.vitalii.logparser.input.Trace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.*;

@JsonPropertyOrder({ "start", "end", "service", "span", "calls"})
public class TraceChain {
    @JsonProperty("start")
    private final Date start;
    @JsonProperty("end")
    private final Date end;
    @JsonIgnore
    private final String id;
    @JsonProperty("service")
    private final String name;
    @JsonIgnore
    private final String source;
    @JsonProperty("span")
    private final String destination;

    @JsonProperty("calls")
    // JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<TraceChain> children = new HashSet<>();

    public TraceChain(Trace trace){
        this.start = trace.getStart();
        this.end = trace.getEnd();
        this.id = trace.getId();
        this.name = trace.getName();
        this.source = trace.getSource();
        this.destination = trace.getDestination();
    }

    public void addChild(TraceChain chain){
        this.children.add(chain);
    }

    public Set<TraceChain> getChildren(){
        return this.children;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "TraceChain{" +
                "  id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
