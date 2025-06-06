package com.example.demo.todolist;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/create_task")
    public TaskModel create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        System.out.println("chegou no controller" + request.getAttribute("userId"));

        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        return this.taskRepository.save(taskModel);
    }
}
