package com.mtr.springbootactivemqdynamodb.service;

import com.mtr.springbootactivemqdynamodb.entity.Product;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamoDbOperationService {

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private DynamoDbTable<Product> movieTable;

    public Product saveData(Product movieDetails) {

        return dynamoDbTemplate.save(movieDetails);
    }

    public Product updateData(Product movieDetails) {

        return dynamoDbTemplate.update(movieDetails);
    }

    public void deleteByObject(Product movieDetails) {

        dynamoDbTemplate.delete(movieDetails);
    }

    public void deleteById(String id) {

        Key key = Key.builder().partitionValue(id).build();
        dynamoDbTemplate.delete(key, Product.class);
    }

    public Product findById(String hashKey) {

        Key key = Key.builder().partitionValue(hashKey).build();
        return dynamoDbTemplate.load(key, Product.class);
    }

    public List<Product> scanDataByGenre(String genre) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":val1", AttributeValue.fromS(genre));

        Expression filterExpression = Expression.builder()
                .expression("genre = :val1")
                .expressionValues(expressionValues)
                .build();

        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                .filterExpression(filterExpression).build();

        PageIterable<Product> returnedList = dynamoDbTemplate.scan(scanEnhancedRequest,
                Product.class);

        return returnedList.items().stream().collect(Collectors.toList());
    }

    public PageIterable<Product> queryData(String partitionKey, String genre) {

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":value", AttributeValue.fromS(genre));

        Expression filterExpression = Expression.builder()
                .expression("genre = :val1")
                .expressionValues(expressionValues)
                .build();

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(
                        Key.builder()
                                .partitionValue(partitionKey)
                                .build());

        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .filterExpression(filterExpression)
                .build();

        return dynamoDbTemplate.query(queryRequest, Product.class);
    }


}
