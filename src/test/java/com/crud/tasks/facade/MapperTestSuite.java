package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

        TrelloCardDto trelloCardDto = new TrelloCardDto("card test","card desc","top","test_id", new Badges());

        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListsDto);
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(trelloBoardsDto);
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        List<TrelloListDto> trelloListDto2 = trelloMapper.mapToListDto(trelloList);
        List<TrelloBoardDto> trelloBoardDto2 = trelloMapper.mapToBoardsDto(trelloBoard);
        TrelloCardDto trelloCardDto2 = trelloMapper.mapToCardDto(trelloCard);

        assertEquals("1", trelloList.get(0).getId());
        assertEquals("my_task", trelloBoard.get(0).getName());
        assertEquals("card desc", trelloCard.getDescription());
        assertEquals("1", trelloListDto2.get(0).getId());
        assertEquals("my_task", trelloBoardDto2.get(0).getName());
        assertEquals("card desc", trelloCardDto2.getDescription());

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
