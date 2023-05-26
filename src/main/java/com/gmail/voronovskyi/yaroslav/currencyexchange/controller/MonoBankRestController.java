package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
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

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mono")
public class MonoBankRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonoBankRestController.class);
    private final IMonoBankService monoBankService;
    private final ModelMapper modelMapper;

    @Autowired
    public MonoBankRestController(IMonoBankService monoBankService, ModelMapper modelMapper) {
        this.monoBankService = monoBankService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MonoBankDto> getAll() {
        LOGGER.debug("Try get all monoBanks");
        return convertToDtoList(monoBankService.getAll());
    }

    @GetMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MonoBankDto> searchByDate(@Param(Constants.DATE) String date) {
        LOGGER.debug("Try search monoBanks by date {}", date);
        return convertToDtoList(monoBankService.search(date));
    }

    private MonoBankDto convertToDto(MonoBank monoBank) {
        try {
            return modelMapper.map(monoBank, MonoBankDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException(Constants.MONOBANK_NOT_FOUND_MESSAGE);
        }
    }

    private MonoBank convertToEntity(MonoBankDto monoBankDto) {
        try {
            return modelMapper.map(monoBankDto, MonoBank.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException(Constants.MONOBANK_NOT_FOUND_MESSAGE);
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
