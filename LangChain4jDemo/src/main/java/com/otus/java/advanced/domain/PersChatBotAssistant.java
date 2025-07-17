package com.otus.java.advanced.domain;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface PersChatBotAssistant {
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
