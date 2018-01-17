package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.math.BigDecimal;
import java.util.Objects;

public class Periodical implements Identified<Integer> {
    private static final int PERIODICAL_NAME_MAX_LENGTH = 100;
    private static final int PERIODICAL_DESCRIPTION_MAX_LENGTH = 1000;

    private Integer id;
    private String name;
    private String description;
    private BigDecimal subscriptionCost;
    private short issuesPerYear;
    private boolean isLimited;
    private Genre genre;
    private Publisher publisher;

    public Periodical() {

    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) throws IllegalArgumentException{
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException{
        if (Objects.isNull(name) || name.length() > PERIODICAL_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "periodical name must not be null and have length less than : " + PERIODICAL_NAME_MAX_LENGTH);
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException{
        if (Objects.isNull(name) || name.length() > PERIODICAL_DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "periodical description must not be null and have length less than : " + PERIODICAL_DESCRIPTION_MAX_LENGTH);
        }
        this.description = description;
    }

    public BigDecimal getSubscriptionCost() {
        return subscriptionCost;
    }

    /*TODO decide whether it's needed to convert here*/
    public void setSubscriptionCost(BigDecimal subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public short getIssuesPerYear() {
        return issuesPerYear;
    }

    public void setIssuesPerYear(short issuesPerYear) throws IllegalArgumentException{
        if (issuesPerYear < 0) {
            throw new IllegalArgumentException("number of issues per year must be positive number");
        }
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
