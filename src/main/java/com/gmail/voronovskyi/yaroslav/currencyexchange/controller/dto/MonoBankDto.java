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
public class MonoBankDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currencyCodeA;
    private String currencyCodeB;
    private String date;
    private String rateSell;
    private String rateBuy;
    private String rateCross;
}
