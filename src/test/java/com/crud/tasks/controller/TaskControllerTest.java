package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private DbService service;

    @Test
    public void fetchTasks() throws Exception {
            List<Task> taskList = new ArrayList<>();
            taskList.add(new Task(2L, "Task List", "Task content"));

            List<TaskDto> taskListDto = new ArrayList<>();
            taskListDto.add(new TaskDto(5L,"TaskDto List","TaskDto content"));
            Gson gson = new Gson();
            String jsonContent = gson.toJson(taskListDto);

            when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);
            when(service.getAllTasks()).thenReturn(taskList);

            mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", is(taskListDto.get(0).getId().intValue())))
                    .andExpect(jsonPath("$[0].title", is(taskListDto.get(0).getTitle())))
                    .andExpect(jsonPath("$[0].content", is(taskListDto.get(0).getContent())));
        }

    @Test
    public void fetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task", "Task content");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(new Task()));

        //When & Then
        mockMvc.perform(get("/v1/task/getTask/").contentType(MediaType.APPLICATION_JSON)
                .param("taskId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title",is("Task")))
                .andExpect(jsonPath("$.content",is("Task content")));
    }

    @Test
    public void createTask() throws Exception{
        TaskDto taskDto =new TaskDto(3L, "Task List", "Task content");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

       // when(service.saveTask(task)).thenReturn(task);

        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTask() throws Exception{
        TaskDto taskDto =new TaskDto(3L, "Task List", "Task content");
        Task task =new Task(3L, "Task List", "Task content");
        Gson gson = new Gson();
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        String jsonContent = gson.toJson(taskDto);

        when(service.saveTask(any())).thenReturn(task);

        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("Task List")))
                .andExpect(jsonPath("$.content", is("Task content")));

    }

    @Test
    public void deleteTask() throws Exception{
        Task task =new Task(3L, "Task List", "Task content");
        // TaskDto taskDto = taskMapper.mapToTaskDto(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        service.delete(task.getId());

        this.mockMvc.perform(delete("/v1/task/deleteTask").contentType(MediaType.APPLICATION_JSON)
                .param("taskId","3")
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}