package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.Minfin;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IMinfinRepository;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IMinfinService;
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
public class MinfinService implements IMinfinService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinfinService.class);
    private final IMinfinRepository minfinRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public MinfinService(IMinfinRepository minfinRepository, RestTemplate restTemplate) {
        this.minfinRepository = minfinRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Minfin> getAll() {
        List<Minfin> minfinsList = getDataFromSource();
        save(minfinsList);
        LOGGER.debug("Try get all minfins from DB");
        minfinsList = minfinRepository.findAll();
        if (minfinsList.isEmpty()) {
            throw new EntityNotFoundException(Constants.MINFIN_NOT_FOUND_MESSAGE);
        }
        LOGGER.debug("All minfins was successfully got from DB");
        return minfinsList;
    }

    @Override
    @Transactional
    public void save(List<Minfin> minfinsList) {
        LOGGER.debug("Try save minfins from source to DB");
        minfinRepository.saveAll(minfinsList);
        LOGGER.debug("Minfins was successfully saved from source to DB");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Minfin> search(String date) {
        LOGGER.debug("Try get minfins with date {} from DB", date);
        List<Minfin> minfinsList = minfinRepository.search(date);
        if (minfinsList.isEmpty()) {
            throw new EntityNotFoundException("Minfins with date " + date + " does not exist or has been deleted!");
        }
        LOGGER.debug("All minfins with date {} was successfully got from DB", date);
        return minfinsList;
    }

    @Override
    public List<Minfin> getDataFromSource() {
        LOGGER.debug("Try get minfins from source");
        String url = Constants.MINFIN_URL;
        Minfin[] minfinsArray = restTemplate.getForObject(url, Minfin[].class);
        if (minfinsArray == null) {
            throw new EntityNotFoundException(Constants.MINFIN_NOT_FOUND_MESSAGE);
        }
        LOGGER.debug("Minfins was successfully got from source");
        return Arrays.asList(minfinsArray);
    }
}
