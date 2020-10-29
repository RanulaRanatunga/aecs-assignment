package com.sliit.delivery.data;

import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.List;

public interface DBManager {

    boolean persistData(Object data, String tblName, String partitionKey, String partitionValue) throws JsonProcessingException;

    Object getDataItem(String tblName, String partitionKey, String partitionValue, Class c) throws JsonProcessingException;

    List<Object> getAll(String tblName, Class c) throws JsonProcessingException;

    boolean deleteDataItem(String tblName, String partitionKey, String partitionValue);
}
