import com.otus.java.advanced.domain.*;

import com.otus.java.advanced.domain.repos.PersistentChatMemoryStore;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;

import static dev.langchain4j.model.LambdaStreamingResponseHandler.onPartialResponseAndErrorBlocking;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class DemoLangChain4J {

    private static final String OPEN_AI_GIGACHAT_PROXY= "http://localhost:8090/v1";
    private static final String OPEN_AI_SIMPLE_PROXY = "http://langchain4j.dev/demo/openai/v1";

    private static final OpenAiChatModel model = OpenAiChatModel.builder()
            .baseUrl(OPEN_AI_SIMPLE_PROXY)
            .apiKey("demo")
            .modelName(GPT_4_O_MINI)
            .logRequests(false)
            .logResponses(false)
            .build();

    private static final OpenAiChatModel modelJson = OpenAiChatModel.builder()
            .baseUrl(OPEN_AI_SIMPLE_PROXY)
            .apiKey("demo")
            .modelName(GPT_4_O_MINI)
            .responseFormat("json_schema")
            .strictJsonSchema(true)
            .build();

    private static final StreamingChatModel streamingModel = OpenAiStreamingChatModel.builder()
            .baseUrl(OPEN_AI_GIGACHAT_PROXY)
            .apiKey("demo")
            .modelName(GPT_4_O_MINI)
            .build();

    @Test
    void simpleChat() {
        var message = UserMessage.userMessage(Constants.MSG_HELLO);
        var request = ChatRequest.builder()
                .messages(message)
                .build();
        System.out.printf("OTUS: %s%n", message.singleText());
        var response = model.chat(request);
        System.out.printf("LLM: %s%n", response.aiMessage().text());

        var secondMessage = UserMessage.userMessage(Constants.ABOUT_ME);
        var secondRequest = ChatRequest.builder()
                .messages(message, response.aiMessage(), secondMessage)
                .build();
        System.out.printf("OTUS: %s%n", secondMessage.singleText());
        response = model.chat(secondRequest);
        System.out.printf("LLM: %s%n", response.aiMessage().text());
    }

    @Test
    void simpleStreamingChat() throws InterruptedException {
        onPartialResponseAndErrorBlocking(streamingModel, "Расскажи несколько шуток",
                System.out::print, Throwable::printStackTrace);
    }

    @Test
    void simpleChatMemory() {

        var chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();

        var bot = AiServices.builder(ChatBotAssistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        System.out.printf("OTUS: %s%n", Constants.MSG_HELLO);
        var answer = bot.chat(Constants.MSG_HELLO);
        System.out.printf("LLM: %s%n", answer);

        System.out.printf("OTUS: %s%n", Constants.ABOUT_ME);
        String answerWithName = bot.chat(Constants.ABOUT_ME);
        System.out.printf("LLM: %s%n", answerWithName);

        System.out.printf("OTUS: %s%n", Constants.CONTEXT_OTUS);
        String answerWithInfo = bot.chat(Constants.CONTEXT_OTUS);
        System.out.printf("LLM: %s%n", answerWithInfo);
    }

    @Test
    void simpleChatMemoryToken() {

        var chatMemory = TokenWindowChatMemory.builder()
                .maxTokens(300, new OpenAiTokenCountEstimator(GPT_4_O_MINI))
                .build();

        var bot = AiServices.builder(ChatBotAssistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        System.out.printf("OTUS: %s%n", Constants.MSG_HELLO);
        var answer = bot.chat(Constants.MSG_HELLO);
        System.out.printf("LLM: %s%n", answer);

        System.out.printf("OTUS: %s%n", Constants.ABOUT_ME);
        String answerWithName = bot.chat(Constants.ABOUT_ME);
        System.out.printf("LLM: %s%n", answerWithName);

        System.out.printf("OTUS: %s%n", Constants.CONTEXT_OTUS);
        String answerWithInfo = bot.chat(Constants.CONTEXT_OTUS);
        System.out.printf("LLM: %s%n", answerWithInfo);
    }

    @Test
    void chatMemoryPersistence() {
        var repo = new PersistentChatMemoryStore();

        var chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryStore(repo)
                .id(1)
                .build();

        var bot = AiServices.builder(ChatBotAssistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        System.out.printf("LLM: %s%n",  Constants.MSG_HELLO);
        var answer = bot.chat(Constants.MSG_HELLO);
        System.out.printf("LLM: %s%n", answer);

        System.out.printf("OTUS: %s%n", Constants.ONLY_CONTEXT_OTUS);
        String answerWithName = bot.chat( Constants.ONLY_CONTEXT_OTUS);
        System.out.printf("LLM: %s%n", answerWithName);

        System.out.printf("OTUS: %s%n", Constants.ABOUT_ME);
        String answerWithAAboutMe = bot.chat(Constants.ABOUT_ME);
        System.out.printf("LLM: %s%n", answerWithAAboutMe);
    }

    @Test
    void chatMemoryPersistenceContext() {
        var repo = new PersistentChatMemoryStore();

        var chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryStore(repo)
                .id(1)
                .build();

        var bot = AiServices.builder(ChatBotAssistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        System.out.printf("OTUS: %s%n", Constants.ABOUT_OTUS);
        String answerWithAAboutMe = bot.chat(Constants.ABOUT_OTUS);
        System.out.printf("LLM: %s%n", answerWithAAboutMe);
    }

    @Test
    void chatMemoryForEachUserPersistenceContext() {
        PersistentChatMemoryStore repo = new PersistentChatMemoryStore();

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(repo)
                .build();

        var bot = AiServices.builder(PersChatBotAssistant.class)
                .chatModel(model)
                .chatMemoryProvider(chatMemoryProvider)
                .build();

        System.out.printf("OTUS: %s%n",  Constants.MSG_HELLO);
        System.out.printf("LLM: %s%n", bot.chat(1,  Constants.MSG_HELLO));

        System.out.printf("OTUS: %s%n", Constants.ONLY_CONTEXT_OTUS);
        System.out.printf("LLM: %s%n", bot.chat(1,  Constants.ONLY_CONTEXT_OTUS));

        System.out.printf("OTUS: %s%n", Constants.ABOUT_OTUS);
        System.out.printf("LLM: %s%n", bot.chat(1, Constants.ABOUT_OTUS));

        System.out.printf("USER: %s%n", Constants.MSG_HELLO_USER);
        System.out.printf("LLM: %s%n", bot.chat(2, Constants.MSG_HELLO_USER));

        System.out.printf("USER: %s%n", Constants.ABOUT_OTUS);
        System.out.printf("LLM: %s%n", bot.chat(2, Constants.ABOUT_OTUS));
    }

    @Test
    void teacherConversation() {
        var aiService = AiServices.builder(Teacher.class)
                .chatModel(model)
                .build();
        var lang = "Java";
        var plan = aiService.lessonPlan(lang);
        System.out.printf("На создание плана потрачено %s токенов%n ",  plan.tokenUsage().totalTokenCount());
        var question = plan.content().get(8);
        System.out.printf("Student: %s%n", question);
        System.out.printf("LLM: %s%n", aiService.chat(lang, question));
    }

    @Test
    void form() {
        var aiService = AiServices.builder(StudentForm.class)
                .chatModel(model)
                .build();

        var student = aiService.studentExtractor("Студент 2010 года рождения по имени Игорь из города Москва");
        System.out.println(student);
    }

    @Test
    void javaAdvancedCourse() {
        var aiService = AiServices.builder(Teacher.class)
                .chatModel(model)
                .tools(new Tools())
                .build();

        System.out.println(aiService.lessonPlanJavaAdvanced("https://otus.ru/lessons/java-advanced"));
    }
}