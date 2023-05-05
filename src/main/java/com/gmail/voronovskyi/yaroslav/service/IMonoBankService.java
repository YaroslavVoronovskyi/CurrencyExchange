package com.gmail.voronovskyi.yaroslav.service;

import com.gmail.voronovskyi.yaroslav.model.MonoBank;

import java.util.List;

public interface IMonoBankService {

    List<MonoBank> getAll();
    List<MonoBank> save(List<MonoBank> monoBanksList);
    List<MonoBank> search(String date);
}
