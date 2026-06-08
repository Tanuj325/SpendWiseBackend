package org.example.repository;

import org.bson.types.ObjectId;
import org.example.entity.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, ObjectId> {

    List<Expense> findByUserId(ObjectId userId);

    Page<Expense> findByUserId(ObjectId userId, Pageable pageable);
}