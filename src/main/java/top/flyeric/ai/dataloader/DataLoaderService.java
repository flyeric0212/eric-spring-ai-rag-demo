package top.flyeric.ai.dataloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DataLoaderService {
    private final VectorStore vectorStore;
    private final JdbcTemplate jdbcTemplate;
    private final ResourcePatternResolver resourceResolver;

    public DataLoaderService(VectorStore vectorStore, JdbcTemplate jdbcTemplate, ResourcePatternResolver resourceResolver) {
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
        this.resourceResolver = resourceResolver;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM vector_store";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void delete() {
        String sql = "DELETE FROM vector_store";
        jdbcTemplate.update(sql);
    }

    private List<Resource> getFiles(String dirPath) throws IOException {
        List<Resource> fileList = new ArrayList<>();
        Resource[] resources = resourceResolver.getResources("classpath:" + dirPath + "/*");
        for (Resource resource : resources) {
            if (resource.exists()) {
                fileList.add(resource);
            }
        }
        return fileList;
    }

    private List<Document> getDocuments(String dirPath) throws IOException {
        List<Resource> files = getFiles(dirPath);
        List<Document> documents = new ArrayList<>();

        files.forEach(fileResource -> {
            DocumentReader reader = getReader(fileResource);
            if (reader != null) {
                documents.addAll(reader.get());
            }
        });
        return documents;
    }

    private DocumentReader getReader(Resource documentResource) {
        String fileName = documentResource.getFilename();
        assert StringUtils.isNotBlank(fileName);
        if (fileName.endsWith(".pdf")) {
            log.info("Parsing PDF document");
            PdfDocumentReaderConfig pdfDocumentReaderConfig = PdfDocumentReaderConfig.builder()
                    .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                            .withNumberOfBottomTextLinesToDelete(3)
                            .withNumberOfTopPagesToSkipBeforeDelete(1)
                            .build())
                    .withPagesPerDocument(1)
                    .build();
            return new PagePdfDocumentReader(documentResource, pdfDocumentReaderConfig);
        } else if (fileName.endsWith(".txt")) {
            log.info("Parsing TXT document");
            return new TextReader(documentResource);
        } else if (fileName.endsWith(".json")) {
            log.info("Parsing JSON document");
            return new JsonReader(documentResource);
        }
        return null;
    }

    public void load() throws IOException {
        List<Document> documents = getDocuments("data");
        if (!CollectionUtils.isEmpty(documents)) {
            var textSplitter = new TokenTextSplitter();
            log.info("Parsing document, splitting, creating embeddings and storing in vector store...  this will take a while.");
            this.vectorStore.accept(textSplitter.apply(documents));
            log.info("Done parsing document, splitting, creating embeddings and storing in vector store");
        } else {
            throw new RuntimeException("No reader found for document");
        }
    }

}
