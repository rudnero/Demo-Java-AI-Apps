package com.otus.java.advanced.domain;

import dev.langchain4j.service.UserMessage;

public interface StudentForm {

    @UserMessage("Извлеки информацию о студенте из описания {{it}}")
    Student studentExtractor(String msg);
}
