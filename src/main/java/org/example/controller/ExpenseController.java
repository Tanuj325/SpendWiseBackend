package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.entity.Expense;
import org.example.repository.ExpenseRepository;
import org.example.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final ExpenseRepository expenseRepository;

    // ================= ADD EXPENSE =================

    @PostMapping("/{userId}")
    public Expense addExpense(
            @RequestBody Expense expense,
            @PathVariable String userId
    ) {

        return expenseService.addExpense(
                expense,
                new ObjectId(userId)
        );
    }

    // ================= GET ALL =================

    @GetMapping("/{userId}")
    public List<Expense> getExpenses(
            @PathVariable String userId
    ) {

        return expenseService.getUserExpenses(userId);
    }

    // ================= CATEGORY TOTAL =================

    @GetMapping("/category/{userId}")
    public Map<String, Double> getCategoryTotals(
            @PathVariable String userId
    ) {

        List<Expense> expenses =
                expenseService.getUserExpenses(userId);

        Map<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {

            if (e.getDate() == null) continue;

            map.put(
                    e.getCategory(),
                    map.getOrDefault(
                            e.getCategory(),
                            0.0
                    ) + e.getAmount()
            );
        }

        return map;
    }

    // ================= MONTHLY REPORT =================

    @GetMapping("/report/{userId}")
    public Map<String, Object> getMonthlyReport(
            @PathVariable String userId,
            @RequestParam int month,
            @RequestParam int year
    ) {

        List<Expense> expenses =
                expenseRepository.findByUserId(
                        new ObjectId(userId)
                );

        Map<String, Double> categoryWise =
                new HashMap<>();

        Map<Integer, Double> dayWise =
                new HashMap<>();

        double total = 0;

        for (Expense e : expenses) {

            if (e.getDate() == null) continue;

            LocalDate date = e.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (
                    date.getMonthValue() == month + 1
                            && date.getYear() == year
            ) {

                categoryWise.put(
                        e.getCategory(),
                        categoryWise.getOrDefault(
                                e.getCategory(),
                                0.0
                        ) + e.getAmount()
                );

                int day = date.getDayOfMonth();

                dayWise.put(
                        day,
                        dayWise.getOrDefault(
                                day,
                                0.0
                        ) + e.getAmount()
                );

                total += e.getAmount();
            }
        }

        Map<String, Object> response =
                new HashMap<>();

        response.put("categoryWise", categoryWise);

        response.put("dayWise", dayWise);

        response.put("total", total);

        return response;
    }

    // ================= LAST 6 MONTHS =================

    @GetMapping("/monthly/{userId}")
    public Map<String, Double> getLast6Months(
            @PathVariable String userId
    ) {

        List<Expense> expenses =
                expenseRepository.findByUserId(
                        new ObjectId(userId)
                );

        Map<String, Double> monthlyMap =
                new LinkedHashMap<>();

        String[] months = {
                "Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"
        };

        // LAST 6 MONTHS
        for (int i = 5; i >= 0; i--) {

            LocalDate date =
                    LocalDate.now().minusMonths(i);

            String key =
                    months[date.getMonthValue() - 1]
                            + " "
                            + date.getYear();

            monthlyMap.put(key, 0.0);
        }

        // FILL DATA
        for (Expense e : expenses) {

            if (e.getDate() == null) continue;

            LocalDate date = e.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            String key =
                    months[date.getMonthValue() - 1]
                            + " "
                            + date.getYear();

            if (monthlyMap.containsKey(key)) {

                monthlyMap.put(
                        key,
                        monthlyMap.get(key)
                                + e.getAmount()
                );
            }
        }

        return monthlyMap;
    }
}