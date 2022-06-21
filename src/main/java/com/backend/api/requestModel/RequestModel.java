package com.backend.api.requestModel;

import java.util.List;

public class RequestModel {

    public int from;
    public String type;
    public String keyword;
    public List<FilterModel> filter;
    public int size;
    public List<Double>after;
    public int next;

}
