package com.company.pm.screen.filter;

import java.util.EventObject;

public class SearchEvent extends EventObject {

    private final String searchText;

    public SearchEvent(Object source, String searchText) {
        super(source);
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }
}
