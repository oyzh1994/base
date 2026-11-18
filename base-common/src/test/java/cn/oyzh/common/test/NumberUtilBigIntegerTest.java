package cn.oyzh.common.test;

import cn.oyzh.common.util.NumberUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberUtilBigIntegerTest {

    private static final String LARGE_INTEGER = "9999999999999999999999999999";

    @Test
    public void parsesIntegersBeyondLongWithoutPrecisionLoss() {
        Number value = NumberUtil.parseNumber(LARGE_INTEGER);

        assertEquals(new BigInteger(LARGE_INTEGER), value);
    }

    @Test
    public void parsesDecimalsBeyondDoublePrecisionWithoutPrecisionLoss() {
        Number value = NumberUtil.parseNumber("12345678901234567890.123456789");

        assertEquals(new BigDecimal("12345678901234567890.123456789"), value);
    }

    @Test
    public void parsesScientificNotationAsBigDecimal() {
        Number value = NumberUtil.parseNumber("1.0000E+28");

        assertEquals(new BigDecimal("1.0000E+28"), value);
    }

    @Test
    public void comparesBothOperandsExactly() {
        BigInteger small = new BigInteger(LARGE_INTEGER);
        BigInteger large = small.add(BigInteger.ONE);

        assertTrue(NumberUtil.isLT(small, large));
        assertTrue(NumberUtil.isLTEq(small, small));
        assertTrue(NumberUtil.isEq(small, new BigDecimal(LARGE_INTEGER)));
        assertTrue(NumberUtil.isGT(large, small));
        assertTrue(NumberUtil.isGTEq(large, large));
        assertFalse(NumberUtil.isGT(small, large));
    }
}
