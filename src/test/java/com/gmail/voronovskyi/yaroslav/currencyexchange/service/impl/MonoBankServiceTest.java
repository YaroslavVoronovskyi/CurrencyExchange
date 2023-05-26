package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.MonoBank;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IMonoBankRepository;
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
public class MonoBankServiceTest {

    private final static String TEST_MONOBANK_DATE = "1683669674";

    @Mock
    private IMonoBankRepository monoBankRepositoryMock;
    @Mock
    private RestTemplate restTemplateMock;
    @InjectMocks
    private MonoBankService monoBankService;

    @Test
    public void shouldReturnExpectedMonoBank() {
        Mockito.when(monoBankRepositoryMock.search(TEST_MONOBANK_DATE)).thenReturn(List.of(createTestMonoBank()));
        assertEquals(monoBankService.search(TEST_MONOBANK_DATE), List.of(createTestMonoBank()));
    }

    @Test
    public void shouldThrowExceptionWhenTryReturnExpectedMonoBank() {
        Mockito.when(restTemplateMock.getForObject(Constants.MONOBANK_URL, MonoBank[].class)).thenReturn(null);
        Mockito.when(monoBankRepositoryMock.search(TEST_MONOBANK_DATE)).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> monoBankService.search(TEST_MONOBANK_DATE));
    }

    @Test
    public void shouldReturnExpectedMonoBanksList() {
        Mockito.when(restTemplateMock.getForObject(Constants.MONOBANK_URL, MonoBank[].class))
                .thenReturn(createTestMonoBanksArray());
        Mockito.when(monoBankRepositoryMock.findAll()).thenReturn(List.of(createTestMonoBank()));
        assertEquals(monoBankService.getAll(), List.of(createTestMonoBank()));
    }

    @Test
    public void shouldThrowExceptionWhenTryReturnExpectedMonoBanksList() {
        Mockito.when(restTemplateMock.getForObject(Constants.MONOBANK_URL, MonoBank[].class)).thenReturn(null);
        Mockito.when(monoBankRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> monoBankService.getAll());
    }

    @Test
    public void shouldSaveNewMonoBank() {
        MonoBank monoBank = createTestMonoBank();
        monoBankService.save(List.of(monoBank));
        Mockito.verify(monoBankRepositoryMock).saveAll(List.of(monoBank));
    }

    @Test
    public void shouldGetDataFromSource() {
        Mockito.when(restTemplateMock.getForObject(Constants.MONOBANK_URL, MonoBank[].class))
                .thenReturn(createTestMonoBanksArray());
        List<MonoBank> monoBanksList = monoBankService.getDataFromSource();
        assertEquals(monoBanksList.size(), List.of(createTestMonoBank()).size());
        assertEquals(monoBanksList.get(0), createTestMonoBank());
    }

    @Test
    public void shouldThrowExceptionWhenTryGetDataFromSource() {
        Mockito.when(restTemplateMock.getForObject(Constants.MONOBANK_URL, MonoBank[].class)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> monoBankService.getDataFromSource());
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

    private MonoBank[] createTestMonoBanksArray() {
        return new MonoBank[]{createTestMonoBank()};
    }
}
