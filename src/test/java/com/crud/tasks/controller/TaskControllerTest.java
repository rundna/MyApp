package com.crud.tasks.controller;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
            Gson gson = new Gson();
            String jsonContent = gson.toJson(taskList);

            when(service.getAllTasks()).thenReturn(taskList);

            mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", is(2L)))
                    .andExpect(jsonPath("$[0].title", is("Task List")))
                    .andExpect(jsonPath("$[0].content", is("Task content")));
        }

    @Test
    public void fetchTask() throws Exception {
        Task task =new Task(2L, "Task List", "Task content");
        Optional<Task> taskO = Optional.ofNullable(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        when(service.getTask(2l)).thenReturn(taskO);

        mockMvc.perform(get("/v1/task/getTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2L)))
                .andExpect(jsonPath("$[0].title", is("Task List")))
                .andExpect(jsonPath("$[0].content", is("Task content")));
    }

    @Test
    public void createTask() throws Exception{
        Task task =new Task(3L, "Task List", "Task content");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        when(service.saveTask(task)).thenReturn(task);

        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3L)))
                .andExpect(jsonPath("$[0].title", is("Task List")))
                .andExpect(jsonPath("$[0].content", is("Task content")));

    }

    @Test
    public void updateTask() throws Exception{
        Task task =new Task(3L, "Task List", "Task content");
       // TaskDto taskDto = taskMapper.mapToTaskDto(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        when(service.saveTask(task)).thenReturn(task);

        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3L)))
                .andExpect(jsonPath("$[0].title", is("Task List")))
                .andExpect(jsonPath("$[0].content", is("Task content")));

    }

    @Test
    public void deleteTask() throws Exception{
        Task task =new Task(3L, "Task List", "Task content");
        // TaskDto taskDto = taskMapper.mapToTaskDto(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        service.delete(task.getId());

        this.mockMvc.perform(delete("/v1/task/deleteTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

    }
}