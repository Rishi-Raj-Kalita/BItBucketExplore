package com.backend.api.responseModel;

import java.util.List;


public class ResponseModel {

    public String type;
    public String path;
    public String repoName;
    public List<String> extension;
    public String fileName;
    public double size;
    public double _score;
    public String projectName;
    public String content;
    public List<Double>sort;



    public ResponseModel(String type, String path, String repoName, List<String> extension, String fileName, double size,double score, String projectName, String content,List<Double>sort) {
        this.type = type;
        this.path = path;
        this.repoName = repoName;
        this.extension = extension;
        this.fileName = fileName;
        this.size = size;
        this._score=score;
        this.projectName = projectName;
        this.content = content;
        this.sort=sort;

    }
}
