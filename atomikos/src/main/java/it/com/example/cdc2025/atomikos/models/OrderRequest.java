package it.com.example.cdc2025.atomikos.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderRequest {
    private String itemCode;
    private int requestedQuantity;
}
