/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Rate Plan for a {@link RoomStayEntity}.
 */
@Entity
@Table(name = "rate_plan")
public class RatePlanEntity implements Serializable {

    private static final long serialVersionUID = -8203327202280285827L;

    @Id
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

    public void setCommissionAmount(Double commisionAmount) {
        this.commissionAmount = commisionAmount;
    }

    public String getCommissionCurrencyCode() {
        return commissionCurrencyCode;
    }

    public void setCommissionCurrencyCode(String commissionCurrencyCode) {
        this.commissionCurrencyCode = commissionCurrencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RatePlanEntity ratePlan = (RatePlanEntity) o;
        return Objects.equals(ratePlanCode, ratePlan.ratePlanCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ratePlanCode);
    }

    @Override
    public String toString() {
        return "RatePlanEntity{" +
                "ratePlanCode='" + ratePlanCode + '\'' +
                ", mealPlanIndicator=" + mealPlanIndicator +
                ", mealPlanCodes=" + mealPlanCodes +
                ", commissionPercent=" + commissionPercent +
                ", commissionAmount=" + commissionAmount +
                ", commissionCurrencyCode='" + commissionCurrencyCode + '\'' +
                '}';
    }
}
