package com.gmail.voronovskyi.yaroslav.controller;

import com.gmail.voronovskyi.yaroslav.controller.dto.MinfinDto;
import com.gmail.voronovskyi.yaroslav.model.Minfin;
import com.gmail.voronovskyi.yaroslav.service.IMinfinService;
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
@RequestMapping("/minfin")
public class MinfinRestController {

    private final IMinfinService minfinService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public MinfinRestController(IMinfinService minfinService, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.minfinService = minfinService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Minfin> getAll() {
        getFromUrl();
        return minfinService.getAll();
    }

    @GetMapping("/api")
    public MinfinDto[] get() {
        String url = "https://api.minfin.com.ua/mb/f2ab12b3c7da7f83a4b71c0f0f5d7375364f407f/";
        return restTemplate.getForObject(url, MinfinDto[].class);
    }

    @GetMapping("/search")
    public List<MinfinDto> findByDate(@Param("date") String date) {
        return convertToDtoList(minfinService.search(date));
    }

    private void getFromUrl() {
        String url = "https://api.minfin.com.ua/mb/f2ab12b3c7da7f83a4b71c0f0f5d7375364f407f/";
        MinfinDto[] minfinDtosArray = restTemplate.getForObject(url, MinfinDto[].class);
        minfinService.save(convertToEntityList(Arrays.asList(minfinDtosArray)));
    }

    private MinfinDto convertToDto(Minfin minfin) {
        return modelMapper.map(minfin, MinfinDto.class);
    }

    private Minfin convertToEntity(MinfinDto minfinDto) {
        return modelMapper.map(minfinDto, Minfin.class);
    }

    private List<MinfinDto> convertToDtoList(List<Minfin> minfinsList) {
        return minfinsList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<Minfin> convertToEntityList(List<MinfinDto> minfinDtosList) {
        return minfinDtosList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
