package com.backend.api.searchmodel;


import com.backend.api.requestModel.Search_After_Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrayItems {
    public String _index;
    public String _type;
    public String _id;
    public double _score;
    public SourceModel _source;
    public List<Double> sort;

}
