package com.gmail.voronovskyi.yaroslav.controller;

import com.gmail.voronovskyi.yaroslav.controller.dto.MonoBankDto;
import com.gmail.voronovskyi.yaroslav.model.MonoBank;
import com.gmail.voronovskyi.yaroslav.service.IMonoBankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mono")
public class MonoBankRestController {

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
    public List<MonoBankDto> getAll() {
        getFromUrl();
        return convertToDtoList(monoBankService.getAll());
    }

    @GetMapping("/search")
    public List<MonoBankDto> findByDate(@Param("date") String date) {
        return convertToDtoList(monoBankService.search(date));
    }

    @GetMapping("/api")
    public void get() {
        String url = "https://api.monobank.ua/bank/currency";
        MonoBank[] monosArray = restTemplate.getForObject(url, MonoBank[].class);
        monoBankService.save(Arrays.asList(monosArray));
    }

    private void getFromUrl() {
        String url = "https://api.monobank.ua/bank/currency";
        MonoBankDto[] monosArray = restTemplate.getForObject(url, MonoBankDto[].class);
        monoBankService.save(convertToEntityList(Arrays.asList(monosArray)));
    }

    private MonoBankDto convertToDto(MonoBank monoBank) {
        return modelMapper.map(monoBank, MonoBankDto.class);
    }

    private MonoBank convertToEntity(MonoBankDto monoBankDto) {
        return modelMapper.map(monoBankDto, MonoBank.class);
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
