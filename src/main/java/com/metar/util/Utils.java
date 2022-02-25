package com.metar.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Utils {

    public static Set METAR_PROPERTIES_SET = new HashSet();

    static {
        METAR_PROPERTIES_SET.addAll(
                List.of("timestamp", "wind", "visibility", "temperature", "dew")
        );
    }
}
