package com.periodicals.command.util;

import com.periodicals.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static com.periodicals.command.util.RedirectType.*;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;

public abstract class PagedCommand<T> implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PaginationInfoHolder<T> paginationInfoHolder = this.getPaginationInfoHolderInstance(request);

        if(Objects.nonNull(paginationInfoHolder)) {
            paginationInfoHolder.setAttributesToRequest(request);
        } else {
            return new CommandResult(FORWARD, ERROR_PAGE);
        }

        return new CommandResult(FORWARD, paginationInfoHolder.getCurrentPageHref());
    }

    protected abstract PaginationInfoHolder<T> getPaginationInfoHolderInstance(HttpServletRequest request);
}
