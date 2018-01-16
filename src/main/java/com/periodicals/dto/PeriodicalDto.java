package com.periodicals.dto;

import com.periodicals.dao.entities.Genre;
import com.periodicals.dao.entities.Publisher;

import java.math.BigDecimal;

public class PeriodicalDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal subscriptionCost;
    private short issuesPerYear;
    private boolean isLimited;
    private Genre genre;
    private Publisher publisher;

    public PeriodicalDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(BigDecimal subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public short getIssuesPerYear() {
        return issuesPerYear;
    }

    public void setIssuesPerYear(short issuesPerYear) {
        this.issuesPerYear = issuesPerYear;
    }

    public boolean getIsLimited() {
        return isLimited;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
