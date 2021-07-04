package com.example.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    private long count = 4;

    private AmazonDynamoDB dynamoDBClient;

    @Autowired
    public BookController(AmazonDynamoDB dynamoDBClient) {
        this.dynamoDBClient = dynamoDBClient;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        List<Book> result = new ArrayList<>();

        ScanRequest request = new ScanRequest().withTableName("book");
        ScanResult scanResult = dynamoDBClient.scan(request);

        for (Map<String, AttributeValue> item : scanResult.getItems()) {
            result.add(toBook(item));
        }

        return result;
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable int id) {
        Map<String, AttributeValue> values = new HashMap<>();
        values.put("id", new AttributeValue(String.valueOf(id)));
        GetItemResult result = dynamoDBClient.getItem("book", values);
        Map<String, AttributeValue> item = result.getItem();

        if(item != null) {
            return toBook(item);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Entity not found"
        );
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {

        book.setId(count++);

        Map<String, AttributeValue> values = new HashMap<>();
        values.put("name", new AttributeValue(book.getName()));
        values.put("author", new AttributeValue(book.getAuthor()));
        values.put("id", new AttributeValue(String.valueOf(book.getId())));
        dynamoDBClient.putItem("book", values);

        return book;
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        Book book = getBook(id);
        Map<String, AttributeValue> values = new HashMap<>();
        values.put("id", new AttributeValue(String.valueOf(id)));
        DeleteItemResult deleteItemResult = dynamoDBClient.deleteItem("book", values);
        System.out.println(deleteItemResult);
    }

    private Book toBook(Map<String, AttributeValue> item) {
        return new Book(Long.parseLong(item.get("id").getS()), item.get("name").getS(), item.get("author").getS());
    }

}
