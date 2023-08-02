package com.beetech.api_intern.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileNameEditor {
    private FileNameEditor() {
        throw new IllegalStateException("Utility class");
    }

    public static String appendDatetime(String fileName) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss", Locale.US);
        String formattedDate = formatter.format(new Date());

        int dotIndex = fileName.lastIndexOf('.');
        String extension = "";
        String newFilename = fileName;
        if (dotIndex != -1) {
            extension = fileName.substring(dotIndex);
            newFilename = fileName.substring(0, dotIndex);
        }

        return newFilename + "_" + formattedDate + extension;
    }
}
