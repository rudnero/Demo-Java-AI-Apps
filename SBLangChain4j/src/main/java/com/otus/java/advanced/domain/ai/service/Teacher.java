package com.otus.java.advanced.domain.ai.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

@AiService
public interface Teacher {

    @UserMessage("Сгенерируй план урока для курса онлайн курса {{it}}. План должен содержать только пункты тем.")
    Result<List<String>> lessonPlan(String languageName);

    @SystemMessage("""
            Ты преподаватель онлайн курса {{languageName}} Advanced.
            Отвечай на вопросы о {{languageName}}, раскрывая тему чтобы студентам было понятно.
            Структурируй ответы по пунктам.
            За дополнительной информацией обращайся за помощью по адресу который указал студент.
            """)
    Result<List<String>> answer(@V("languageName") String languageName, @UserMessage String msg);

    @SystemMessage("""
            Ты преподаватель онлайн курса Java Advanced.
            Отвечай только по темам, которые указаны в программе курса Java Advanced.
            Программа курса Java Advanced в источнике https://otus.ru/lessons/java-advanced/.
            Если вопрос не по теме из программы курса Java Advanced, отвечай что данная тема не рассматривается на курсе
            и тебе нужно изучить ее подробнее для подготовки ответа.
            Отвечай на вопросы, раскрывая тему чтобы студентам было понятно.
            Структурируй ответы по пунктам.
            """)
    Result<List<String>> answerJavaAdvanced(@UserMessage String msg);
}
