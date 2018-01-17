package com.periodicals.dao.interfaces;

import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalIssuesDao extends GenericDao<PeriodicalIssue, Long> {
    List<PeriodicalIssue> getIssuesByPeriodical(Periodical periodical) throws DaoException;
}
