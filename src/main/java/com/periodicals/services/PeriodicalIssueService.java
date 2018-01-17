package com.periodicals.services;

import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalIssuesJdbcDao;
import com.periodicals.dao.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalIssueService {
    private static PeriodicalIssueService issuesService = new PeriodicalIssueService();

    private static PeriodicalIssuesJdbcDao dao =
            (PeriodicalIssuesJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalIssuesDao();

    private PeriodicalIssueService() {

    }

    public static PeriodicalIssueService getInstance() {
        return issuesService;
    }

    public List<PeriodicalIssue> getByPeriodical(Periodical periodical) {
        List<PeriodicalIssue> result = null;
        try {
           result = dao.getIssuesByPeriodical(periodical);
        } catch (DaoException e) {
            /*logger*/
        }
        return result;
    }

    public void delete(int id) {
//        try {
//            dao.delete(id);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
    }

    public PeriodicalIssue getByPk(long id) {
        PeriodicalIssue result = null;
        try {
            result = dao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void update(PeriodicalIssue issue) {
        try {
            dao.update(issue);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void add(PeriodicalIssue issue) {
        try {
            dao.add(issue);
        } catch (DaoException e) {
            e.printStackTrace(); /*TODO throw*/
        }
    }

    public List<PeriodicalIssue> getIssuesByPeriodical(Periodical periodical) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            issues = dao.getIssuesByPeriodical(periodical);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return issues;
    }
}
