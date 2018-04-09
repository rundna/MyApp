package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AttachementByType {

    @JsonProperty("trello")
    private Trello trello;
}
