package com.cf.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class TaskmasterController {

    private S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskmasterController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getAllTasks(@PathVariable UUID id) {
        return taskRepository.findById(id).get();
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

    @PostMapping("tasks/{id}/images")
    public Task addImage(@PathVariable UUID id,
                                 @RequestPart(value = "file") MultipartFile file) {
        String imageUrl = this.s3Client.uploadFile(file);
        Task task = taskRepository.findById(id).get();
        task.setImageUrl(imageUrl);
        String tmpUrl = imageUrl.substring(0, imageUrl.length() - 4) + "-thumb" + imageUrl.substring(imageUrl.length() - 4);
        tmpUrl = tmpUrl.substring(0, 35) + "resized" + tmpUrl.substring(35);
        task.setThumbUrl(tmpUrl);
        taskRepository.save(task);
        return task;
    }
}
