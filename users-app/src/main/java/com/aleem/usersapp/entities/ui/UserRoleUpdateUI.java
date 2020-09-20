package com.aleem.usersapp.entities.ui;


import java.time.LocalDateTime;


public class UserRoleUpdateUI {
    LocalDateTime validFrom;
    LocalDateTime validTo;

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }
}
