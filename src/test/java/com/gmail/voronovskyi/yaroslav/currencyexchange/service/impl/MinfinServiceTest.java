package com.gmail.voronovskyi.yaroslav.currencyexchange.service.impl;

import com.gmail.voronovskyi.yaroslav.currencyexchange.Constants;
import com.gmail.voronovskyi.yaroslav.currencyexchange.model.Minfin;
import com.gmail.voronovskyi.yaroslav.currencyexchange.repository.IMinfinRepository;
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
public class MinfinServiceTest {

    private final static String TEST_MINFIN_DATE = "2023-05-11 13:30:00";

    @Mock
    private IMinfinRepository minfinRepositoryMock;
    @Mock
    private RestTemplate restTemplateMock;
    @InjectMocks
    private MinfinService minfinService;

    @Test
    public void shouldReturnExpectedMinfin() {
        Mockito.when(minfinRepositoryMock.search(TEST_MINFIN_DATE)).thenReturn(List.of(createTestMinfin()));
        assertEquals(minfinService.search(TEST_MINFIN_DATE), List.of(createTestMinfin()));
    }

    @Test
    public void shouldThrowExceptionWhenTryReturnExpectedMinfin() {
        Mockito.when(restTemplateMock.getForObject(Constants.MINFIN_URL, Minfin[].class)).thenReturn(null);
        Mockito.when(minfinRepositoryMock.search(TEST_MINFIN_DATE)).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> minfinService.search(TEST_MINFIN_DATE));
    }

    @Test
    public void shouldReturnExpectedMinfinsList() {
        Mockito.when(restTemplateMock.getForObject(Constants.MINFIN_URL, Minfin[].class))
                .thenReturn(createTestMinfinsArray());
        Mockito.when(minfinRepositoryMock.findAll()).thenReturn(List.of(createTestMinfin()));
        assertEquals(minfinService.getAll(), List.of(createTestMinfin()));
    }

    @Test
    public void shouldThrowExceptionWhenTryReturnExpectedMinfinsList() {
        Mockito.when(restTemplateMock.getForObject(Constants.MINFIN_URL, Minfin[].class)).thenReturn(null);
        Mockito.when(minfinRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class, () -> minfinService.getAll());
    }

    @Test
    public void shouldSaveNewMinfin() {
        Minfin minfin = createTestMinfin();
        minfinService.save(List.of(minfin));
        Mockito.verify(minfinRepositoryMock).saveAll(List.of(minfin));
    }

    @Test
    public void shouldGetDataFromSource() {
        Mockito.when(restTemplateMock.getForObject(Constants.MINFIN_URL, Minfin[].class))
                .thenReturn(createTestMinfinsArray());
        List<Minfin> minfinsList = minfinService.getDataFromSource();
        assertEquals(minfinsList.size(), List.of(createTestMinfin()).size());
        assertEquals(minfinsList.get(0), createTestMinfin());
    }

    @Test
    public void shouldThrowExceptionWhenTryGetDataFromSource() {
        Mockito.when(restTemplateMock.getForObject(Constants.MINFIN_URL, Minfin[].class)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> minfinService.getDataFromSource());
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

    private Minfin[] createTestMinfinsArray() {
        return new Minfin[]{createTestMinfin()};
    }
}
