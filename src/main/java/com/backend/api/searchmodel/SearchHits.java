package com.backend.api.searchmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHits {
    public  Object total;
    public double max_score;
    public List<ArrayItems> hits;
}
