package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private List<ObjectId> expenseIds;

    // FRONTEND KO STRING ID DENE KE LIYE
    @JsonProperty("id")
    public String getIdString() {

        if (id == null) {
            return null;
        }

        return id.toHexString();
    }
}