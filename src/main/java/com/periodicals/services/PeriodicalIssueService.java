package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalIssuesJdbcDao;
import com.periodicals.entities.PeriodicalIssue;
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

    public List<PeriodicalIssue> getByPeriodicalId(int id) {
        List<PeriodicalIssue> result = null;
        try {
           result = dao.getIssuesByPeriodicalId(id);
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
            result = dao.getByKey(id);
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

    public List<PeriodicalIssue> getIssuesByPeriodicalId(int i) {
        List<PeriodicalIssue> issues = new ArrayList<>();
        try {
            issues = dao.getIssuesByPeriodicalId(i);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return issues;
    }
}
