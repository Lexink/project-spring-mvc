package com.javarush.dao;

import com.javarush.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface TaskDAO extends Repository<Task, Integer> {
    Optional<Task> findById(Integer id);

    Page<Task> findAll(Pageable pageable);

    void deleteTaskById(Integer id);
    Task save(Task task);
}
