package com.backend.api.searchmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceModel {
    public  String path;
    public boolean fork;
    public boolean Public;
    public int repositoryId;
    public List<String> extension;
    public String filename;
    public long size;
    public int projectId;
    public String content;

}
