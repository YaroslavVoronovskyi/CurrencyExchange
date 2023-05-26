package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.PrivatBankDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IPrivatBankService;
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

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/privat")
public class PrivatBankRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivatBankRestController.class);
    private final IPrivatBankService privatBankService;
    private final ModelMapper modelMapper;

    @Autowired
    public PrivatBankRestController(IPrivatBankService privatBankService, ModelMapper modelMapper) {
        this.privatBankService = privatBankService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<PrivatBankDto> getAll() {
        LOGGER.debug("Try get all privatBanks");
        return convertToDtoList(privatBankService.getAll());
    }

    @GetMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<PrivatBankDto> searchByDate(@Param(Constants.DATE) String date) {
        LOGGER.debug("Try search privatBanks by date {}", date);
        return convertToDtoList(privatBankService.search(date));
    }

    private PrivatBankDto convertToDto(PrivatBank privatBank) {
        try {
            return modelMapper.map(privatBank, PrivatBankDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException(Constants.PRIVATBANK_NOT_FOUND_MESSAGE);
        }
    }

    private PrivatBank convertToEntity(PrivatBankDto privatBankDto) {
        try {
            return modelMapper.map(privatBankDto, PrivatBank.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException(Constants.PRIVATBANK_NOT_FOUND_MESSAGE);
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
