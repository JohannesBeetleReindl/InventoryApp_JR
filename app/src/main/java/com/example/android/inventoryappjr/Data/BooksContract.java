package com.example.android.inventoryappjr.Data;

import android.provider.BaseColumns;


/**
 * API Contract for the inventory app.
 */
public final class BooksContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BooksContract() {
    }

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class BooksEntry implements BaseColumns {

        /**
         * Name of database table for products
         */
        public final static String TABLE_NAME = "products";

        /**
         * Unique ID number for the pet (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME = "productName";

        /**
         * Price of the product.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_PRICE = "price";

        /**
         * Quantity of products.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";

        /**
         * books supplier.
         * <p>
         * The only possible values are {@link #SUPPLIER_UNKNOWN}, {@link #SUPPLIER_PENGUIN},
         * {@link #SUPPLIER_HARPERCOLLINS}, {@link #SUPPLIER_SIMONSCHUSTER},{@link #SUPPLIER_HACHETTE},
         * or {@link #SUPPLIER_MACMILLAN}
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_SUPPLIER = "supplier";

        /**
         * Phone number of the supplier.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_NUMBER = "supplierNumber";

        /**
         * Possible values for the suppliers.
         */
        public static final int SUPPLIER_UNKNOWN = 0;
        public static final int SUPPLIER_PENGUIN = 1;
        public static final int SUPPLIER_HARPERCOLLINS = 2;
        public static final int SUPPLIER_SIMONSCHUSTER = 3;
        public static final int SUPPLIER_HACHETTE = 4;
        public static final int SUPPLIER_MACMILLAN = 5;

    }

}