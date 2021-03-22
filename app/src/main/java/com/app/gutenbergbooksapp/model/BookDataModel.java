package com.app.gutenbergbooksapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BookDataModel {
    Integer count;
    String next;
    String  previous;
    @SerializedName("results")
    List<Result> resultsList = new ArrayList();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Result> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<Result> resultsList) {
        this.resultsList = resultsList;
    }
}



