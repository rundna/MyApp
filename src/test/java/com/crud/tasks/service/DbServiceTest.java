package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
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
//    @InjectMocks
//    private RestTemplate restTemplate;

    @Test
    public void testDbServiceMethods(){
        Task task = new Task();
        task.setId(3l);
        task.setTitle("db service task");
        task.setContent("test content");
        dbService.saveTask(task);

        List<Task> taskList = dbService.getAllTasks();
        Optional taskId = dbService.getTask(task.getId());

        dbService.delete(task.getId());

        assertEquals(1, taskList.size());

    }


}
