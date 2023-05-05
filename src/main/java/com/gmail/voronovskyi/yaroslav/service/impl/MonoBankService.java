package com.gmail.voronovskyi.yaroslav.service.impl;

import com.gmail.voronovskyi.yaroslav.model.MonoBank;
import com.gmail.voronovskyi.yaroslav.repository.IMonoBankRepository;
import com.gmail.voronovskyi.yaroslav.service.IMonoBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MonoBankService implements IMonoBankService {

    private final IMonoBankRepository monoBankRepository;

    @Autowired
    public MonoBankService(IMonoBankRepository monoBankRepository) {
        this.monoBankRepository = monoBankRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonoBank> getAll() {
        List<MonoBank> monoBanksList = monoBankRepository.findAll();
        if (monoBanksList.isEmpty()) {
            throw new EntityNotFoundException("MonoBank list is empty!");
        }
        return monoBanksList;
    }

    @Override
    @Transactional
    public List<MonoBank> save(List<MonoBank> monoBanksList) {
        return monoBankRepository.saveAll(monoBanksList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonoBank> search(String date) {
        List<MonoBank> monoBanksList = monoBankRepository.search(date);
        if (monoBanksList.isEmpty()) {
            throw new EntityNotFoundException("MonoBank list is empty!");
        }
        return monoBanksList;
    }
}
