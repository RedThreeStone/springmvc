package it.lei.boot.solr;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei
 */
@Data
public class ResultModel {
    private Integer totalNum;

    private Integer pageTotal;

    List<Product> productList=new ArrayList<>();
}
