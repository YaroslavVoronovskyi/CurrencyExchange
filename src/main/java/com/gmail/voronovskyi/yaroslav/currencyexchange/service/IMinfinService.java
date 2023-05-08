package com.gmail.voronovskyi.yaroslav.currencyexchange.service;

import com.gmail.voronovskyi.yaroslav.currencyexchange.model.Minfin;

import java.util.List;

public interface IMinfinService {

    List<Minfin> getAll();
    void save(List<Minfin> minfinsList);
    List<Minfin> search(String date);
}
