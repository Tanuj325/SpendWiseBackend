package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "expenses")
public class Expense {

    @Id
    @JsonIgnore  // FIX: hide raw ObjectId; expose as string below
    private ObjectId id;

    private String description;
    private double amount;
    private String category;
    private Date date = new Date();

    @JsonIgnore  // FIX: hide raw ObjectId userId; expose as string below
    private ObjectId userId;

    @JsonProperty("id")
    public String getIdString() {
        if (id == null) return null;
        return id.toHexString();
    }

    @JsonProperty("userId")
    public String getUserIdString() {
        if (userId == null) return null;
        return userId.toHexString();
    }
}