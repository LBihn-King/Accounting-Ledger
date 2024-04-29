package com.pluralsite;

public class Debit {
    private int cardNumber;
    private String expirationDate;
    private short securityCode;

    public Debit(int cardNumber, String expirationDate, short securityCode) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public short getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(short securityCode) {
        this.securityCode = securityCode;
    }
}
