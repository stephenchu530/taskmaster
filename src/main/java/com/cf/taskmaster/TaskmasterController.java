package com.cf.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/users/{assignee}/tasks")
    public List<Task> getAssigneeTasks(@PathVariable String assignee) {
        return taskRepository.findByAssignee(assignee);
    }

    @PostMapping("/tasks")
    public @ResponseBody Task addNewTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return task;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTask(@PathVariable UUID id) {
        Task task = taskRepository.findById(id).get();
        task.incrementStatus();
        taskRepository.save(task);
        return task;
    }

    @PutMapping ("/tasks/{id}/assign/{assignee}")
    public Task assignAssignee(@PathVariable UUID id, @PathVariable String assignee) {
        Task task = taskRepository.findById(id).get();
        task.setAssignee(assignee);
        taskRepository.save(task);
        return task;
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<UUID> deleteTask(@PathVariable UUID id) {
        taskRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
