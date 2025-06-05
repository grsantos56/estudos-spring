package com.example.demo.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create_task")
    public TaskModel create(@RequestBody TaskModel taskModel){
        System.out.println("chegou no controller");
        return this.taskRepository.save(taskModel);
    }
}
