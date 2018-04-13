package com.alex.qasystem;

import com.alex.qasystem.util.BCrypt;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BCryptTest {

    @Test
    public void test() {
        String hashed = BCrypt.hashpw("12345", BCrypt.gensalt());
        System.out.println(hashed);
        System.out.println(hashed.length());
        assertThat(BCrypt.checkpw("12345", hashed), is(true));
    }
}
