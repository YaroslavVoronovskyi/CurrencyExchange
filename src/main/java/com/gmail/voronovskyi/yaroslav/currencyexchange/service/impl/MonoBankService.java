package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.MonoBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IMonoBankRepository;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IMonoBankService;
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
public class MonoBankService implements IMonoBankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonoBankService.class);
    private final IMonoBankRepository monoBankRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public MonoBankService(IMonoBankRepository monoBankRepository, RestTemplate restTemplate) {
        this.monoBankRepository = monoBankRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonoBank> getAll() {
        List<MonoBank> monoBanksList = getDataFromSource();
        save(monoBanksList);
        LOGGER.debug("Try get all monoBanks from DB");
        monoBanksList = monoBankRepository.findAll();
        if (monoBanksList.isEmpty()) {
            throw new EntityNotFoundException(Constants.MONOBANK_NOT_FOUND_MESSAGE);
        }
        LOGGER.debug("All monoBanks was successfully got from DB");
        return monoBanksList;
    }

    @Override
    @Transactional
    public void save(List<MonoBank> monoBanksList) {
        LOGGER.debug("Try save monoBanks from source to DB");
        monoBankRepository.saveAll(monoBanksList);
        LOGGER.debug("MonoBanks was successfully saved from source to DB");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonoBank> search(String date) {
        LOGGER.debug("Try get monoBanks with date {} from DB", date);
        List<MonoBank> monoBanksList = monoBankRepository.search(date);
        if (monoBanksList.isEmpty()) {
            throw new EntityNotFoundException("MonoBanks with date " + date + " does not exist or has been deleted!");
        }
        LOGGER.debug("All monoBanks with date {} was successfully got from DB", date);
        return monoBanksList;
    }

    @Override
    public List<MonoBank> getDataFromSource() {
        LOGGER.debug("Try get monoBanks from source");
        String url = Constants.MONOBANK_URL;
        MonoBank[] monosArray = restTemplate.getForObject(url, MonoBank[].class);
        if (monosArray == null) {
            throw new EntityNotFoundException(Constants.MONOBANK_NOT_FOUND_MESSAGE);
        }
        LOGGER.debug("MonoBanks was successfully got from source");
        return Arrays.asList(monosArray);
    }
}
