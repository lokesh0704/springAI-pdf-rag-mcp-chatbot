# Spring AI PDF RAG Chatbot

A simple Retrieval-Augmented Generation (RAG) application built using Spring AI, Java, PostgreSQL (PGVector), and OpenAI

This project reads PDF documents, converts them into vector embeddings, stores them in PGVector, and retrieves relevant content to answer user questions using an LLM.

## Features

- Upload and process PDF documents
- Generate embeddings using AI models
- Store vectors in PostgreSQL with PGVector
- Retrieve relevant document chunks for user queries
- Chat interface powered by Spring AI
- Easy to switch between OpenAI, Ollama, and other supported models

## Tech Stack

- Java 17
- Spring Boot
- Spring AI
- PostgreSQL + PGVector
- OpenAI / Ollama
- Docker

## How It Works

1. Read PDF documents
2. Split content into smaller chunks
3. Generate embeddings for each chunk
4. Store embeddings in PGVector
5. User asks a question
6. Relevant chunks are retrieved from the vector database
7. LLM generates an answer using the retrieved context

## Running the Project

### Start PostgreSQL with PGVector

```bash
docker compose up -d
```

### Run the Application

```bash
mvn spring-boot:run
```

### Access API

```text
http://localhost:8080
```

## Learning Outcome

This project helped me understand:

- RAG architecture
- Vector databases and embeddings
- Spring AI integrations
- LLM-powered applications
- Document retrieval using PGVector

---

Feel free to explore the code and share suggestions or improvements.

### Author

**Lokesh Mahale**  
Java Developer | Spring Boot | Microservices | Gen-AI Enthusiast
