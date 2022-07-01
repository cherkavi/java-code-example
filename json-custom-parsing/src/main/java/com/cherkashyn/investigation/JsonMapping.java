package com.cherkashyn.investigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonMapping {
    @JsonProperty("vin")
    private String vin;

    @JsonProperty("type")
    private String type;

    private List<JsonMappingNested> frames;

    @JsonProperty(value ="frames")
    public void unpackFrames(Object frames){
        ArrayList<HashMap<String,?>> elements=null;
        while(true){
            if(frames instanceof HashMap){
                elements = ((ArrayList<HashMap<String,?>>)((HashMap)frames).get("images_metadata"));
                break;
            }
            if(frames instanceof  List){
                elements = (ArrayList<HashMap<String,?>>)frames;
                break;
            }
            elements = new ArrayList<>();
            break;
        }
        this.frames=elements.stream().map(eachElement->JsonMappingNested.fromMap(eachElement)).collect(Collectors.toList());
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<JsonMappingNested> getFrames() {
        return frames;
    }

    public void setFrames(List<JsonMappingNested> frames) {
        this.frames = frames;
    }

    @Override
    public String toString() {
        return "JsonMapping{" +
                "vin='" + vin + '\'' +
                ", type='" + type + '\'' +
                ", frames=" + frames +
                '}';
    }
}

