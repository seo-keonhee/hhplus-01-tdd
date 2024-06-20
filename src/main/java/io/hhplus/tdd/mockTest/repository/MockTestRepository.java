package io.hhplus.tdd.mockTest.repository;

import io.hhplus.tdd.mockTest.repository.Entity.MockTestEntity;

public interface MockTestRepository {

    MockTestEntity findById(Long id);

    MockTestEntity findByName(String name);
}
