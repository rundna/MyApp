package com.crud.tasks.trello.client;

import com.crud.tasks.domain.Badges;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloClientTest2 {
    @Autowired
    private TrelloConfig trelloConfig;
    @Autowired
    private TrelloService trelloService;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testFetchBoard() throws URISyntaxException {
        //Given

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>()); //, new ArrayList<>(), new Badges());

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUser() + "/boards/")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("id", trelloConfig.getTrelloBoardId())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        System.out.println(url);
        //URI uri = new URI("http://test.com/members/kkg7/boards?key=test&token=test&fields=name,id&lists=all");
        //when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloService.fetchTrelloBoards();
        //System.out.println(fetchedTrelloBoards.get(0).getLists().toArray());

        //Then
        assertEquals(3, fetchedTrelloBoards.size());
        assertEquals(trelloConfig.getTrelloBoardId(), fetchedTrelloBoards.get(0).getId());
        assertEquals("Kodilla Application", fetchedTrelloBoards.get(0).getName());
        //assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists().toString());
    }

    @Test
    public void testTrelloCardExist() throws URISyntaxException{
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Task", "Test description", "top", "test_id", new Badges());

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards/" + trelloConfig.getTrelloCardId())
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token",trelloConfig.getTrelloToken())
                .queryParam("name",trelloCardDto.getName())
                .queryParam("desc",trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId()).build().encode().toUri();
        System.out.println(url);



//        assertEquals("1", newCard.getId());
//        assertEquals("Test task", newCard.getName());
//        assertEquals("http://test.com", newCard.getShortUrl());
    }

}
