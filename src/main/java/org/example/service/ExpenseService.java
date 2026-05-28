package org.example.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.entity.Expense;
import org.example.entity.User;
import org.example.repository.ExpenseRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Expense addExpense(Expense expense, ObjectId userId) {
        expense.setDate(new Date());
        expense.setUserId(userId);
        Expense savedExpense = expenseRepository.save(expense);

        // ✅ UPDATE USER
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {

            if (user.getExpenseIds() == null) {
                user.setExpenseIds(new ArrayList<>());
            }else{
                user.getExpenseIds().add(savedExpense.getId());

            }

            userRepository.save(user);
        }

        return savedExpense;
    }

    public List<Expense> getUserExpenses(String userId) {
        return expenseRepository.findByUserId(new ObjectId(userId));
    }
}