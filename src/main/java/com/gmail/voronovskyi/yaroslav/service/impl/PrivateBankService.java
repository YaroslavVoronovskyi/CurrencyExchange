package com.gmail.voronovskyi.yaroslav.service.impl;

import com.gmail.voronovskyi.yaroslav.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.repository.IPrivateBankRepository;
import com.gmail.voronovskyi.yaroslav.service.IPrivateBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PrivateBankService implements IPrivateBankService {

    private final IPrivateBankRepository privateBankRepository;

    @Autowired
    public PrivateBankService(IPrivateBankRepository privateBankRepository) {
        this.privateBankRepository = privateBankRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivatBank> getAll() {
        List<PrivatBank> privatBanksList = privateBankRepository.findAll();
        if (privatBanksList.isEmpty()) {
            throw new EntityNotFoundException("PrivatBank list is empty!");
        }
        return privatBanksList;
    }

    @Override
    @Transactional
    public List<PrivatBank> save(List<PrivatBank> privatBanksList) {
        return privateBankRepository.saveAll(privatBanksList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivatBank> search(String date) {
        List<PrivatBank> privatBanksList = privateBankRepository.search(date);
        if (privatBanksList.isEmpty()) {
            throw new EntityNotFoundException("PrivatBank list is empty!");
        }
        return privatBanksList;
    }
}
