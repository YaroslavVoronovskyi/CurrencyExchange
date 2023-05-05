package com.gmail.voronovskyi.yaroslav.controller;

import com.gmail.voronovskyi.yaroslav.controller.dto.PrivatBankDto;
import com.gmail.voronovskyi.yaroslav.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.service.IPrivateBankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/privat")
public class PrivateBankRestController {

    private final IPrivateBankService privateBankService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public PrivateBankRestController(IPrivateBankService privateBankService, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.privateBankService = privateBankService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<PrivatBank> getAll() {
        getFromUrl();
        return privateBankService.getAll();
    }
    @GetMapping("/api")
    public PrivatBankDto[] get() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        return restTemplate.getForObject(url, PrivatBankDto[].class);
    }

    @GetMapping("/search")
    public List<PrivatBankDto> findByDate(@Param("date") String date) {
        return convertToDtoList(privateBankService.search(date));
    }

    private void getFromUrl() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        PrivatBankDto[] privatBankDtosArray = restTemplate.getForObject(url, PrivatBankDto[].class);
        privateBankService.save(convertToEntityList(Arrays.asList(privatBankDtosArray)));
    }

    private PrivatBankDto convertToDto(PrivatBank privatBank) {
        return modelMapper.map(privatBank, PrivatBankDto.class);
    }

    private PrivatBank convertToEntity(PrivatBankDto privatBankDto) {
        return modelMapper.map(privatBankDto, PrivatBank.class);
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
