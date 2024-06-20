package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.database.UserPointTable;

public class PointService {

    public UserPoint findPointById(Long id) {
        UserPointTable userPointTable = new UserPointTable();
        return userPointTable.selectById(id);
    }

    public UserPoint updatePointById(long id, long point) {
        UserPointTable userPointTable = new UserPointTable();
        return userPointTable.insertOrUpdate(id,point);
    }
}
