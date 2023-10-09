package com.macnss.helpers;

import java.util.function.Supplier;

public class RetirementHelpers {

    public static double calculateRetirementPension(float salary, int totalWorkingDays) {
        if (totalWorkingDays <= 3240) {
            Supplier<Double> calculateMonthlyRetirementPension = () ->
                    salary * (50 / 100.0);
            double monthlyRetirementPension = calculateMonthlyRetirementPension.get();
            monthlyRetirementPension = adjustRetirementSalary((float) monthlyRetirementPension);
            return monthlyRetirementPension;
        } else if (totalWorkingDays >= 7560) {
            Supplier<Double> calculateMonthlyRetirementPension = () ->
                    salary * (70 / 100.0);
            double monthlyRetirementPension = calculateMonthlyRetirementPension.get();
            monthlyRetirementPension = adjustRetirementSalary((float) monthlyRetirementPension);
            return monthlyRetirementPension;
        } else {
            int numberOf216Increments = (totalWorkingDays - 3240) / 216;
            Supplier<Double> calculateMonthlyRetirementPension = () ->
                    salary * ((50 + numberOf216Increments) / 100.0);

            double monthlyRetirementPension = calculateMonthlyRetirementPension.get();
            monthlyRetirementPension = adjustRetirementSalary((float) monthlyRetirementPension);
            return monthlyRetirementPension;
        }
    }

    private static double adjustRetirementSalary(float monthlyRetirementPension) {
        return Math.round(monthlyRetirementPension * 100.0) / 100.0;
    }

}
