package com.github.monaboiste.yetanovalidate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MainTest {

    @Test
    void test() {
        log.info("In test...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        new Main().testMe();
    }
}