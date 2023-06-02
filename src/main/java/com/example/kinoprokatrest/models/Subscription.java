package com.example.kinoprokatrest.models;

import jakarta.persistence.*;

import java.time.LocalDate;

import java.util.List;

@Entity
public class Subscription {
    /*
        Модель подписки. Пользователь покупает подписку на неделю, месяц или год. У подписки есть дата начала, окончания в зависимости от типа и тип.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private SubType subTypes;

    public Subscription(Long id, LocalDate date, LocalDate endDate, SubType subTypes) {
        this.id = id;
        this.date = date;
        this.endDate = endDate;
        this.subTypes = subTypes;
    }

    public Subscription(SubType subTypes) {
        this.subTypes = subTypes;
    }

    public Subscription() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SubType getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(SubType subTypes) {
        this.subTypes = subTypes;
    }
}
