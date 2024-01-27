package com.github.monaboiste.yetanovalidate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public void testMe() {
        log.info("Testing... {}, {}, {}", 1, 2, 3);
    }
}

class MakeSonarUnhappy {
    /**
     * This declaration should break the rule squid:S3077.
     */
    private volatile MakeSonarUnhappy instance;
}
