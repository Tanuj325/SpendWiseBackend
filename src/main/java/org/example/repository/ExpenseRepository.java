package org.example.repository;

import org.example.entity.Expense;
import org.bson.types.ObjectId;
import org.example.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, ObjectId> {

    List<Expense> findByUserId(ObjectId userId);
}