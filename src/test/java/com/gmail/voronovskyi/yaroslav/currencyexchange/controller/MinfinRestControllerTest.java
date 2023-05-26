package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.MinfinDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.Minfin;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IMinfinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MinfinRestControllerTest {

    private final static String TEST_MINFIN_DATE = "2023-05-11 13:30:00";

    @Mock
    private IMinfinService minfinServiceMock;
    @Mock
    private ModelMapper modelMapperMock = new ModelMapper();
    @InjectMocks
    private MinfinRestController minfinRestController;

    @Test
    public void shouldReturnExpectedMinfinDtosList() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestMinfinDto());
        Mockito.when(minfinServiceMock.getAll()).thenReturn(List.of(createTestMinfin()));
        assertEquals(minfinRestController.getAll(), List.of(createTestMinfinDto()));
    }

    @Test
    public void shouldReturnExpectedMonoBankDtoByDate() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestMinfinDto());
        Mockito.when(minfinServiceMock.search(TEST_MINFIN_DATE)).thenReturn(List.of(createTestMinfin()));
        assertEquals(minfinRestController.searchByDate(TEST_MINFIN_DATE), List.of(createTestMinfinDto()));
    }

    private Minfin createTestMinfin() {
        return Minfin.builder()
                .id("164943")
                .pointDate("2023-05-11 13:45:03")
                .date("2023-05-11 13:30:00")
                .ask("40.3285")
                .bid("39.9402")
                .currency("eur")
                .build();
    }

    private MinfinDto createTestMinfinDto() {
        return MinfinDto.builder()
                .id("164943")
                .pointDate("2023-05-11 13:45:03")
                .date("2023-05-11 13:30:00")
                .ask("40.3285")
                .bid("39.9402")
                .currency("eur")
                .build();
    }
}
