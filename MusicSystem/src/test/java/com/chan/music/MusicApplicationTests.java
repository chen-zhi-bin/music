package com.chan.music;

import com.chan.music.utils.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Constants.User.DEFAULT_AVATAR);
    }

}
