package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.TrelloConfig;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTestSuite {
    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;
    @Mock
    private TrelloFacade trelloFacade;

    private static final Logger LOGGER= LoggerFactory.getLogger(TrelloClient.class);

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    public void testMappingBoard() throws URISyntaxException{

        //Given

        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");


        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());
        URI uri = new URI("http://test.com/members/kkg7/boards?key=test&token=test&fields=id,name&lists=all");


        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloFacade.fetchTrelloBoards();
        List<TrelloBoard> board = trelloMapper.mapToBoards(fetchedTrelloBoards);
        List<TrelloListDto> listDto = fetchedTrelloBoards.get(0).getLists();
        List<TrelloList> list = trelloMapper.mapToList(listDto);
        //Then
        assertEquals(1,board.size());
        assertEquals(new ArrayList<>(),list);
    }
    @Test
    public void testCardMapper() throws URISyntaxException{
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Task", "Test description", "top", "test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20Task&desc=Test%20description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                trelloCardDto.getName(),
                "http://test.com",
                new Badges()
        );
        when(restTemplate.postForObject(uri,null,CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        CreatedTrelloCardDto newCard = trelloFacade.createCard(trelloCardDto);

        TrelloCard card = trelloMapper.mapToCard(trelloCardDto);
        assertEquals("test_id", card.getListId());
        assertEquals("Test Task", card.getName());
        assertEquals("Test Description", card.getDescription());
        assertEquals("1",newCard.getId());
        assertEquals("Test Task",newCard.getName());
    }
}
