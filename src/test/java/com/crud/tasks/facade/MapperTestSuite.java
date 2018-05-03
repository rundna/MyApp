package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapperTestSuite {
    @InjectMocks
    private TaskMapper taskMapper;

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testTrelloMapping() {

        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("1", "my_task", trelloListsDto));

        TrelloCardDto trelloCardDto = new TrelloCardDto("card test","card desc","top","test_id");

        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListsDto);
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(trelloBoardsDto);
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        assertEquals("1", trelloList.get(0).getId());
        assertEquals("my_task", trelloBoard.get(0).getName());
        assertEquals("card desc", trelloCard.getDescription());

    }
    @Test
    public void testTaskMapping(){
        TaskDto taskDto = new TaskDto(1L,"task title","task content");
        Task task = taskMapper.mapToTask(taskDto);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        List<TaskDto> taskListDto = taskMapper.mapToTaskDtoList(taskList);

        assertEquals(1L,taskListDto.get(0).getId().longValue());

    }
}
