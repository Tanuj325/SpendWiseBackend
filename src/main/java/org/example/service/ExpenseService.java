package org.example.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.entity.Expense;
import org.example.entity.User;
import org.example.repository.ExpenseRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
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
            expense.setDate(new Date());
        }

        Expense saved = expenseRepository.save(expense);

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            if (user.getExpenseIds() == null) {
                user.setExpenseIds(new ArrayList<>());
            }
            user.getExpenseIds().add(saved.getId());
            userRepository.save(user);
        }

        return saved;
    }

    public List<Expense> getUserExpenses(String userId) {
        return expenseRepository.findByUserId(new ObjectId(userId));
    }

    public Page<Expense> getUserExpenses(
            String userId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return expenseRepository.findByUserId(
                new ObjectId(userId),
                pageable
        );
    }

    public Expense updateExpense(
            Expense newExpense,
            ObjectId userId,
            ObjectId expenseId
    ) {

        Expense oldExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!oldExpense.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        if (newExpense.getDescription() != null) {
            oldExpense.setDescription(newExpense.getDescription());
        }

        if (newExpense.getAmount() != null) {
            oldExpense.setAmount(newExpense.getAmount());
        }

        if (newExpense.getCategory() != null) {
            oldExpense.setCategory(newExpense.getCategory());
        }

        if (newExpense.getDate() != null) {
            oldExpense.setDate(newExpense.getDate());
        }

        return expenseRepository.save(oldExpense);
    }

    public void deleteExpense(ObjectId userId, ObjectId expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        userRepository.findById(userId).ifPresent(user -> {
            user.getExpenseIds().removeIf(id -> id.equals(expenseId));
            userRepository.save(user);
        });

        expenseRepository.delete(expense);
    }
}