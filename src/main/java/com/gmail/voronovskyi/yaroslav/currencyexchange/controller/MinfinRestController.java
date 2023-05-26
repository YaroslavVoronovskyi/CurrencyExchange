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

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/minfin")
public class MinfinRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinfinRestController.class);
    private final IMinfinService minfinService;
    private final ModelMapper modelMapper;

    @Autowired
    public MinfinRestController(IMinfinService minfinService, ModelMapper modelMapper) {
        this.minfinService = minfinService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MinfinDto> getAll() {
        LOGGER.debug("Try get all minfins");
        return convertToDtoList(minfinService.getAll());
    }

    @GetMapping("/search")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<MinfinDto> searchByDate(@Param(Constants.DATE) String date) {
        LOGGER.debug("Try search minfins by date {}", date);
        return convertToDtoList(minfinService.search(date));
    }

    private MinfinDto convertToDto(Minfin minfin) {
        try {
            return modelMapper.map(minfin, MinfinDto.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException(Constants.MINFIN_NOT_FOUND_MESSAGE);
        }
    }

    private Minfin convertToEntity(MinfinDto minfinDto) {
        try {
            return modelMapper.map(minfinDto, Minfin.class);
        } catch (MappingException exception) {
            throw new EntityNotFoundException(Constants.MINFIN_NOT_FOUND_MESSAGE);
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
