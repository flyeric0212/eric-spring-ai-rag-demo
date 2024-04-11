package top.flyeric.ai.openai;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ChatController {

    private final OpenAiChatClient chatClient;
    private final RagService ragService;

    @Autowired
    public ChatController(OpenAiChatClient chatClient, RagService ragService) {
        this.chatClient = chatClient;
        this.ragService = ragService;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return new LinkedHashMap<>(){{
            put("question", message);
            put("answer",chatClient.call(message));
        }};
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }

    @GetMapping("/rag")
    public Map<String, String> rag(@RequestParam(value = "question", defaultValue = "What is the largest trend of 2023?") String question,
                                   @RequestParam(value = "rag", defaultValue = "true") boolean rag) {
        String answer = ragService.generate(question, rag);
        return new LinkedHashMap<>(){{
            put("question", question);
            put("answer", answer);
        }};
    }
}
