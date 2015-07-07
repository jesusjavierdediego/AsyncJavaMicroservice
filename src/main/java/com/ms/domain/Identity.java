package com.ms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Identity {
    private final String name;
    private final String version;
    
    @JsonCreator
    public Identity(@JsonProperty("name") String name,
                      @JsonProperty("version") String version) {
        this.name = name;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }
}

