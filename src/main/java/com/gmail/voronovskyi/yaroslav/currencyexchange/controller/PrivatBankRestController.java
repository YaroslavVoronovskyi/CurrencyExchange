package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.PrivatBankDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IPrivateBankService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/privat")
public class PrivatBankRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivatBankRestController.class);
    private final IPrivateBankService privateBankService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public PrivatBankRestController(IPrivateBankService privateBankService, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.privateBankService = privateBankService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<PrivatBank> getAll() {
        getDataFromSourceAndSaveToDb();
        LOGGER.debug("Try get all privatBanks");
        return privateBankService.getAll();
    }

    @GetMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<PrivatBankDto> searchByDate(@Param("date") String date) {
        LOGGER.debug("Try search privatBanks by date {}", date);
        return convertToDtoList(privateBankService.search(date));
    }

    private void getDataFromSourceAndSaveToDb() {
        LOGGER.debug("Try get privatBanks from source and save");
        String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        PrivatBankDto[] privatBankDtosArray = restTemplate.getForObject(url, PrivatBankDto[].class);
        privateBankService.save(convertToEntityList(Arrays.asList(privatBankDtosArray)));
        LOGGER.debug("PrivatBanks was successfully got from source and saved");
    }

    private PrivatBankDto convertToDto(PrivatBank privatBank) {
        try {
            return modelMapper.map(privatBank, PrivatBankDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("PrivatBankDto does not exist or has been deleted");
        }
    }

    private PrivatBank convertToEntity(PrivatBankDto privatBankDto) {
        try {
            return modelMapper.map(privatBankDto, PrivatBank.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("PrivatBank does not exist or has been deleted");
        }
    }

    private List<PrivatBankDto> convertToDtoList(List<PrivatBank> privatBanksList) {
        return privatBanksList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<PrivatBank> convertToEntityList(List<PrivatBankDto> privatBankDtosList) {
        return privatBankDtosList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
