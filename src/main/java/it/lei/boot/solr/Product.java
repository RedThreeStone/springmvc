package it.lei.boot.solr;

import lombok.Data;

@Data
public class Product {
    private Integer pid;
    private String name;
    private Double price;
    private String picturePath;
    private String keywords;


}
