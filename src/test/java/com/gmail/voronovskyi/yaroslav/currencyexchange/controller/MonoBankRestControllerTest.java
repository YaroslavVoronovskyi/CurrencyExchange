package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.MonoBankDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.MonoBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IMonoBankService;
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
public class MonoBankRestControllerTest {

    private final static String TEST_MONOBANK_DATE = "1683669674";

    @Mock
    private IMonoBankService monoBankServiceMock;
    @Mock
    private ModelMapper modelMapperMock = new ModelMapper();
    @InjectMocks
    private MonoBankRestController monoBankRestController;

    @Test
    public void shouldReturnExpectedMonoBankDtosList() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestMonoBankDto());
        Mockito.when(monoBankServiceMock.getAll()).thenReturn(List.of(createTestMonoBank()));
        assertEquals(monoBankRestController.getAll(), List.of(createTestMonoBankDto()));
    }

    @Test
    public void shouldReturnExpectedMonoBankDtoByDate() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestMonoBankDto());
        Mockito.when(monoBankServiceMock.search(TEST_MONOBANK_DATE)).thenReturn(List.of(createTestMonoBank()));
        assertEquals(monoBankRestController.searchByDate(TEST_MONOBANK_DATE), List.of(createTestMonoBankDto()));
    }

    private MonoBank createTestMonoBank() {
        return MonoBank.builder()
                .id("1")
                .currencyCodeA("840")
                .currencyCodeB("980")
                .date("1683669674")
                .rateSell("37.4406")
                .rateBuy("36.65")
                .rateCross("0")
                .build();
    }

    private MonoBankDto createTestMonoBankDto() {
        return MonoBankDto.builder()
                .currencyCodeA("840")
                .currencyCodeB("980")
                .date("1683669674")
                .rateSell("37.4406")
                .rateBuy("36.65")
                .rateCross("0")
                .build();
    }
}
