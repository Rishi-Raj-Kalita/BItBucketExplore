package com.backend.api.repoSearchModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoArrayItems {
    public String _index;
    public String _type;
    public String _id;
    public double _score;
    public RepoSourceModel _source;
}
