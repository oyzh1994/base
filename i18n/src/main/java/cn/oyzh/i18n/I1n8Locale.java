package cn.oyzh.i18n;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

@Data
@AllArgsConstructor
public class I1n8Locale {

    private String name;

    private Locale locale;

    private String displayName;

    private String description;
}
