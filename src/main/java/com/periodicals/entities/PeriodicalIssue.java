package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class that represents periodical issue
 * @see Periodical
 */
public class PeriodicalIssue implements Identified<UUID> {
    private static final int ISSUE_NAME_MAX_LENGTH = 100;

    private UUID id;
    private int issueNo;
    private String name;
    private Date publishDate;
    private UUID periodicalId;

    public PeriodicalIssue() {

    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(int issueNo) throws IllegalArgumentException {
        if (issueNo <= 0) {
            throw new IllegalArgumentException("Number of issue must be greater than 0");
        }
        this.issueNo = issueNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.isNull(name) || name.length() > ISSUE_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "issue name must not be null and have length less than: " + ISSUE_NAME_MAX_LENGTH);
        }
        this.name = name;
    }

    public UUID getPeriodicalId() {
        return periodicalId;
    }

    /*TODO decide whether is right to check here*/
    public void setPeriodicalId(UUID periodicalId) {
        this.periodicalId = periodicalId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    /*TODO decide whether is right to check here*/
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodicalIssue that = (PeriodicalIssue) o;
        return issueNo == that.issueNo &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(publishDate, that.publishDate) &&
                Objects.equals(periodicalId, that.periodicalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issueNo, name, publishDate, periodicalId);
    }
}
