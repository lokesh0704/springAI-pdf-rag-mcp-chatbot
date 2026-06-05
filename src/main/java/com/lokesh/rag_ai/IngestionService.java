package com.lokesh.rag_ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class IngestionService implements CommandLineRunner {

    // Logger object for logging messages
    private static final Logger log =
            LoggerFactory.getLogger(IngestionService.class);

    // Spring AI Vector Store
    // Used to save document embeddings
    private final VectorStore vectorStore;

    // Constructor Injection
    // Spring automatically injects the configured VectorStore bean
    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Automatically executes when application starts
    @Override
    public void run(String... args) throws Exception {

        // Step 1:
        // Read PDF file from resources/docs folder
        PagePdfDocumentReader pdfReader =
                new PagePdfDocumentReader(
                        new ClassPathResource("docs/Lokesh_Introduction.pdf"));

        // Step 2:
        // Convert PDF into a list of Documents
        // Each page becomes a Document object
        var documents = pdfReader.get();

        // Step 3:
        // Split large documents into smaller chunks
        // Chunk size = 800 tokens
        var textSplitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .build();

        // Step 4:
        // Apply splitter to documents
        // Result = many smaller chunks
        var splitDocuments = textSplitter.apply(documents);

        
        // Step 5:
        // Generate embeddings and store them
        // in the configured vector database
        vectorStore.add(splitDocuments);

        // Step 6:
        // Log success message
        log.info("Vector store loaded successfully!");
    }
}