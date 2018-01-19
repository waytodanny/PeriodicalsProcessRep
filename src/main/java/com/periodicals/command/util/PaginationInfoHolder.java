package com.periodicals.command.util;

import java.util.List;

/**
 * Class that is needed to carry some info for pages that use objects pagination
 * E - collection of elements that is to be divided on pages
 * T - variable that corresponding for covering max. objects count
 */
public class PaginationInfoHolder<E, T extends Number> {
    private List<E> displayedObjects;
    private T recordsCount;
    private T currentPage;

    /**
     * link to the next page of objects set
     */
    private String nextPageHrefLink;

    /**
     * link to the next page for servlet
     * TODO think of where to place
     */
    private String redirectedPageLink;

    public List<E> getDisplayedObjects() {
        return displayedObjects;
    }

    public void setDisplayedObjects(List<E> displayedObjects) {
        this.displayedObjects = displayedObjects;
    }

    public T getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(T recordsCount) {
        this.recordsCount = recordsCount;
    }

    public T getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(T currentPage) {
        this.currentPage = currentPage;
    }

    public String getNextPageHrefLink() {
        return nextPageHrefLink;
    }

    public void setNextPageHrefLink(String nextPageHrefLink) {
        this.nextPageHrefLink = nextPageHrefLink;
    }

    public String getRedirectedPageLink() {
        return redirectedPageLink;
    }

    public void setRedirectedPageLink(String redirectedPageLink) {
        this.redirectedPageLink = redirectedPageLink;
    }
}
