package com.cf.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskmasterController {
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public @ResponseBody Task addNewTask(@ModelAttribute Task task) {
        task.setStatus("Available");
        taskRepository.save(task);
        return taskRepository.findById(task.getId()).get();
    }

    @PutMapping("/tasks/{id}/state")
    public void updateTask(@PathVariable UUID id) {
        Task task = taskRepository.findById(id).get();
        String status = task.getStatus();
        if (status.equals("Available")) {
            task.setStatus("Assigned");
        } else if (status.equals("Assigned")) {
            task.setStatus("Accepted");
        } else if (status.equals("Accepted")) {
            task.setStatus("Finished");
        }
        taskRepository.save(task);
    }

    @DeleteMapping("tasks/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskRepository.deleteById(id);
    }
}
