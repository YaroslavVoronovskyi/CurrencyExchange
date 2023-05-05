package com.gmail.voronovskyi.yaroslav.service;

import com.gmail.voronovskyi.yaroslav.model.Minfin;

import java.util.List;

public interface IMinfinService {

    List<Minfin> getAll();
    List<Minfin> save(List<Minfin> minfinsList);
    List<Minfin> search(String date);
}
