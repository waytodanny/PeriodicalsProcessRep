package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.sql.Date;
import java.util.Objects;

public class PeriodicalIssue implements Identified<Long> {
    private static final int ISSUE_NAME_MAX_LENGTH = 100;

    private Long id;
    private Integer issueNo;
    private String name;
    private Date publishDate;
    private Integer periodicalId;

    public PeriodicalIssue() {

    }

    public PeriodicalIssue(String name, int periodicalId) throws IllegalArgumentException {
        this();
        setName(name);
        setPeriodicalId(periodicalId);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        this.id = id;
    }

    public Integer getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(int issueNo) {
        if (issueNo <= 0) {
            throw new IllegalArgumentException("number of issue must be greater than 0");
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

    public Integer getPeriodicalId() {
        return periodicalId;
    }

    /*TODO decide whether is right to check here*/
    public void setPeriodicalId(Integer periodicalId) {
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
        return Objects.equals(id, that.id) &&
                Objects.equals(issueNo, that.issueNo) &&
                Objects.equals(name, that.name) &&
                Objects.equals(publishDate, that.publishDate) &&
                Objects.equals(periodicalId, that.periodicalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issueNo, name, publishDate, periodicalId);
    }
}
