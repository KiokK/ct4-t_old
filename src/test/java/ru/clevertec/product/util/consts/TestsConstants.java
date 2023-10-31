package ru.clevertec.product.util.consts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class TestsConstants {

    public static final UUID DEFAULT_UUID = UUID.fromString("346d425a-51ea-402d-a465-f2ce9decf3c8");
    public static final String DEFAULT_NAME =  "Apple";
    public static final String DEFAULT_DESCRIPTION =  "Prince From Asia";
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(0.55);
    public static final LocalDateTime DEFAULT_CREATED = LocalDateTime.of(2023, Month.OCTOBER, 26, 3,33,23);

    public static final UUID NEW_UUID = UUID.fromString("301d64aa-1ba5-45c8-9207-6dc87fbb3328");
    public static final String NEW_NAME = "Melon";
    public static final BigDecimal NEW_PRICE = new BigDecimal("0.23");
    public static final String NEW_DESCRIPTION = "Very melon";
    public static final UUID NO_EXCISTS_UUID = UUID.fromString("57bf0290-1821-43c3-94a2-9246fa7d0c86");
}
