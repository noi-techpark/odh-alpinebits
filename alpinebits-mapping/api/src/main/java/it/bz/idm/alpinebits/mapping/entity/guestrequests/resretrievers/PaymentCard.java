/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * Payment card information.
 */
public class PaymentCard {

    private String cardCode;

    private Integer expireDate;

    private String cardHolderName;

    private String cardNumberPlain;

    private String encryptedValue;

    private String encryptionMethod;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Integer expireDate) {
        this.expireDate = expireDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumberPlain() {
        return cardNumberPlain;
    }

    public void setCardNumberPlain(String cardNumberPlain) {
        this.cardNumberPlain = cardNumberPlain;
    }

    public String getEncryptedValue() {
        return encryptedValue;
    }

    public void setEncryptedValue(String encryptedValue) {
        this.encryptedValue = encryptedValue;
    }

    public String getEncryptionMethod() {
        return encryptionMethod;
    }

    public void setEncryptionMethod(String encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    @Override
    public String toString() {
        return "PaymentCard{" +
                "cardCode='" + cardCode + '\'' +
                ", expireDate=" + expireDate +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardNumberPlain='" + cardNumberPlain + '\'' +
                ", encryptedValue='" + encryptedValue + '\'' +
                ", encryptionMethod='" + encryptionMethod + '\'' +
                '}';
    }
}
