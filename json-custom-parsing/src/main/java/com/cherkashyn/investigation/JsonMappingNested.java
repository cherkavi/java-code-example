package com.cherkashyn.investigation;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonMappingNested {
    @JsonProperty(required = true, value = "number")
    @JsonAlias("frameId")
    private int number;

    @JsonProperty(required = true, value = "logger_time")
    private Long loggerTime;

    @JsonProperty(required = true, value = "bdc_time")
    private Long bdcTime;

    public static JsonMappingNested fromMap(HashMap<String, ?> source){
       // mapping map to object
       return new ObjectMapper().convertValue(source, FrameMetadata.class);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getLoggerTime() {
        return loggerTime;
    }

    public void setLoggerTime(Long loggerTime) {
        this.loggerTime = loggerTime;
    }

    public Long getBdcTime() {
        return bdcTime;
    }

    public void setBdcTime(Long bdcTime) {
        this.bdcTime = bdcTime;
    }

    @Override
    public String toString() {
        return "JsonMappingNested{" +
                "number=" + number +
                ", loggerTime=" + loggerTime +
                ", bdcTime=" + bdcTime +
                '}';
    }
}
