package se.group3.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.services.SerializationService;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SerializationServiceTest {

    @InjectMocks
    private SerializationService SerializationUtil;

    @Test
    void jsonStringFromClass() {
        CellDTO cellDTO = Mockito.mock(CellDTO.class);

        Mockito.when(cellDTO.getCol()).thenReturn(2);
        Mockito.when(cellDTO.getRow()).thenReturn(2);
        Mockito.when(cellDTO.getNextCells()).thenReturn(Arrays.asList(2, 3));
        Mockito.when(cellDTO.getId()).thenReturn("4");
        Mockito.when(cellDTO.getType()).thenReturn("test");
        Mockito.when(cellDTO.getNumber()).thenReturn(1);

        try {
            Assertions.assertEquals("{\"id\":\"4\",\"number\":1,\"type\":\"test\",\"nextCells\":[2,3],\"row\":2,\"col\":2}",
                    SerializationUtil.jsonStringFromClass(cellDTO));
            verify(cellDTO, times(1)).getCol();
            verify(cellDTO, times(1)).getRow();
            verify(cellDTO, times(1)).getNumber();
            verify(cellDTO, times(1)).getType();
            verify(cellDTO, times(1)).getNextCells();
            verify(cellDTO, times(1)).getId();
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getCause());
        }
    }
    @Test
    void toObject() {
        String jsonString = "{\"id\":\"4\",\"number\":1,\"type\":\"test\",\"nextCells\":[2,3],\"row\":2,\"col\":2}";

        try {
            CellDTO cellDTO = (CellDTO) SerializationUtil.toObject(jsonString, CellDTO.class);
            Assertions.assertNotNull(cellDTO);
            Assertions.assertEquals("4", cellDTO.getId());
            Assertions.assertEquals(1, cellDTO.getNumber());
            Assertions.assertEquals("test", cellDTO.getType());
            Assertions.assertEquals(Arrays.asList(2, 3), cellDTO.getNextCells());
            Assertions.assertEquals(2, cellDTO.getRow());
            Assertions.assertEquals(2, cellDTO.getCol());
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getCause());
        }
    }
}