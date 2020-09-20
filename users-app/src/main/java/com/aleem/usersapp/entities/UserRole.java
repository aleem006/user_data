package com.aleem.usersapp.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long version;
    @ManyToOne
    private User user;
    @ManyToOne
    private Unit unit;
    @ManyToOne
    private Role role;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    @Transient
    public boolean isValid(){
        LocalDateTime now = LocalDateTime.now();
        return isValidWithDateTime(now);
    }
    @Transient
    public boolean isValidWithDateTime(LocalDateTime dateTime){
        if(dateTime.isAfter(validFrom) && validTo == null){
            return true;
        }else return dateTime.isAfter(validFrom) && dateTime.isBefore(validTo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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
