package io.hhplus.tdd.point.service;

import io.hhplus.tdd.database.UserPointTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.point.UserPoint;

class PointServiceTest {

    // 유저 아이디로 포인트 조회가 가능한가? 확인해보았습니다.
    @Test
    @DisplayName("아이디로 포인트 조회")
    void findPointById() {

        PointService pointService = new PointService();
        UserPointTable userPointTable = mock(UserPointTable.class);

        // given
        Long id = 1L;

        // when
        when(userPointTable.selectById(id)).thenReturn(pointService.findPointById(id));
        UserPoint testUser = userPointTable.selectById(id);

        // then
        // 이 둘을 비교하는게 맞나요?? 테스트 시나리오를 이상하게 이해해서;;;;
        assertEquals(id, testUser.id());
        assertEquals(0, testUser.point());

    }

    // 충전 및 사용 둘다 결국 포인트를 갱신하는 것이므로 서비스단에서는 요청한 포인트 값으로 갱신만 하도록 작성하였습니다.
    // 충전 로직 및 사용로직(포인트 부족시 사용불가 등) 은 컨트롤러에서 구현예정입니다.
    @Test
    @DisplayName("아이디로 포인트 갱신")
    void updatePointById() {

        PointService pointService = new PointService();
        UserPointTable userPointTable = mock(UserPointTable.class);

        // given
        Long id = 1L;
        Long point = 100L;

        // when
        when(userPointTable.insertOrUpdate(id,point))
                .thenReturn(pointService.updatePointById(id,point));
        UserPoint testUser = userPointTable.insertOrUpdate(id,point);

        // then
        // 아이디1에 포인트 100이 들어 있는지 체크
        assertEquals(id, testUser.id());
        assertEquals(point, testUser.point());
    }
}