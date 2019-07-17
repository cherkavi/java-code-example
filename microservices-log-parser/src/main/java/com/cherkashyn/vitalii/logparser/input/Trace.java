package com.cherkashyn.vitalii.logparser.input;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.trimToNull;

public class Trace {

    @NotNull(message = "trace start datetime should be specified")
    private Date start;
    @NotNull(message = "trace end datetime should be specified")
    private Date end;
    @NotNull(message = "trace group ID is missed")
    private String id;
    @NotNull(message = "service name is missed")
    private String name;
    @NotNull(message = "source of message is missed")
    private String source;
    @NotNull(message = "destination point for message is missed")
    private String destination;

    private Trace(){
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
        return "Trace{" +
                "start=" + start +
                ", end=" + end +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public static class TraceBuilder{

        private final Trace goal;

        private TraceBuilder(){
            this.goal = new Trace();
        }
        public static TraceBuilder create(){
            return new TraceBuilder();
        }

        public Trace build(){
            return this.goal;
        }

        public TraceBuilder setStart(Date start){
            this.goal.start = start;
            return this;
        }

        public TraceBuilder setEnd(Date end){
            this.goal.end = end;
            return this;
        }

        public TraceBuilder setId(String id){
            this.goal.id = trimToNull(id);
            return this;
        }

        public TraceBuilder setName(String name){
            this.goal.name = trimToNull(name);
            return this;
        }

        public TraceBuilder setSource(String source){
            this.goal.source = trimToNull(source);
            return this;
        }

        public TraceBuilder setDestination(String destination){
            this.goal.destination = trimToNull(destination);
            return this;
        }

    }
}
