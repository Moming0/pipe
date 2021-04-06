package com.bs;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.service.LogPressureService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class PipeWebApplicationTests {

    @Resource
    private LogPressureService logPressureService;

	@Test
	void contextLoads() {

            Double d = 1.123456;
            LogPressure log1 = new LogPressure();
            log1.setReadNumber(d);
            System.out.println(log1);
            LogPressure log2 = new LogPressure(1, 2, new Date(), d, "a", "a", "a", new Date(), new Date(), false);

            System.out.println(log2);
	}

}
