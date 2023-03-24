package com.javarush.controller;

import com.javarush.domain.Status;
import com.javarush.domain.Task;
import com.javarush.dto.TaskDTO;
import com.javarush.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@AllArgsConstructor
public class MainController {

    private final TaskService service;
    @GetMapping("/")
    public String getTasks(Model model,
                           @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        Page<Task> page = service.getAllTasks(pageable);
        model.addAttribute("page", page);

        int totalPages = page.getTotalPages();
        List<String> statusNames = Arrays.stream(Status.values())
                .map(status->status.getStatusName())
                .collect(Collectors.toList());
        model.addAttribute("statusNames", statusNames);
        model.addAttribute("taskDTO", new TaskDTO());
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("page", page);
        return "main";
    }

    @Transactional
    @PostMapping("/")
    public String createTask(Model model, @ModelAttribute("taskDTO") @Valid TaskDTO taskDTO, BindingResult bindingResult, @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
//        if (bindingResult.hasErrors()){
//            return "main";
//        }
        service.createTask(taskDTO);
        Page<Task> page = service.getAllTasks(pageable);
        Integer lastPage = page.getTotalPages()-1;
        return "redirect:/?page=" + lastPage;
    }

    @Transactional
    @PostMapping(path="/", consumes = "application/json")
    public void updateTask(Model model, @RequestBody TaskDTO taskDTO){
        service.updateTask(taskDTO);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteTask(Model model, @PathVariable Integer id){
        if(Objects.isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        service.deleteTask(id);
    }
}
