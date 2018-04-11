package com.crud.tasks.mapper;

import com.crud.tasks.domain.AttachementByType;
import com.crud.tasks.domain.Trello;
import com.crud.tasks.domain.TrelloListDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedTrelloCard {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortUrl")
    private String shortUrl;

    //@JsonProperty("badges")
    //private String badges;

    //@JsonProperty("votes")
    //private int votes;

    //@JsonProperty("attachements")
    //private AttachementByType attachements;

    //@JsonProperty("trello")
    //private Trello trello;

    //@JsonProperty("board")
    //private int board;
    //@JsonProperty("card")
    //private int card;




}
