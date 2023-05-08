package com.gmail.voronovskyi.yaroslav.currencyexchange.service;

import com.gmail.voronovskyi.yaroslav.currencyexchange.model.MonoBank;

import java.util.List;

public interface IMonoBankService {

    List<MonoBank> getAll();
    void save(List<MonoBank> monoBanksList);
    List<MonoBank> search(String date);
}
