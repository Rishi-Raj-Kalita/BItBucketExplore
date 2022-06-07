package com.backend.api.repoSearchModel;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoSearchHits {
    public  Object total;
    public double max_score;
    public List<RepoArrayItems> hits;
}
