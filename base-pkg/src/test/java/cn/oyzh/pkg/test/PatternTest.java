package cn.oyzh.pkg.test;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class PatternTest {


    @Test
    public void test1(){
        Pattern pattern = Pattern.compile(".*.MF", Pattern.CASE_INSENSITIVE);
        System.out.println(pattern.matcher("xx/xx.MF").matches());
    }
}
