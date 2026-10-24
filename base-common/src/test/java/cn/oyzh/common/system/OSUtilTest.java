package cn.oyzh.common.system;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class OSUtilTest {

    @Test
    public void normalizesOsNameIndependentlyOfDefaultLocale() {
        String originalOsName = System.getProperty("os.name");
        Locale originalLocale = Locale.getDefault();
        try {
            System.setProperty("os.name", "Windows 11");
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals("WINDOWS 11", OSUtil.getOSType());

            System.setProperty("os.name", "Linux");
            assertEquals("LINUX", OSUtil.getOSType());
        } finally {
            if (originalOsName == null) {
                System.clearProperty("os.name");
            } else {
                System.setProperty("os.name", originalOsName);
            }
            Locale.setDefault(originalLocale);
        }
    }
}
