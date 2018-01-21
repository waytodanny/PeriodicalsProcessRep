package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.math.BigDecimal;
import java.util.Objects;

public class Periodical implements Identified<String> {
    private static final int UUID_DEFAULT_LENGTH = 36;
    private static final int PERIODICAL_NAME_MAX_LENGTH = 100;
    private static final int PERIODICAL_DESCRIPTION_MAX_LENGTH = 1000;

    private String id;
    private String name;
    private String description;
    private BigDecimal subscriptionCost;
    private int issuesPerYear;
    private boolean isLimited;
    private Genre genre;
    private Publisher publisher;

    public Periodical() {

    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.length() != UUID_DEFAULT_LENGTH) {
            throw new IllegalArgumentException("Invalid id length: " + id.length());
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.isNull(name) || name.length() > PERIODICAL_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "periodical name must not be null and have length less than : " + PERIODICAL_NAME_MAX_LENGTH);
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
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

    public int getIssuesPerYear() {
        return issuesPerYear;
    }

    public void setIssuesPerYear(int issuesPerYear) {
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
                Objects.equals(genre, that.genre) &&
                Objects.equals(publisher, that.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, subscriptionCost, issuesPerYear, isLimited, genre, publisher);
    }
}
