package com.otus.java.advanced.controller;

import com.otus.java.advanced.domain.ai.service.Teacher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class AITeacher {

    private final Teacher teacher;

    public AITeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @GetMapping("/plan/{lang}")
    public List<String> planGenerator (@PathVariable("lang") String lang) {
        return teacher.lessonPlan(lang).content();
    }

    @PostMapping("/answer/{lang}")
    public List<String> answer(@PathVariable("lang") String lang, @RequestBody String question) {
        return teacher.answer(lang, question)
                .content();
    }

    @PostMapping("/answer/course/java-advanced")
    public List<String> answerJavaAdvanced(@RequestBody String question) {
        return teacher.answerJavaAdvanced(question)
                .content();
    }
}
