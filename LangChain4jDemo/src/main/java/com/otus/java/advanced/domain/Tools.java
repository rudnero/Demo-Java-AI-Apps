package com.otus.java.advanced.domain;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Tools {
    @Tool("При составлении плана урока используй материал из источника")
    public String getWebPageContent(@P("Ссылка на страницу источника") String url) throws IOException {
        Document jsoupDocument = Jsoup.connect(url).get();
        return jsoupDocument.body().text();
    }
}
