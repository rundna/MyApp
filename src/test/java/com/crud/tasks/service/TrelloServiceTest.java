package com.crud.tasks.service;

import com.crud.tasks.controller.TrelloController;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @Mock
    TrelloService trelloService;
    @Mock
    TrelloClient trelloClient;
    @Mock
    TrelloConfig trelloConfig;
    @Mock
    RestTemplate restTemplate;

    private static final Logger LOGGER= LoggerFactory.getLogger(TrelloClient.class);

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloBoardId()).thenReturn("1");
        when(trelloConfig.getTrelloCardId()).thenReturn("2");
        when(trelloConfig.getTrelloUser()).thenReturn("test_user");
    }

    @Test
    public void testTrelloService() throws URISyntaxException {
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());


        URI uri = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUser() + "/boards/")
                .queryParam("key",trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("id",trelloConfig.getTrelloBoardId())
                .queryParam("fields","name,id")
                .queryParam("lists","all").build().encode().toUri();

        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoards);
        URI url = new URI("http://test.com/members/test_user/boards/?key=test&token=test&id=1&fields=name,id&lists=all");

        List<TrelloBoardDto> board = trelloService.fetchTrelloBoards();
        //String result = trelloConfig.getTrelloApiEndpoint() +" " + trelloConfig.getTrelloBoardId();
        //trelloController.getTrelloBoards();

        //System.out.println(result);
        System.out.println(board.size());

        System.out.println(uri);

        assertEquals(url,uri);
        assertEquals(0,board.size());

    }
}
