package com.backend.api.repoSearchModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoSourceModel {
    String slug;
    String name;
    String quickSearchRepositoryName;
    boolean Public;
    boolean fork;
    String quickSearchProjectName;
    int projectId;
    String hierarchyId;
    int projectType;
    double size;



}
