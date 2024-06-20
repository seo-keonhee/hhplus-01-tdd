package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.service.HistoryService;
import io.hhplus.tdd.point.service.PointService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointController pointController;

    @Test
    void charge() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        //given
        long id = 1;
        long amount = 10;
        int i = 1;

        UserPoint userPoint = UserPoint.empty(id);
        when(pointController.charge(id, amount)).thenReturn(new UserPoint(id, (long)i*amount, System.currentTimeMillis()));

        //when
        for (i = 1; i <= 20; i++) {
            executorService.submit(() -> {
                try {
                    // Table클래스를 DB 처럼 쓰는 방법을 도무지 모르겠어요 ㅠㅠ
                    // 아무리 돌려도 table클래스가 초기화 됩니다. ㅠㅠ

                    mockMvc.perform(patch("/point/" + id + "/charge")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"amount\": " + amount + "}"))
                                    .andExpect(status().isOk()) // 응답 상태 검증
                                    .andExpect(content().string("true"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        //then
        List<PointHistory> testList = pointController.history(id);
        assertEquals(20, testList.size());
        assertEquals(id, pointController.point(id).id());
        assertEquals(200, pointController.point(id).point());
    }

    @Test
    void use() {
// 포인트 사용은 시간부족으로 작성 실패
    }
}