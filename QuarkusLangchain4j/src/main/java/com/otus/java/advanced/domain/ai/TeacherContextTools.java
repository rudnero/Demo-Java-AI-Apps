package com.otus.java.advanced.domain.ai;


import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.inject.Singleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Singleton
public class TeacherContextTools {

    @Tool("При ответе на вопрос ученика опирайся на материал из источника")
    public String getWebPageContent(@P("Ссылка на страницу") String url) throws IOException {
        Document jsoupDocument = Jsoup.connect(url).get();
        return jsoupDocument.body().text();
    }
}
