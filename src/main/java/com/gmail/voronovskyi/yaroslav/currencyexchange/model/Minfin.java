package com.gmail.voronovskyi.yaroslav.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "minfins")
public class Minfin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String pointDate;
    private String date;
    private String ask;
    private String bid;
    private String currency;
}
