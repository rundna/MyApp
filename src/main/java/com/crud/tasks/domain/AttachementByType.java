package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttachementByType {

    @JsonProperty("trello")
    private Trello trello;
}
