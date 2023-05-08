package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IPrivateBankRepository;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IPrivateBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PrivateBankService implements IPrivateBankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateBankService.class);
    private final IPrivateBankRepository privateBankRepository;

    @Autowired
    public PrivateBankService(IPrivateBankRepository privateBankRepository) {
        this.privateBankRepository = privateBankRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivatBank> getAll() {
        LOGGER.debug("Try get all privatBanks from DB");
        List<PrivatBank> privatBanksList = privateBankRepository.findAll();
        if (privatBanksList.isEmpty()) {
            throw new EntityNotFoundException("PrivatBanks does not exist or has been deleted!");
        }
        LOGGER.debug("All privatBanks was successfully got from DB");
        return privatBanksList;
    }

    @Override
    @Transactional
    public void save(List<PrivatBank> privatBanksList) {
        LOGGER.debug("Try save privatBanks from source to DB");
        privateBankRepository.saveAll(privatBanksList);
        LOGGER.debug("PrivatBanks was successfully saved from source to DB");
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivatBank> search(String date) {
        LOGGER.debug("Try get privatBanks with date {} from DB", date);
        List<PrivatBank> privatBanksList = privateBankRepository.search(date);
        if (privatBanksList.isEmpty()) {
            throw new EntityNotFoundException("PrivatBanks with date " + date + " does not exist or has been deleted!");
        }
        LOGGER.debug("All privatBanks with date {} was successfully got from DB", date);
        return privatBanksList;
    }
}
