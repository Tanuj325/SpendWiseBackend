package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    @JsonIgnore  // FIX: hide the raw ObjectId field from Jackson to avoid duplicate "id" field
    private ObjectId id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    // FIX: serialize expenseIds as hex strings, not raw ObjectId objects
    @JsonIgnore
    private List<ObjectId> expenseIds = new ArrayList<>();

    // FRONTEND KO STRING ID DENE KE LIYE
    @JsonProperty("id")
    public String getIdString() {
        if (id == null) {
            return null;
        }
        return id.toHexString();
    }

    // FIX: expose expenseIds as a list of hex strings so frontend gets clean string IDs
    @JsonProperty("expenseIds")
    public List<String> getExpenseIdStrings() {
        if (expenseIds == null) return new ArrayList<>();
        List<String> result = new ArrayList<>();
        for (ObjectId oid : expenseIds) {
            result.add(oid.toHexString());
        }
        return result;
    }
}