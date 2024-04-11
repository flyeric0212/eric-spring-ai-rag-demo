package top.flyeric.ai.openai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RagService {

    @Value("classpath:/prompts/rag.st")
    private Resource ragPromptResource;

    @Value("classpath:/prompts/chatbot.st")
    private Resource chatbotPromptResource;

    private final ChatClient aiClient;
    private final VectorStore vectorStore;

    public RagService(ChatClient aiClient, VectorStore vectorStore) {
        this.aiClient = aiClient;
        this.vectorStore = vectorStore;
    }

    public String generate(String message, boolean rag) {
        Message systemMessage = getSystemMessage(message, rag);
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        log.info("Asking AI model to reply to question.");
        ChatResponse aiResponse = aiClient.call(prompt);
        log.info("AI responded.");
        return aiResponse.getResult().getOutput().getContent();
    }

    private Message getSystemMessage(String message, boolean rag) {
        if (rag) {
            log.info("Retrieving relevant documents");
            List<Document> similarDocuments = vectorStore.similaritySearch(message);
            log.info(String.format("Found %s relevant documents.", similarDocuments.size()));
            String documents = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining("\n"));
            SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(this.ragPromptResource);
            return systemPromptTemplate.createMessage(Map.of("documents", documents));
        } else {
            log.info("Not stuffing the prompt, using generic prompt");
            return new SystemPromptTemplate(this.chatbotPromptResource).createMessage();
        }
    }

}
