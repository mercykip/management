package com.management.security.oauth.config;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class SimpleCORSFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
