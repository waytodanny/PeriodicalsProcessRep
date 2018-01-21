package com.periodicals.command.util;

import com.periodicals.Main;
import com.periodicals.command.util.CommandResult;
import com.periodicals.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    CommandResult execute(HttpServletRequest req, HttpServletResponse resp);
}