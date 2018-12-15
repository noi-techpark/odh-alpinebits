/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * Rate Plan for a {@link RoomStay}.
 */
public class RatePlan {

    private String ratePlanCode;

    private Boolean mealPlanIndicator;

    private Integer mealPlanCodes;

    private Integer commissionPercent;

    private Double commissionAmount;

    private String commissionCurrencyCode;

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public Boolean getMealPlanIndicator() {
        return mealPlanIndicator;
    }

    public void setMealPlanIndicator(Boolean mealPlanIndicator) {
        this.mealPlanIndicator = mealPlanIndicator;
    }

    public Integer getMealPlanCodes() {
        return mealPlanCodes;
    }

    public void setMealPlanCodes(Integer mealPlanCodes) {
        this.mealPlanCodes = mealPlanCodes;
    }

    public Integer getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Integer commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public Double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getCommissionCurrencyCode() {
        return commissionCurrencyCode;
    }

    public void setCommissionCurrencyCode(String commissionCurrencyCode) {
        this.commissionCurrencyCode = commissionCurrencyCode;
    }

    @Override
    public String toString() {
        return "RatePlan{" +
                "ratePlanCode='" + ratePlanCode + '\'' +
                ", mealPlanIndicator=" + mealPlanIndicator +
                ", mealPlanCodes=" + mealPlanCodes +
                ", commissionPercent=" + commissionPercent +
                ", commissionAmount=" + commissionAmount +
                ", commissionCurrencyCode='" + commissionCurrencyCode + '\'' +
                '}';
    }
}
