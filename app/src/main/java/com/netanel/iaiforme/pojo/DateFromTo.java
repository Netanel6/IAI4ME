package com.netanel.iaiforme.pojo;

public class DateFromTo {
    String id;
    String name;
    String last;
    String dateFrom;
    String dateTo;


    public DateFromTo() {
    }

    public DateFromTo(String id, String dateFrom, String dateTo) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public DateFromTo(String id, String name, String last, String dateFrom, String dateTo) {
        this.id = id;
        this.name = name;
        this.last = last;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
