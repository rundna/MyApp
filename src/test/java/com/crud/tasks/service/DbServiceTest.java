package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;
//    @InjectMocks
//    private RestTemplate restTemplate;

    @Test
    public void testDbServiceMethods(){

        TaskDto taskDto = new TaskDto();
        //taskDto.setId(3l);
        taskDto.setTitle("db service task");
        taskDto.setContent("test content");
        Task task = dbService.saveTask(taskMapper.mapToTask(taskDto));
        dbService.saveTask(taskMapper.mapToTask(taskDto));


        Optional taskItem = dbService.getTask(task.getId().longValue());
        Task taskItem2 = (Task) taskItem.orElse(taskDto);
        dbService.delete(taskItem2.getId());
        List<Task> taskList = dbService.getAllTasks();

        dbService.getAllTasks().forEach(taskId->dbService.delete(taskId.getId()));

        assertEquals(1, taskList.size());



    }


}
