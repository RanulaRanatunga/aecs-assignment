package com.sliit.delivery.data;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class DynamoDBConfiguration {

    @Autowired
    Environment env;

    private String endpoint;
    private AmazonDynamoDB client;

    public AmazonDynamoDB getDynamoDbClient() {
        if (this.client == null) {
            try {
                this.endpoint = env.getProperty("db.host");
                client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(this.endpoint, "us-west-2"))
                        .build();
            } catch (Exception e) {
                System.out.println("Error : Couldn't create db connection");
            }
        }
        return this.client;
    }

    public boolean createTable(String tblName, List<KeySchemaElement> keyElements, List<AttributeDefinition> attributes) {
        DynamoDB dynamoDB = new DynamoDB(getDynamoDbClient());

        try {
            Table table = dynamoDB.createTable(tblName, keyElements, attributes,
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
            return true;
        } catch (ResourceInUseException e) {
            System.out.println("Table already created");
        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
        return false;
    }
}
