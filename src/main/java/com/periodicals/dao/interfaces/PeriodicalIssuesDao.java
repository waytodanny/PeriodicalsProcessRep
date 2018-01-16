package com.periodicals.dao.interfaces;

import com.periodicals.dao.interfaces.GenericDao;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalIssuesDao extends GenericDao<PeriodicalIssue, Long> {
    List<PeriodicalIssue> getIssuesByPeriodicalId(int id) throws DaoException;
}
