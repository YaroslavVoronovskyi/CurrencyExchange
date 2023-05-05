package com.gmail.voronovskyi.yaroslav.service;

import com.gmail.voronovskyi.yaroslav.model.PrivatBank;

import java.util.List;

public interface IPrivateBankService {

    List<PrivatBank> getAll();
    List<PrivatBank> save(List<PrivatBank> privatBanksList);
    List<PrivatBank> search(String date);
}
