package com.mtr.springbootactivemqdynamodb.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Product {

    private String id;
    private String productInfo;
    private String productDesc;
    private String productionInfo;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("productInfo")
    public String getProductInfo() {
        return productInfo;
    }
    @DynamoDbAttribute("productDesc")
    public String getProductDesc() {
        return productDesc;
    }
    @DynamoDbAttribute("productionInfo")
    public String getProductionInfo() {
        return productionInfo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductionInfo(String productionInfo) {
        this.productionInfo = productionInfo;
    }
}
