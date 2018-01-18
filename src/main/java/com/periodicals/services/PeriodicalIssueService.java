package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalIssuesJdbcDao;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalIssueService {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalIssueService.class.getSimpleName());

    private static PeriodicalIssueService issuesService = new PeriodicalIssueService();

    private static PeriodicalIssuesJdbcDao issuesDao =
            (PeriodicalIssuesJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalIssuesDao();

    private PeriodicalIssueService() {

    }

    public static PeriodicalIssueService getInstance() {
        return issuesService;
    }

    public static void update(PeriodicalIssue issue) throws ServiceException {
        try {
            issuesDao.update(issue);
            LOGGER.debug("Issue successfully updated");
        } catch (DaoException e) {
            LOGGER.error("Failed to update issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void delete(PeriodicalIssue periodicalIssue) throws ServiceException {
        try {
            issuesDao.delete(periodicalIssue);
            LOGGER.debug("Issue successfully deleted");
        } catch (DaoException e) {
            LOGGER.error("Failed to delete issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public PeriodicalIssue getByPk(long id) {
        PeriodicalIssue result = null;
        try {
            result = issuesDao.getById(id);
            LOGGER.debug("Obtained periodical issue by PK " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain issue by PK " + id);
        }
        return result;
    }

    public void add(PeriodicalIssue issue) throws ServiceException {
        try {
            issuesDao.add(issue);
            LOGGER.debug("Issue has been successfully added");
        } catch (DaoException e) {
            LOGGER.error("Failed to add issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public List<PeriodicalIssue> getIssuesByPeriodical(Periodical periodical) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            issues = issuesDao.getIssuesByPeriodical(periodical);
            LOGGER.debug("Obtained issues by periodical");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain issues by periodical");
        }
        return issues;
    }
}
