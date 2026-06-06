package com.lokesh.rag_ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class IngestionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);

    private final VectorStore vectorStore;


    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {

        // ✅ Check if vector store is already populated to avoid duplicate ingestion
        List<Document> existing = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("Lokesh")
                        .topK(1)
                        .build()
        );

        if (!existing.isEmpty()) {
            log.info("Vector store already populated. Skipping ingestion.");
            return;
        }

        log.info("Vector store empty. Starting ingestion...");

        // Step 1: Read PDF
        PagePdfDocumentReader pdfReader =
                new PagePdfDocumentReader(new ClassPathResource("docs/Lokesh_Introduction.pdf"));

        // Step 2: Get documents (one per page)
        var documents = pdfReader.get();

        // Step 3 & 4: Split into chunks of 800 tokens
        var splitDocuments = TokenTextSplitter.builder()
                .withChunkSize(800)
                .build()
                .apply(documents);

        // Step 5: Embed and store
        vectorStore.add(splitDocuments);

        log.info("Vector store loaded successfully! {} chunks stored.", splitDocuments.size());

    }
}