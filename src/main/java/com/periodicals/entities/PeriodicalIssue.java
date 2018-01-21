package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.sql.Date;
import java.util.Objects;

public class PeriodicalIssue implements Identified<String> {
    private static final int UUID_DEFAULT_LENGTH = 36;
    private static final int ISSUE_NAME_MAX_LENGTH = 100;

    private String id;
    private int issueNo;
    private String name;
    private Date publishDate;
    private String periodicalId;

    public PeriodicalIssue() {

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

    public String getPeriodicalId() {
        return periodicalId;
    }

    /*TODO decide whether is right to check here*/
    public void setPeriodicalId(String periodicalId) {
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
