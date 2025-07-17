package com.otus.java.advanced.domain;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface Teacher {

    @UserMessage("Сгенерируй план урока для курса онлайн курса {{it}} Advanced. План должен содержать только пункты тем.")
    Result<List<String>> lessonPlan(String languageName);

    @SystemMessage("Ты преподаватель онлайн курса {{languageName}} Advanced. " +
    "Отвечай на вопросы о {{languageName}} понятно, раскрывая тему чтобы студентам было понятно")
    String chat(@V("languageName") String languageName, @UserMessage String userMessage);

    @UserMessage("Сгенерируй план урока для курса онлайн курса Advanced. План должен содержать только пункты тем. Ориентируйся на информацию из ресурса {{it}}")
    String lessonPlanJavaAdvanced(String msg);

}
