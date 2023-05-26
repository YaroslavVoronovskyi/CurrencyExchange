package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IPrivateBankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PrivatBankServiceTest {

    private final static String TEST_PRIVATBANK_DATE = "2023-05-10";

    @Mock
    private IPrivateBankRepository privateBankRepositoryMock;
    @Mock
    private RestTemplate restTemplateMock;
    @InjectMocks
    private PrivatBankService privatBankService;

    @Test
    public void shouldReturnExpectedPrivatBank() {
        Mockito.when(privateBankRepositoryMock.search(TEST_PRIVATBANK_DATE)).thenReturn(List.of(createTestPrivatBank()));
        assertEquals(privatBankService.search(TEST_PRIVATBANK_DATE), List.of(createTestPrivatBank()));
    }

    @Test
    public void shouldThrowExceptionWhenTryReturnExpectedPrivatBank() {
        Mockito.when(privateBankRepositoryMock.search(TEST_PRIVATBANK_DATE)).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> privatBankService.search(TEST_PRIVATBANK_DATE));
    }

    @Test
    public void shouldReturnExpectedPrivatBanksList() {
        Mockito.when(restTemplateMock.getForObject(Constants.PRIVATBANK_URL, PrivatBank[].class))
                .thenReturn(createTestPrivatBanksArray());
        Mockito.when(privateBankRepositoryMock.findAll()).thenReturn(List.of(createTestPrivatBank()));
        assertEquals(privatBankService.getAll(), List.of(createTestPrivatBank()));
    }

    @Test
    public void shouldThrowExceptionWhenTryReturnExpectedPrivatBanksList() {
        Mockito.when(restTemplateMock.getForObject(Constants.PRIVATBANK_URL, PrivatBank[].class)).thenReturn(null);
        Mockito.when(privateBankRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> privatBankService.getAll());
    }

    @Test
    public void shouldSaveNewPrivatBank() {
        PrivatBank privatBank = createTestPrivatBank();
        privatBankService.save(List.of(privatBank));
        Mockito.verify(privateBankRepositoryMock).saveAll(List.of(privatBank));
    }

    @Test
    public void shouldGetDataFromSource() {
        Mockito.when(restTemplateMock.getForObject(Constants.PRIVATBANK_URL, PrivatBank[].class))
                .thenReturn(createTestPrivatBanksArray());
        List<PrivatBank> privatBanksList = privatBankService.getDataFromSource();
        assertEquals(privatBanksList.size(), List.of(createTestPrivatBank()).size());
        assertEquals(privatBanksList.get(0), createTestPrivatBank());
    }

    @Test
    public void shouldThrowExceptionWhenTryGetDataFromSource() {
        Mockito.when(restTemplateMock.getForObject(Constants.PRIVATBANK_URL, PrivatBank[].class)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> privatBankService.getDataFromSource());
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

    private PrivatBank[] createTestPrivatBanksArray() {
        return new PrivatBank[]{createTestPrivatBank()};
    }
}
