# Spring AI RAG Demo

## Prerequisites

### Environment
- SpringBoot 3.2.4
- JDK 21
- Gradle 8.7
- OpenAI API Key
- Docker Compose
- Git

### OpenAI Credentials
Set the API key as an environment variable named OPENAI_API_KEY. 
Set the Base Url as an environment variable named OPENAI_BASE_URL.
E.g:
```bash
export OPENAI_API_KEY=sk-1234567890abcdef1234567890abcdef
export OPENAI_BASE_URL=https://api.openai.com/
```

### VectorStore Setup
```bash
docker-compose up -d
```
Later starts PGVector DB on localhost and port 5432.

OR 
ADD `developmentOnly 'org.springframework.boot:spring-boot-docker-compose'` into `build.gradle`

We can use the tool `DBeaver` or `IDEA Database` as GUI for postgres.

## Build And Run
```bash
./gradlw bootRun
```

## Data Load And Clean
```text
POST /data/load
GET /data/count
DELETE /data/delete
```

## Test
Execute http request, refer to the `.http` directory.
eg:
```bash
GET http://localhost:8080/ai/generate

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked

{
  "question": "Tell me a joke",
  "answer": "Why did the scarecrow win an award? Because he was outstanding in his field!"
}
```
Executed script: `POST http://localhost:8080/data/load`
```text
2024-04-10T22:55:52.459+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] t.f.ai.dataloader.DataLoaderService      : Parsing PDF document
2024-04-10T22:55:52.969+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] t.f.ai.dataloader.DataLoaderService      : Parsing document, splitting, creating embeddings and storing in vector store...  this will take a while.
2024-04-10T22:55:53.009+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 1
2024-04-10T22:55:53.156+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 4
2024-04-10T22:55:53.184+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 7
2024-04-10T22:55:53.188+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 10
2024-04-10T22:55:53.278+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 13
2024-04-10T22:55:53.330+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 16
2024-04-10T22:55:53.370+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 19
2024-04-10T22:55:53.397+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 22
2024-04-10T22:55:53.429+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 25
2024-04-10T22:55:53.455+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing PDF page: 28
2024-04-10T22:55:53.581+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.ai.reader.pdf.PagePdfDocumentReader  : Processing 37 pages
2024-04-10T22:55:53.606+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.612+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.617+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.623+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.625+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.631+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.636+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.645+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.650+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.656+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.664+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.669+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.676+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.682+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.687+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.693+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.699+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.704+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.710+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.713+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.717+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.722+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.729+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.736+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.741+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.751+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:55:53.757+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 3 chunks.
2024-04-10T22:55:53.762+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] o.s.a.transformer.splitter.TextSplitter  : Splitting up document into 2 chunks.
2024-04-10T22:56:52.916+08:00  INFO 18778 --- [spring-ai-rag-demo] [nio-8080-exec-3] t.f.ai.dataloader.DataLoaderService      : Done parsing document, splitting, creating embeddings and storing in vector store
```

RAG test：
```bash
### use RAG
GET http://localhost:8080/rag

{
  "question": "What is the largest trend of 2023?",
  "answer": "The largest trend of 2023 is the rise of Generative AI, powered by Large Language Models (LLMs) such as GPT-3 and GPT-4. This trend has gained significant prominence in the AI and ML industry, with widespread adoption being driven by technologies like ChatGPT."
}

GET http://localhost:8080/rag?question=开源大模型食用指南主要讲了什么？

{
  "question": "开源大模型食用指南主要讲了什么？",
  "answer": "开源大模型食用指南主要介绍了围绕开源大模型、针对国内初学者的教程。其中包括环境配置、本地部署、高效微调等技能的全流程指导，旨在简化开源大模型的部署、使用和应用流程，让更多普通学生、研究者更好地使用开源大模型。具体内容包括基于 AutoDL 平台的开源 LLM 环境配置指南，不同开源 LLM 的部署使用教程，开源 LLM 的部署应用指导，以及开源 LLM 的全量微调、高效微调方法等。这些内容旨在帮助学生和未来的从业者了解和熟悉开源大模型的食用方法。"
}

### without RAG
GET http://localhost:8080/rag?rag=false

{
  "question": "What is the largest trend of 2023?",
  "answer": "I'm sorry, but I don't have information on future trends as they are subject to change and evolve over time."
}
```


## Clean
If you start it manually, please execute the following script to clean up resources:
```bash
docker-compose down
```

## Reference
[Spring AI](https://spring.io/projects/spring-ai)

[RAG and Spring AI — Querying Your Own Documents with Open AI)](https://levelup.gitconnected.com/rag-and-spring-ai-querying-your-own-documents-with-open-ai-54b404eb7d08)
