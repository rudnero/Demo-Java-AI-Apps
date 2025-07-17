package com.otus.java.advanced.domain.ai;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.inject.Singleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Singleton
public class TeacherAdvancedContextTools {

    @Tool("В случае преподавателя курса Java Advanced бери программу курса из источника")
    public String getWebPageContentJavaAdvance(@P("Источник") String url) throws IOException {
            Document jsoupDocument = Jsoup.connect(url).get();
            return jsoupDocument.body().text();
    }
}
