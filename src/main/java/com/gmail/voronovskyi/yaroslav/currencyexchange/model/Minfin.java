package com.gmail.voronovskyi.yaroslav.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "minfins")
public class Minfin {

    @Id
    private String id;

    private String pointDate;
    private String date;
    private String ask;
    private String bid;
    private String currency;
}
