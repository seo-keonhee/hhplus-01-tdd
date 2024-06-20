package io.hhplus.tdd.mockTest;

import io.hhplus.tdd.mockTest.repository.Entity.MockTestEntity;
import io.hhplus.tdd.mockTest.repository.MockTestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class MockTestServiceTest {

    @Test
    public void testFindNameById() {
        // Mock 객체 생성
        MockTestRepository mockTestRepository = Mockito.mock(MockTestRepository.class);
        MockTestService mockTestService = new MockTestService(mockTestRepository);

        // given
        MockTestEntity mockEntity = new MockTestEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Test Name");
        when(mockTestRepository.findById(1L)).thenReturn(mockEntity);

        // when
        String name = mockTestService.findNameById(1L);

        // then
        assertEquals("Test Name", name);
    }


    @Test
    public void testUpdateName() {
        // Mock 객체 생성
        MockTestRepository mockTestRepository = Mockito.mock(MockTestRepository.class);
        MockTestService mockTestService = new MockTestService(mockTestRepository);

        // given
        MockTestEntity mockEntity = new MockTestEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Test Name");
        when(mockTestRepository.findById(1L)).thenReturn(mockEntity);

        // when
        mockTestService.updateName(1L, "Update Name");
        String name = mockTestService.findNameById(1L);

        // then
        assertEquals("Update Name", name);
    }


    @Test
    public void testFindIdByName() {
        // Mock 객체 생성
        MockTestRepository mockTestRepository = Mockito.mock(MockTestRepository.class);
        MockTestService mockTestService = new MockTestService(mockTestRepository);

        // given
        MockTestEntity mockEntity = new MockTestEntity();
        mockEntity.setId(1L);
        mockEntity.setName("Test Name");

        // when
        when(mockTestRepository.findByName("Test Name")).thenReturn(mockEntity);
        Long id = mockTestService.findIdByName("Test Name");

        // then
        assertEquals(1L, id);
    }
}