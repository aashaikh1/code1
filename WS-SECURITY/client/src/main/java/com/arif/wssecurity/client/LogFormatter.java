package com.arif.wssecurity.client;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {
    public synchronized String format(LogRecord record) {
        String longForm = super.format(record);
        return longForm.indexOf("INFO: ") > 0 ? longForm.substring(longForm.indexOf("INFO: ") + 6) : longForm;
    }
}
