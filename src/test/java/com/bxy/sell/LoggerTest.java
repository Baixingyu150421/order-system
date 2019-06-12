package com.bxy.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.Buffer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
//        private final static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        @Test
        @Ignore
        public void test1(){
            String name ="tom";
            String password = "154135";
            log.info("name : {} , password : {}" ,name,password);
            log.debug("debug...");
            log.info("info...");
            log.error("error...");
            log.warn("warn...");
        }

    public static void main(String[] args) {

    }
}
