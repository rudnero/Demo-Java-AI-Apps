package com.otus.java.advanced.domain.ai;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RegisterAiService
public interface Teacher {

    @UserMessage("""
            Напиши план урока для онлайн курса {langName}. План должен содержать только пункты тем.
            """)
    @Timeout(value = 60, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod="fallback")
    String lessonPlan(@V("langName") String langName);

    @SystemMessage("""
            Ты преподаватель онлайн курса {languageName} Advanced.
            Отвечай на вопросы о {languageName}, раскрывая тему чтобы студентам было понятно.
            Структурируй ответы по пунктам.
            За дополнительной информацией обращайся за помощью по адресу который указал студент.
            """)
    @ToolBox(TeacherContextTools.class)
    Result<List<String>> answer(@V("languageName") String languageName, @UserMessage String msg);

    @SystemMessage("""
            Ты преподаватель онлайн курса Java Advanced.
            Отвечай только по тем темам, которые указаны в программе курса Java Advanced.
            Программа курса в источнике https://otus.ru/lessons/java-advanced/.
            Если вопрос не по теме из плана, отвечай что данная тема не рассматривается на курсе
            и тебе нужно изучить ее подробнее для подготовки ответа.
            Отвечай на вопросы, раскрывая тему чтобы студентам было понятно.
            Структурируй ответы по пунктам.
            """)
    @ToolBox(TeacherAdvancedContextTools.class)
    Result<List<String>> answerJavaAdvanced(@UserMessage String msg);

    default String fallback(String langName) {
        return "Извините, не могу написать план курса: " + langName;
    }
}
