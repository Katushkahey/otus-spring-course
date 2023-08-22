package com.homework.spring.util;

import org.bson.Document;

public interface RawResultPrinter {
    @SuppressWarnings("unchecked")
    void prettyPrintRawResult(Document document);
}
