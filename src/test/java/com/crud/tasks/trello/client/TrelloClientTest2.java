package com.crud.tasks.trello.client;

import com.crud.tasks.domain.Badges;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
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
import static org.mockito.Mockito.when;

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
        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void testCreateTrelloCardByService() throws URISyntaxException{
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Task", "Test description", "top", "test_id", new Badges());

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token",trelloConfig.getTrelloToken())
                .queryParam("name",trelloCardDto.getName())
                .queryParam("desc",trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId()).build().encode().toUri();
        System.out.println(url);

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new Badges()
        );
        when(restTemplate.postForObject(url,null,CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        CreatedTrelloCardDto newCard = trelloService.createTrelloCard(trelloCardDto);

        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

}
