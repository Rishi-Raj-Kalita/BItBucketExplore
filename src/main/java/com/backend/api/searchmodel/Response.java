package com.backend.api.searchmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    public int took;
    public boolean timed_out;
    public Object _shards;
    public SearchHits hits;
}
