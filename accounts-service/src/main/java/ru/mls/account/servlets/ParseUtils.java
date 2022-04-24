package ru.mls.account.servlets;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ParseUtils {
    static Long parseAccountId(HttpServletRequest req, String parameterName) {
        return parseValue(req, parameterName, Long::valueOf);
    }

    static BigDecimal parseMoney(HttpServletRequest req, String parameterName) {
        return parseValue(req, parameterName, value -> BigDecimal.valueOf(Double.parseDouble(value)));
    }

    private static <T> T parseValue(HttpServletRequest req, String parameterName, Function<String, T> converter) {
        String rawParameter = req.getParameter(parameterName);
        try {
            return converter.apply(rawParameter);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new RuntimeException(
                    String.format("Invalid value for parameter %s: '%s'", parameterName, rawParameter), ex
            );
        }
    }
}
