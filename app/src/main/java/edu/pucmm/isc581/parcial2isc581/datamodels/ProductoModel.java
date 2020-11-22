package edu.pucmm.isc581.parcial2isc581.datamodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProductoModel {
    private Integer id;
    private String name;
    private Integer price;
    private String category;
    private String image64;
}
