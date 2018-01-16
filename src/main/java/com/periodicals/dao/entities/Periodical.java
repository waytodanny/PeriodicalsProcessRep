package com.periodicals.dao.entities;

import com.periodicals.dao.entities.util.Identified;

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
    private Short genreId;
    private Integer publisherId;

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

    public boolean isLimited() {
        return isLimited;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public Short getGenreId() {
        return genreId;
    }

    /*TODO decide whether is right to check here*/
    public void setGenreId(Short genreId) {
        this.genreId = genreId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    /*TODO decide whether is right to check here*/
    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return issuesPerYear == that.issuesPerYear &&
                isLimited == that.isLimited &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(subscriptionCost, that.subscriptionCost) &&
                Objects.equals(genreId, that.genreId) &&
                Objects.equals(publisherId, that.publisherId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, subscriptionCost, issuesPerYear, isLimited, genreId, publisherId);
    }
}
