package com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MinfinDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String pointDate;
    private String date;
    private String ask;
    private String bid;
    private String currency;
}
