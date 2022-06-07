package com.backend.api.repoSearchModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoResponse {
    public int took;
    public boolean timed_out;
    public Object _shards;
    public RepoSearchHits hits;
}
