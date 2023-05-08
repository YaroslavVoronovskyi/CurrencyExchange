package com.gmail.voronovskyi.yaroslav.currencyexchange.service;

import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;

import java.util.List;

public interface IPrivateBankService {

    List<PrivatBank> getAll();
    void save(List<PrivatBank> privatBanksList);
    List<PrivatBank> search(String date);
}
