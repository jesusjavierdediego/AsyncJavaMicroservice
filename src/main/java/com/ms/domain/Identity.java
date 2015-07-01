package com.ms.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Identity {
    private final String version;
    private final String name;
    
    @JsonCreator
    public Identity(@JsonProperty("version") String version,
                    @JsonProperty("name") String name) {
        this.version = version;
        this.name = name;
    }


    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }
}
