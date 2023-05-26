package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IPrivateBankRepository;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IPrivatBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
public class PrivatBankService implements IPrivatBankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivatBankService.class);
    private final IPrivateBankRepository privateBankRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PrivatBankService(IPrivateBankRepository privateBankRepository, RestTemplate restTemplate) {
        this.privateBankRepository = privateBankRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrivatBank> getAll() {
        List<PrivatBank> privatBanksList = getDataFromSource();
        save(privatBanksList);
        LOGGER.debug("Try get all privatBanks from DB");
        privatBanksList = privateBankRepository.findAll();
        if (privatBanksList.isEmpty()) {
            throw new EntityNotFoundException(Constants.PRIVATBANK_NOT_FOUND_MESSAGE);
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

    @Override
    public List<PrivatBank> getDataFromSource() {
        LOGGER.debug("Try get privatBanks from source");
        String url = Constants.PRIVATBANK_URL;
        PrivatBank[] privatsArray = restTemplate.getForObject(url, PrivatBank[].class);
        if (privatsArray == null) {
            throw new EntityNotFoundException(Constants.PRIVATBANK_NOT_FOUND_MESSAGE);
        }
        LOGGER.debug("PrivatBanks was successfully got from source");
        return Arrays.asList(privatsArray);
    }
}
