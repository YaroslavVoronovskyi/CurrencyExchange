package com.gmail.voronovskyi.yaroslav.currencyexchange.controller;

import com.gmail.voronovskyi.yaroslav.currencyexchange.controller.dto.PrivatBankDto;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.service.IPrivatBankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PrivatBankRestControllerTest {

    private final static String TEST_PRIVATBANK_DATE = "2023-05-10";

    @Mock
    private IPrivatBankService privatBankServiceMock;
    @Mock
    private ModelMapper modelMapperMock = new ModelMapper();
    @InjectMocks
    private PrivatBankRestController bankRestController;

    @Test
    public void shouldReturnExpectedPrivatBankDtosList() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestPrivatBankDto());
        Mockito.when(privatBankServiceMock.getAll()).thenReturn(List.of(createTestPrivatBank()));
        assertEquals(bankRestController.getAll(), List.of(createTestPrivatBankDto()));
    }

    @Test
    public void shouldReturnExpectedPrivatBankDtoByDate() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestPrivatBankDto());
        Mockito.when(privatBankServiceMock.search(TEST_PRIVATBANK_DATE)).thenReturn(List.of(createTestPrivatBank()));
        assertEquals(bankRestController.searchByDate(TEST_PRIVATBANK_DATE), List.of(createTestPrivatBankDto()));
    }

    private PrivatBank createTestPrivatBank() {
        return PrivatBank.builder()
                .id("1")
                .ccy("EUR")
                .buy("40.08830")
                .sale("41.66667")
                .date("2023-05-10")
                .build();
    }

    private PrivatBankDto createTestPrivatBankDto() {
        return PrivatBankDto.builder()
                .ccy("EUR")
                .buy("40.08830")
                .sale("41.66667")
                .date(LocalDate.parse("2023-05-10"))
                .build();
    }
}

