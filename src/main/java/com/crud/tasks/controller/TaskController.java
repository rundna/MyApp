package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private DbService service;

    @RequestMapping(method= RequestMethod.GET, value="getTasks")
    public List<TaskDto> getTasks(){
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }
    @RequestMapping(method= RequestMethod.GET, value="getTask")
    public TaskDto getTask(Long taskId){
        return new TaskDto(1L,"test title","test_content");
        //return taskMapper.mapToTaskDto(service.getTaskById(2L));
    }
    @RequestMapping(method= RequestMethod.DELETE, value="deleteTask")
    public void deleteTask(String taskId){}

    @RequestMapping(method= RequestMethod.PUT, value="updateTask")
    public TaskDto updateTask(TaskDto task){
        return new TaskDto(1L,"edited test title"," edited_test_content");
    }
    @RequestMapping(method= RequestMethod.POST, value="createTask")
    public void createTask(TaskDto taskDto){}
}
