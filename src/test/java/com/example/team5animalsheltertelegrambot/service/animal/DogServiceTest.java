package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.repository.animal.DogRepository;
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
class DogServiceTest {

    @Mock
    private DogRepository dogRepositoryMock;

    @InjectMocks
    private DogService out;

    @Test
    void saveShouldReturnTrue() {
        when(dogRepositoryMock.save(CORRECT_DOG_1)).thenReturn(CORRECT_DOG_1);
        assertTrue(out.save(CORRECT_DOG_1));
        verify(dogRepositoryMock, only()).save(CORRECT_DOG_1);
    }

    @Test
    void saveShouldReturnFalse() {
        assertFalse(out.save(INCORRECT_DOG));
    }

    @Test
    void findByIdShouldReturnOptional() {
        when(dogRepositoryMock.findById(anyInt())).thenReturn(Optional.of(CORRECT_DOG_1));
        assertEquals(out.findById(anyInt()), Optional.of(CORRECT_DOG_1));
        verify(dogRepositoryMock, only()).findById(anyInt());
    }

    @Test
    void findByIdShouldReturnEmpty() {
        when(dogRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
        assertEquals(out.findById(anyInt()), Optional.empty());
        verify(dogRepositoryMock, only()).findById(anyInt());
    }

    @Test
    void findAllByHealthShouldReturnListOfHealthyDogs() {
        when(dogRepositoryMock.findAllByHealth(true)).thenReturn(LIST_OF_HEALTHY_DOGS);
        assertEquals(out.findAllByHealth(true), LIST_OF_HEALTHY_DOGS);
        verify(dogRepositoryMock, only()).findAllByHealth(true);
    }

    @Test
    void findAllByHealthShouldReturnListOfUnhealthyDogs() {
        when(dogRepositoryMock.findAllByHealth(false)).thenReturn(LIST_OF_UNHEALTHY_DOGS);
        assertEquals(out.findAllByHealth(false), LIST_OF_UNHEALTHY_DOGS);
        verify(dogRepositoryMock, only()).findAllByHealth(false);
    }

    @Test
    void findAllByVaccinateShouldReturnListOfVaccinatedDogs() {
        when(dogRepositoryMock.findAllByVaccination(true)).thenReturn(LIST_OF_VACCINATED_DOGS);
        assertEquals(out.findAllByVaccinate(true), LIST_OF_VACCINATED_DOGS);
        verify(dogRepositoryMock, only()).findAllByVaccination(true);
    }

    @Test
    void findAllByVaccinateShouldReturnListOfUnvaccinatedDogs() {
        when(dogRepositoryMock.findAllByVaccination(false)).thenReturn(LIST_OF_UNVACCINATED_DOGS);
        assertEquals(out.findAllByVaccinate(false), LIST_OF_UNVACCINATED_DOGS);
        verify(dogRepositoryMock, only()).findAllByVaccination(false);
    }

    @Test
    void findAllByHealthAndVaccinationShouldReturnListOfHealthyAndUnvaccinatedDogs() {
        when(dogRepositoryMock.findAllByHealthAndVaccination(true, false)).thenReturn(LIST_OF_HEALTHY_AND_UNVACCINATED_DOGS);
        assertEquals(out.findAllByHealthAndVaccination(true, false), LIST_OF_HEALTHY_AND_UNVACCINATED_DOGS);
        verify(dogRepositoryMock, only()).findAllByHealthAndVaccination(true, false);
    }

    @Test
    void findAll() {
        when(dogRepositoryMock.findAll()).thenReturn(LIST_OF_CORRECT_DOGS);
        assertEquals(out.findAll(), LIST_OF_CORRECT_DOGS);
        verify(dogRepositoryMock, only()).findAll();
    }

    @Test
    void updateByIdShouldReturn1() {
        when(dogRepositoryMock.findById(anyInt())).thenReturn(Optional.of(CORRECT_DOG_1));
        when(dogRepositoryMock.updateById(1, CORRECT_DOG_1.getName(), CORRECT_DOG_1.getAge(), CORRECT_DOG_1.getHealthy(), CORRECT_DOG_1.getVaccinated())).thenReturn(1);

        assertEquals(out.updateById(1, CORRECT_DOG_1.getName(), CORRECT_DOG_1.getAge(), CORRECT_DOG_1.getHealthy(), CORRECT_DOG_1.getVaccinated()), 1);

        verify(dogRepositoryMock, times(1)).findById(anyInt());
        verify(dogRepositoryMock, times(1)).updateById(1, CORRECT_DOG_1.getName(), CORRECT_DOG_1.getAge(), CORRECT_DOG_1.getHealthy(), CORRECT_DOG_1.getVaccinated());
    }

    @Test
    void updateByIdShouldReturn0() {
        assertEquals(out.updateById(anyInt(), INCORRECT_DOG.getName(), INCORRECT_DOG.getAge(), INCORRECT_DOG.getHealthy(), INCORRECT_DOG.getVaccinated()), 0);
    }

    @Test
    void deleteById() {
        doNothing().when(dogRepositoryMock).deleteById(anyInt());
        dogRepositoryMock.deleteById(1);
        verify(dogRepositoryMock, times(1)).deleteById(anyInt());
    }
}
