package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.MonoBankDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.MonoBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IMonoBankService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mono")
public class MonoBankRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonoBankRestController.class);
    private final IMonoBankService monoBankService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public MonoBankRestController(IMonoBankService monoBankService, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.monoBankService = monoBankService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MonoBankDto> getAll() {
        getDataFromSourceAndSaveToDb();
        LOGGER.debug("Try get all monoBanks");
        return convertToDtoList(monoBankService.getAll());
    }

    @GetMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MonoBankDto> searchByDate(@Param("date") String date) {
        LOGGER.debug("Try search monoBanks by date {}", date);
        return convertToDtoList(monoBankService.search(date));
    }

    private void getDataFromSourceAndSaveToDb() {
        LOGGER.debug("Try get monoBanks from source and save");
        String url = "https://api.monobank.ua/bank/currency";
        MonoBankDto[] monosArray = restTemplate.getForObject(url, MonoBankDto[].class);
        monoBankService.save(convertToEntityList(Arrays.asList(monosArray)));
        LOGGER.debug("MonoBanks was successfully got from source and saved");
    }

    private MonoBankDto convertToDto(MonoBank monoBank) {
        try {
            return modelMapper.map(monoBank, MonoBankDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("MonoBankDto does not exist or has been deleted");
        }
    }

    private MonoBank convertToEntity(MonoBankDto monoBankDto) {
        try {
            return modelMapper.map(monoBankDto, MonoBank.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("MonoBank does not exist or has been deleted");
        }
    }

    private List<MonoBankDto> convertToDtoList(List<MonoBank> monoBanksList) {
        return monoBanksList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<MonoBank> convertToEntityList(List<MonoBankDto> monoBankDtosList) {
        return monoBankDtosList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
