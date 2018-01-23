package com.periodicals.command.util;

public class CommandResult {
    public final RedirectType redirectType;
    public final String pageHref;

    public CommandResult(RedirectType redirectType, String pageHref) {
        this.redirectType = redirectType;
        this.pageHref = pageHref;
    }
}