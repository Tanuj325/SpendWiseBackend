package org.example.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.entity.Expense;
import org.example.entity.User;
import org.example.repository.ExpenseRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Expense addExpense(Expense expense, ObjectId userId) {
        expense.setId(null);
        expense.setUserId(userId);
        if (expense.getDate() == null) {
            expense.setDate(new java.util.Date());
        }
        Expense saved = expenseRepository.save(expense);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (user.getExpenseIds() == null) {
                user.setExpenseIds(new java.util.ArrayList<>());
            }
            user.getExpenseIds().add(saved.getId());
            userRepository.save(user);
        }
        return saved;
    }

    public List<Expense> getUserExpenses(String userId) {

        return expenseRepository.findByUserId(
                new ObjectId(userId)
        );
    }
}