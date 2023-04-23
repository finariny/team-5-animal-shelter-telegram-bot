package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.repository.animal.CatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.team5animalsheltertelegrambot.constant.AnimalConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {

    @Mock
    private CatRepository catRepositoryMock;

    @InjectMocks
    private CatService out;

    @Test
    void saveShouldReturnTrue() {
        when(catRepositoryMock.save(CORRECT_CAT_1)).thenReturn(CORRECT_CAT_1);
        assertTrue(out.save(CORRECT_CAT_1));
        verify(catRepositoryMock, only()).save(CORRECT_CAT_1);
    }

    @Test
    void saveShouldReturnFalse() {
        assertFalse(out.save(INCORRECT_CAT));
    }

    @Test
    void findByIdShouldReturnOptional() {
        when(catRepositoryMock.findById(anyInt())).thenReturn(Optional.of(CORRECT_CAT_1));
        assertEquals(out.findById(anyInt()), Optional.of(CORRECT_CAT_1));
        verify(catRepositoryMock, only()).findById(anyInt());
    }

    @Test
    void findByIdShouldReturnEmpty() {
        when(catRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
        assertEquals(out.findById(anyInt()), Optional.empty());
        verify(catRepositoryMock, only()).findById(anyInt());
    }

    @Test
    void findAllByHealthShouldReturnListOfHealthyCats() {
        when(catRepositoryMock.findAllByHealth(true)).thenReturn(LIST_OF_HEALTHY_CATS);
        assertEquals(out.findAllByHealth(true), LIST_OF_HEALTHY_CATS);
        verify(catRepositoryMock, only()).findAllByHealth(true);
    }

    @Test
    void findAllByHealthShouldReturnListOfUnhealthyCats() {
        when(catRepositoryMock.findAllByHealth(false)).thenReturn(LIST_OF_UNHEALTHY_CATS);
        assertEquals(out.findAllByHealth(false), LIST_OF_UNHEALTHY_CATS);
        verify(catRepositoryMock, only()).findAllByHealth(false);
    }

    @Test
    void findAllByVaccinateShouldReturnListOfVaccinatedCats() {
        when(catRepositoryMock.findAllByVaccination(true)).thenReturn(LIST_OF_VACCINATED_CATS);
        assertEquals(out.findAllByVaccinate(true), LIST_OF_VACCINATED_CATS);
        verify(catRepositoryMock, only()).findAllByVaccination(true);
    }

    @Test
    void findAllByVaccinateShouldReturnListOfUnvaccinatedCats() {
        when(catRepositoryMock.findAllByVaccination(false)).thenReturn(LIST_OF_UNVACCINATED_CATS);
        assertEquals(out.findAllByVaccinate(false), LIST_OF_UNVACCINATED_CATS);
        verify(catRepositoryMock, only()).findAllByVaccination(false);
    }

    @Test
    void findAllByHealthAndVaccinationShouldReturnListOfHealthyAndUnvaccinatedCats() {
        when(catRepositoryMock.findAllByHealthAndVaccination(true, false)).thenReturn(LIST_OF_HEALTHY_AND_UNVACCINATED_CATS);
        assertEquals(out.findAllByHealthAndVaccination(true, false), LIST_OF_HEALTHY_AND_UNVACCINATED_CATS);
        verify(catRepositoryMock, only()).findAllByHealthAndVaccination(true, false);
    }

    @Test
    void findAll() {
        when(catRepositoryMock.findAll()).thenReturn(LIST_OF_CORRECT_CATS);
        assertEquals(out.findAll(), LIST_OF_CORRECT_CATS);
        verify(catRepositoryMock, only()).findAll();
    }

    @Test
    void updateByIdShouldReturn1() {
        when(catRepositoryMock.findById(anyInt())).thenReturn(Optional.of(CORRECT_CAT_1));
        when(catRepositoryMock.updateById(1, CORRECT_CAT_1.getName(), CORRECT_CAT_1.getAge(), CORRECT_CAT_1.getHealthy(), CORRECT_CAT_1.getVaccinated())).thenReturn(1);

        assertEquals(out.updateById(1, CORRECT_CAT_1.getName(), CORRECT_CAT_1.getAge(), CORRECT_CAT_1.getHealthy(), CORRECT_CAT_1.getVaccinated()), 1);

        verify(catRepositoryMock, times(1)).findById(anyInt());
        verify(catRepositoryMock, times(1)).updateById(1, CORRECT_CAT_1.getName(), CORRECT_CAT_1.getAge(), CORRECT_CAT_1.getHealthy(), CORRECT_CAT_1.getVaccinated());
    }

    @Test
    void updateByIdShouldReturn0() {
        assertEquals(out.updateById(anyInt(), INCORRECT_CAT.getName(), INCORRECT_CAT.getAge(), INCORRECT_CAT.getHealthy(), INCORRECT_CAT.getVaccinated()), 0);
    }

    @Test
    void deleteById() {
        doNothing().when(catRepositoryMock).deleteById(anyInt());
        catRepositoryMock.deleteById(1);
        verify(catRepositoryMock, times(1)).deleteById(anyInt());
    }
}
