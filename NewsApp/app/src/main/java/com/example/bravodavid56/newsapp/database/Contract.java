package com.example.bravodavid56.newsapp.database;

import android.provider.BaseColumns;

/**
 * Created by bravodavid56 on 7/24/2017.
 */

public class Contract {

    public static class TABLE_COLUMNS implements BaseColumns {
        public static final String TABLE_NAME="newsitems";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
        public static final String COLUMN_NAME_URL = "url";

        // Contract class defines the various parameters for the database, including table name and column names
    }


}
