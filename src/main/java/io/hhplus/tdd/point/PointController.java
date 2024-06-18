package io.hhplus.tdd.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.hhplus.tdd.database.*;

@RestController
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    /**
     * 특정 유저의 포인트를 조회하는 기능
     */
    @GetMapping("{id}")
    public UserPoint point(
            @PathVariable long id
    ) {
        // 유저포인트 테이블 객체 생성
        UserPointTable userPointTable = new UserPointTable();
        // 유저포인트 조회
        return userPointTable.selectById(id);
    }

    /**
     * 특정 유저의 포인트 충전/이용 내역을 조회하는 기능
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(
            @PathVariable long id
    ) {
        // 포인트 사용이력 테이블 객체생성
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        // 포인트 사용이력 테이블 조회
        return pointHistoryTable.selectAllByUserId(id);
    }

    /**
     * 특정 유저의 포인트를 충전하는 기능
     */
    @PatchMapping("{id}/charge")
    public UserPoint charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        // 유저포인트, 포인트 사용이력 테이블 객체 생성
        UserPointTable userPointTable = new UserPointTable();
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        // 유저포인트 조회
        UserPoint uPoint = userPointTable.selectById(id);
        // 포인트 사용이력 추가
        pointHistoryTable.insert(id, uPoint.point() + amount, TransactionType.CHARGE, System.currentTimeMillis());
        // 유저포인트 적립
        return userPointTable.insertOrUpdate(id, uPoint.point() + amount);
    }
    
    /**
     * 특정 유저의 포인트를 사용하는 기능
     */
    @PatchMapping("{id}/use")
    public UserPoint use(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        // 유저포인트, 포인트 사용이력 테이블 객체 생성
        UserPointTable userPointTable = new UserPointTable();
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        // 유저포인트 조회
        UserPoint uPoint = userPointTable.selectById(id);
        // 포인트 사용이력 추가
        pointHistoryTable.insert(id, uPoint.point() - amount, TransactionType.USE, System.currentTimeMillis());
        // 유저포인트 사용
        return userPointTable.insertOrUpdate(id, uPoint.point() - amount);
    }
}
