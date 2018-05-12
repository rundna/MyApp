package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    private static final Logger LOGGER= LoggerFactory.getLogger(TrelloClient.class);

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("kkg7");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException{
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/kkg7/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
       // System.out.println(restTemplate.getForObject(uri, TrelloBoardDto[].class).toString());
        System.out.println(trelloClient.getTrelloBoards());
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }
    @Test
    public void shouldCreateCard() throws URISyntaxException{
        Badges badges = new Badges();
        Trello trello = new Trello();
        trello.setBoard(3);
        trello.setCard(4);
        AttachementByType att = new AttachementByType();
        att.setTrello(trello);
        badges.setVotes(14);
        badges.setAttachementsByType(att);
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Task", "Test description", "top", "test_id", badges);

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20Task&desc=Test%20description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                new Badges()
        );
        when(restTemplate.postForObject(uri,null,CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
        assertEquals(3, badges.getAttachementsByType().getTrello().getBoard());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException{
        //Given

        URI url = new URI("http://test.com/members/kkg7/boards?key=test&token=test&fields=name,id&lists=all");

        try {
            TrelloBoardDto[] noBoardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            List<TrelloBoardDto> result = Arrays.asList(Optional.ofNullable(noBoardsResponse).orElse(new TrelloBoardDto[0]));
            //System.out.println(result);
            assertEquals(0,result.size());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }



    }
}