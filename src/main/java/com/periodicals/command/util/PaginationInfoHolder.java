package com.periodicals.command.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sun.plugin.dom.css.CSSConstants.ATTR_PAGE;

/**
 * Class that is needed to carry some info for pages that use objects pagination
 * E - collection of elements that is to be divided on pages
 */
public class PaginationInfoHolder<E> {
    private List<E> displayedObjects;
    private int recordsCount;
    private int recordsPerPage;
    private int currentPage;
    private String pageHrefTemplate;

    public static int getPageFromRequest(HttpServletRequest request) {
        return CommandUtils.paramClarifiedInQuery(request, ATTR_PAGE) ?
                Integer.parseInt(request.getParameter(ATTR_PAGE)) :
                1;
    }

    public List<E> getDisplayedObjects() {
        return displayedObjects;
    }

    public void setDisplayedObjects(List<E> displayedObjects) {
        this.displayedObjects = displayedObjects;
    }

    public int getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageHrefTemplate() {
        return pageHrefTemplate;
    }

    public void setPageHrefTemplate(String pageHref) {
        this.pageHrefTemplate = pageHref + "?" + ATTR_PAGE + "=";
    }

    public int getPagesCount() {
        return (int) Math.ceil(this.recordsCount / this.recordsPerPage);
    }

    public int getSkippedRecodrsCount() {
        return (this.currentPage - 1) * this.recordsPerPage;
    }

    public String getCurrentPageHref() {
        return this.pageHrefTemplate + this.currentPage;
    }

    public void setAttributesToRequest(HttpServletRequest request) {
        request.setAttribute("displayedObjects", this.getDisplayedObjects());
        request.setAttribute("pagesCount", this.getPagesCount());
        request.setAttribute("currentPage", this.getCurrentPage());
        request.setAttribute("pageHrefTemplate", this.getPageHrefTemplate());
    }
}

