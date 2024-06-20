package io.hhplus.tdd.point.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;

import java.util.List;


public class HistoryService {

    public List<PointHistory> findAllById(long id){
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        return pointHistoryTable.selectAllByUserId(id);
    }
    
    public PointHistory insertHistory(long id
            , long point, TransactionType type, long updateMillis) {
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        return pointHistoryTable.insert(id,point,type,updateMillis);
    }
}
