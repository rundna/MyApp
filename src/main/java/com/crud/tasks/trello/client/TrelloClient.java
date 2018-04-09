package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.CreatedTrelloCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.api.user}")
    private String trelloUser;

    @Value("${trello.app.card}")
    private String trelloCardId;

    @Autowired
    private RestTemplate restTemplate;

    // public List<TrelloBoardDto> getTrelloBoards(){
     public List<TrelloBoardDto> getTrelloBoards(){

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUser + "/boards/")
                .queryParam("key",trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields","name,id")
                .queryParam("lists","all").build().encode().toUri();
        TrelloBoardDto[] boardsResponse=restTemplate.getForObject(url,TrelloBoardDto[].class);
         System.out.println(url);
        if (boardsResponse !=null){
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){

         URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                 .queryParam("key", trelloAppKey)
                 .queryParam("token",trelloToken)
                 .queryParam("name",trelloCardDto.getName())
                 .queryParam("desc",trelloCardDto.getDescription())
                 .queryParam("pos",trelloCardDto.getPos())
                 .queryParam("idList",trelloCardDto.getListId()).build().encode().toUri();
        System.out.println(url);
        return restTemplate.postForObject(url,null,CreatedTrelloCard.class);

     }
    public CreatedTrelloCard getBadge() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards/" +trelloCardId)
                //.queryParam("id",trelloCardId)
                .queryParam("fields","name,badges")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken).build().encode().toUri();
            System.out.println(url);
        return restTemplate.getForObject(url, CreatedTrelloCard.class);

    }
}
