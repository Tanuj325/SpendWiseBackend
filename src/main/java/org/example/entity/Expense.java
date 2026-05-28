package org.example.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "expenses")
public class Expense {

    @Id
    private ObjectId id;

    private String description;
    private double amount;
    private String category;
    private Date date = new Date();

    private ObjectId userId;
}