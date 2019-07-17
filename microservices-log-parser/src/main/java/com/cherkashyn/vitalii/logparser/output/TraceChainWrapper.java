package com.cherkashyn.vitalii.logparser.output;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * object according description
 */
@JsonPropertyOrder({"id", "root"})
public class TraceChainWrapper {
    @JsonProperty("id")
    private final String id;
    @JsonProperty("root")
    private final TraceChain root;

    public TraceChainWrapper(TraceChain root) {
        this.id = root.getId();
        this.root = root;
    }

    public String getId() {
        return id;
    }

    public TraceChain getRoot() {
        return root;
    }

}
