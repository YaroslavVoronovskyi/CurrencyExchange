package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.MinfinDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.Minfin;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IMinfinService;
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
@RequestMapping("/api/minfin")
public class MinfinRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinfinRestController.class);
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
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Minfin> getAll() {
        getDataFromSource();
        LOGGER.debug("Try get all minfins");
        return minfinService.getAll();
    }

    @GetMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MinfinDto> searchByDate(@Param(Constants.DATE) String date) {
        LOGGER.debug("Try search minfins by date {}", date);
        return convertToDtoList(minfinService.search(date));
    }

    private void getDataFromSource() {
        LOGGER.debug("Try get minfins from source");
        String url = Constants.MINFIN_URL;
        MinfinDto[] minfinDtosArray = restTemplate.getForObject(url, MinfinDto[].class);
        LOGGER.debug("Minfins was successfully got from source");
        saveToDb(minfinDtosArray);
    }

    private void saveToDb(MinfinDto[] minfinDtosArray) {
        LOGGER.debug("Try save minfins from source to DB");
        minfinService.save(convertToEntityList(Arrays.asList(minfinDtosArray)));
        LOGGER.debug("Minfins was successfully saved from source to DB");
    }

    private MinfinDto convertToDto(Minfin minfin) {
        try {
            return modelMapper.map(minfin, MinfinDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("MinfinDto does not exist or has been deleted");
        }
    }

    private Minfin convertToEntity(MinfinDto minfinDto) {
        try {
            return modelMapper.map(minfinDto, Minfin.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException("Minfin does not exist or has been deleted");
        }
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
