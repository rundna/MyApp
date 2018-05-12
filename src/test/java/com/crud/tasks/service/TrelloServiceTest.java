package com.crud.tasks.service;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    TrelloService trelloService;
    @Mock
    TrelloClient trelloClient;


    @Test
    public void testTrelloService() {
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        when(trelloClient.getTrelloBoards()).thenReturn(Arrays.asList(trelloBoards));
        List<TrelloBoardDto> board = trelloService.fetchTrelloBoards();

        System.out.println(trelloBoards);
        System.out.println(board.size());

        assertEquals(1,board.size());

    }
}
