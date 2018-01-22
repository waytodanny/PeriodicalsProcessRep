package com.periodicals.services.entity;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalIssuesJdbcDao;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.util.PageableCollectionService;
import com.periodicals.utils.uuid.UuidGenerator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeriodicalIssueService implements PageableCollectionService<PeriodicalIssue> {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalIssueService.class.getSimpleName());

    private static final PeriodicalIssueService periodicalIssueService = new PeriodicalIssueService();

    private static PeriodicalIssuesJdbcDao issuesDao =
            (PeriodicalIssuesJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalIssuesDao();
    private static PeriodicalsJdbcDao periodicalsDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    private PeriodicalIssueService() { }

    public static PeriodicalIssueService getInstance() {
        return periodicalIssueService;
    }

    public void update(PeriodicalIssue issue) throws ServiceException {
        try {
            issuesDao.updateEntity(issue);
            LOGGER.debug("Issue successfully updated");
        } catch (DaoException e) {
            LOGGER.error("Failed to update issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void deleteById(String periodicalIssueId) throws ServiceException {
        try {
            issuesDao.deleteEntity(periodicalIssueId);
            LOGGER.debug("Issue successfully deleted");
        } catch (DaoException e) {
            LOGGER.error("Failed to deleteUserById issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public PeriodicalIssue getPeriodicalById(String id) {
        PeriodicalIssue result = null;
        try {
            result = issuesDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained periodical issue by PK " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain issue by PK " + id);
        }
        return result;
    }

    public void addNewIssue(String name, int issueNo, UUID periodicalId) throws ServiceException {
        PeriodicalIssue newIssue = new PeriodicalIssue();
        newIssue.setId(UuidGenerator.generateUuid());
        newIssue.setName(name);
        newIssue.setIssueNo(issueNo);
        newIssue.setPeriodicalId(periodicalId);
        try {
            issuesDao.createEntity(newIssue);
            LOGGER.debug("Issue has been successfully added");
        } catch (DaoException e) {
            LOGGER.error("Failed to addNewIssue issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public List<PeriodicalIssue> getPeriodicalIssues(Periodical periodical) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            issues = issuesDao.getPeriodicalIssues(periodical);
            LOGGER.debug("Obtained issues by periodical");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain issues by periodical");
        }
        return issues;
    }

    public int getAllIssuesCount() {
        int result = 0;
        try {
            result = issuesDao.getAllIssuesCount();
            LOGGER.debug("Obtained periodical issues count: " + result);
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodical issues count using DAO: " + e.getMessage());
        }
        return result;
    }

    public int getPeriodicalIssuesCount(UUID periodicalId) {
        int result = 0;
        try {
            Periodical periodical = periodicalsDao.getEntityByPrimaryKey(periodicalId);
            result = issuesDao.getPeriodicalIssuesCount(periodical);
            LOGGER.debug("Obtained periodical issues count: " + result);
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodical issues count using DAO: " + e.getMessage());
        }
        return result;
    }

    public List<PeriodicalIssue> getAllIssuesLimited(int skip, int limit) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            issues = issuesDao.getAllIssuesLimited(skip, limit);
            LOGGER.debug("Obtained genre periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodical issues sublist using DAO: " + e.getMessage());
        }
        return issues;
    }

    public List<PeriodicalIssue> getPeriodicalIssuesLimited(UUID periodicalId, int skip, int limit) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            Periodical periodical = periodicalsDao.getEntityByPrimaryKey(periodicalId);
            issues = issuesDao.getPeriodicalIssuesLimited(periodical, skip, limit);
            LOGGER.debug("Obtained periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodical issues sublist using DAO: " + e.getMessage());
        }
        return issues;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = issuesDao.getEntitiesCount();
            LOGGER.debug("Obtained all periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals count using DAO: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PeriodicalIssue> getEntitiesListBounded(int skip, int limit) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            issues = issuesDao.getAllIssuesLimited(skip, limit);
            LOGGER.debug("Obtained periodicals bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals bounded list using DAO: " + e.getMessage());
        }
        return issues;
    }
}
