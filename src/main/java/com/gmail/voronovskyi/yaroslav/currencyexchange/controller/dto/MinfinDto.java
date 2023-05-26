package com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor

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
