package com.otus.java.advanced.domain.ai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TeacherContext {
    @Tool(name = "getWebPageContent", value = "При ответе на вопрос ученика опирайся на материал из источника")
    public String getWebPageContent(@P("Ссылка на страницу") String url) throws IOException {
        Document jsoupDocument = Jsoup.connect(url).get();
        return jsoupDocument.body().text();
    }

    @Tool(name = "getWebPageContentJavaAdvance", value = "В случае преподавателя курса Java Advanced, который отвечает только по темам, которые указаны в программе курса Java Advanced, бери программу курса из источника.")
    public String getWebPageContentJavaAdvance(@P("Ссылка на страницу") String url) throws IOException {
        Document jsoupDocument = Jsoup.connect(url).get();
        return jsoupDocument.body().text();
    }
}
