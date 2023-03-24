package com.javarush.service;

import com.javarush.dao.TaskDAO;
import com.javarush.domain.Status;
import com.javarush.domain.Task;
import com.javarush.dto.TaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskDAO taskDAO;

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskDAO.findAll(pageable);
    }

    public void deleteTask(Integer id){
        taskDAO.deleteTaskById(id);
    }

    public void createTask(TaskDTO taskDTO){
        Status status = Status.valueOf(taskDTO.getStatus());
        Task task = new Task();
        task.setStatus(status);
        task.setDescription(taskDTO.getDescription());
        taskDAO.save(task);
    }

    public void updateTask(TaskDTO taskDTO){
        Optional<Task> optionalTask = taskDAO.findById(taskDTO.getId());
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setDescription(taskDTO.getDescription());
            task.setStatus(Status.valueOf(taskDTO.getStatus()));
            taskDAO.save(task);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
}
