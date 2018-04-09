package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Badges {

    @JsonProperty("votes")
    private int votes;
    @JsonProperty("attachementsByType")
    private AttachementByType attachementsByType;
}
