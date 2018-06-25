package com.example.android.inventoryappjr;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventoryappjr.Data.BooksDbHelper;
import com.example.android.inventoryappjr.Data.BooksContract;
import com.example.android.inventoryappjr.Data.BooksContract.BooksEntry;


/**
 * Displays list of pets that were entered and stored in the app.
 */
public class BooksActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private BooksDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BooksActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BooksDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BooksEntry._ID,
                BooksEntry.COLUMN_PRODUCT_NAME,
                BooksEntry.COLUMN_PRODUCT_PRICE,
                BooksEntry.COLUMN_PRODUCT_QUANTITY,
                BooksEntry.COLUMN_PRODUCT_SUPPLIER,
                BooksEntry.COLUMN_SUPPLIER_NUMBER
        };

        // Perform a query on the products table
        Cursor cursor = db.query(
                BooksEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        TextView displayView = (TextView) findViewById(R.id.text_view_books);


        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The products table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(BooksEntry._ID + " - " +
                    BooksEntry.COLUMN_PRODUCT_NAME + " - " +
                    BooksEntry.COLUMN_PRODUCT_PRICE + " - " +
                    BooksEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                    BooksEntry.COLUMN_PRODUCT_SUPPLIER + " - " +
                    BooksEntry.COLUMN_SUPPLIER_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BooksEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(BooksEntry.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = cursor.getColumnIndex(BooksEntry.COLUMN_PRODUCT_PRICE);
            int productQuantityColumnIndex = cursor.getColumnIndex(BooksEntry.COLUMN_PRODUCT_QUANTITY);
            int productSupplierColumnIndex = cursor.getColumnIndex(BooksEntry.COLUMN_PRODUCT_SUPPLIER);
            int productsupplierNumberColumnIndex = cursor.getColumnIndex(BooksEntry.COLUMN_SUPPLIER_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentProductPrice = cursor.getInt(productPriceColumnIndex);
                int currentProductQuantity = cursor.getInt(productQuantityColumnIndex);
                int currentProductSupplier = cursor.getInt(productSupplierColumnIndex);
                int currentSupplierNumber = cursor.getInt(productsupplierNumberColumnIndex);

               // int currentGender = cursor.getInt(genderColumnIndex);
               // int currentWeight = cursor.getInt(weightColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentProductName + " - " +
                        currentProductPrice + " - " +
                        currentProductQuantity+ " - " +
                        currentProductSupplier+ " - " +
                        currentSupplierNumber));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPet () {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BooksEntry.COLUMN_PRODUCT_NAME, "Toto");
        values.put(BooksEntry.COLUMN_PRODUCT_PRICE, 999);
        values.put(BooksEntry.COLUMN_PRODUCT_QUANTITY, 2 );
        values.put(BooksEntry.COLUMN_PRODUCT_SUPPLIER, BooksEntry.SUPPLIER_HARPERCOLLINS);
        values.put(BooksEntry.COLUMN_SUPPLIER_NUMBER, 123456789);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(BooksEntry.TABLE_NAME, null, values);

        Log.v("BooksActivity", "New row ID" + newRowId);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

