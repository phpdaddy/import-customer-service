package com.phpdaddy.importcustomerservice.model.customer.enums;

public enum AgeGroup {
    AGE_GROUP_20_30(20, 30), AGE_GROUP_(36, 40);

    private final int minAge;
    private final int maxAge;

    AgeGroup(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public static AgeGroup calc(int age) {
        for (AgeGroup ageGroup : AgeGroup.values()) {
            if (ageGroup.minAge <= age && ageGroup.maxAge >= age) {
                return ageGroup;
            }
        }
        return null;
    }
}