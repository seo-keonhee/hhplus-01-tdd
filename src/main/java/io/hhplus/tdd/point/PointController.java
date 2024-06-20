package io.hhplus.tdd.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.hhplus.tdd.point.service.*;

// 나눠보려고 했는데 결국 로직이 다 컨트롤러에 몰린것 같아요 ㅠㅠ
// 서비스단에서 처리했어야 할 로직을 알려주시면 감사합니다.

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
        // 포인트서비스 객체 생성
        PointService pointService = new PointService();
        return pointService.findPointById(id);
    }

    /**
     * 특정 유저의 포인트 충전/이용 내역을 조회하는 기능
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(
            @PathVariable long id
    ) {
        // 포인트이력 서비스 객체생성
        HistoryService historyService = new HistoryService();
        return historyService.findAllById(id);
    }


    /**
     * 특정 유저의 포인트를 충전하는 기능
     */
    @PatchMapping("{id}/charge")
    public synchronized UserPoint charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        // 포인트서비스, 포인트이력서비스 객체 생성
        PointService pointService = new PointService();
        HistoryService historyService = new HistoryService();
        // 유저포인트 조회
        long point = pointService.findPointById(id).point();
        // 적립이력 추가
        historyService.insertHistory(id, point + amount, TransactionType.CHARGE, System.currentTimeMillis());
        // 유저포인트 적립
        return pointService.updatePointById(id, point + amount);
    }
    
    /**
     * 특정 유저의 포인트를 사용하는 기능
     */
    @PatchMapping("{id}/use")
    public synchronized UserPoint use(
            @PathVariable long id,
            @RequestBody long amount
    ) throws Exception {
        // 포인트서비스, 포인트이력서비스 객체 생성
        PointService pointService = new PointService();
        HistoryService historyService = new HistoryService();
        // 유저포인트 조회
        long point = pointService.findPointById(id).point();
        // 소지한 포인트 보다 적을 경우 반환
        if (point < amount) {
            throw new Exception("소지한 포인트가 부족합니다!!");
        }
        // 사용이력 추가
        historyService.insertHistory(id, point - amount, TransactionType.USE, System.currentTimeMillis());
        // 유저포인트 사용
        return pointService.updatePointById(id, point - amount);
    }
}
