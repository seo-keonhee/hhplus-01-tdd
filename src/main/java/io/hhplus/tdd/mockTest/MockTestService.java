package io.hhplus.tdd.mockTest;

import io.hhplus.tdd.mockTest.repository.MockTestRepository;

public class MockTestService {

    private final MockTestRepository mockTestRepository;

    public MockTestService(MockTestRepository mockTestRepository) {
        this.mockTestRepository = mockTestRepository;
    }

    public String findNameById(Long id) {
        return mockTestRepository.findById(id).getName();
    }

    public String updateName(Long id, String name) {
        mockTestRepository.findById(id).setName(name);
        return mockTestRepository.findById(id).getName();
    }

    public Long findIdByName(String name) {
        return mockTestRepository.findByName(name).getId();
    }
}

