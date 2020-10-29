package com.sliit.delivery.data;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DynamoDBManager implements DBManager {

    ObjectMapper objectMapper;

    @Autowired
    DynamoDBConfiguration dbConfig;

    public DynamoDBManager() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public boolean persistData(Object data, String tblName, String partitionKey, String partitionValue) throws JsonProcessingException {

        createTables(tblName, partitionKey);
        String json = this.objectMapper.writeValueAsString(data);
        DynamoDB dynamoDB = new DynamoDB(dbConfig.getDynamoDbClient());
        Table table = dynamoDB.getTable(tblName);
        table.putItem(new Item().withPrimaryKey(partitionKey, partitionValue).withJSON("details",
                json));
        return true;
    }

    @Override
    public Object getDataItem(String tblName, String partitionKey, String partitionValue, Class c) throws JsonProcessingException {

        DynamoDB dynamoDB = new DynamoDB(dbConfig.getDynamoDbClient());
        Table table = dynamoDB.getTable(tblName);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(partitionKey, partitionValue);
        Item outcome = table.getItem(spec);
        Object data = this.objectMapper.readValue(outcome.getJSONPretty("details"), c);
        return data;
    }

    @Override
    public List<Object> getAll(String tblName, Class c) throws JsonProcessingException {

        List<Object> dataList = new ArrayList<>();
        ScanResult result = null;
        do {
            ScanRequest req = new ScanRequest();
            req.setTableName(tblName);
            if (result != null) {
                req.setExclusiveStartKey(result.getLastEvaluatedKey());
            }
            result = dbConfig.getDynamoDbClient().scan(req);
            List<Map<String, AttributeValue>> rows = result.getItems();

            for (Map<String, AttributeValue> map : rows) {
                try {
                    Item i = ItemUtils.toItem(map.get("details").getM());
                    Object data = this.objectMapper.readValue(i.toJSON(), c);
                    dataList.add(data);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (result.getLastEvaluatedKey() != null);
        return dataList;
    }

    @Override
    public boolean deleteDataItem(String tblName, String partitionKey, String partitionValue) {
        DynamoDB dynamoDB = new DynamoDB(dbConfig.getDynamoDbClient());
        Table table = dynamoDB.getTable(tblName);
        DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey(partitionKey, partitionValue);
        table.deleteItem(spec);
        return true;
    }

    private void createTables(String tblName, String partitionKey) {
        List<KeySchemaElement> elementList = Arrays.asList(new KeySchemaElement(partitionKey, KeyType.HASH));
        List<AttributeDefinition> attributes = Arrays.asList(new AttributeDefinition(partitionKey, ScalarAttributeType.S));
        dbConfig.createTable(tblName, elementList, attributes);
    }
}
