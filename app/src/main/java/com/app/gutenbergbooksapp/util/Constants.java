package com.app.gutenbergbooksapp.util;

/**
 *  This file contain all the constants.
 */
public class Constants {
    public static String  KEY_TOPIC_NAME = "topic_name";

    public static String BOOK_VIEW_HTML = "HTML";
    public static String BOOK_VIEW_PDF = "PDF";
    public static String BOOK_VIEW_TXT = "TXT";
    public static String BOOK_VIEW_ZIP = "ZIP";

    public static enum  PAGE_TYPE {
        NEXT,
        PREVIOUS
    }

   public static enum  REQUEST_TYPE {
        SEARCH_TYPE,
        TOPIC_TYPE
    }

   public static int VIEW_TYPE_LOADING = 0;
    public static int VIEW_TYPE_ITEM = 1;
}
