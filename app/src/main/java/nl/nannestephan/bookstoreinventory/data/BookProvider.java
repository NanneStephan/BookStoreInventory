package nl.nannestephan.bookstoreinventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import nl.nannestephan.bookstoreinventory.data.BookContract.BookEntry;

public class BookProvider extends ContentProvider {

    public static final String LOG_TAG = BookProvider.class.getSimpleName();
    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int BOOKS = 100;
    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int BOOK_ID = 101;
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS, BOOKS);
        sUriMatcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOKS + "/#", BOOK_ID);
    }

    private BookDbHelper mBookDbHelper;

    @Override
    public boolean onCreate() {
        mBookDbHelper = new BookDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mBookDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {

            case BOOKS:
                cursor = database.query(BookEntry.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;

            case BOOK_ID:

                selection = BookEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(BookEntry.TABLE_NAME, projection, selection, selectionArgs
                        , null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {
        //Check if Name is not null
        String name = values.getAsString(BookEntry.COLUMN_BOOK_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Requires Book Name");
        }
        //Check Author
        String author = values.getAsString(BookEntry.COLUMN_BOOK_AUTHOR);
        if (author == null) {
            throw new IllegalArgumentException("Every Book has a Author");
        }
        String category = values.getAsString(BookEntry.COLUMN_BOOK_CATEGORY);
        if (category == null) {
            throw new IllegalArgumentException("Every Book has a Category");
        }
        Double price = values.getAsDouble(BookEntry.COLUMN_PRICE_BOOK);
        if (price == null) {
            throw new IllegalArgumentException("Requires Price");
        }
        //Check Suppliers Name
        String supplier_name = values.getAsString(BookEntry.COLUMN_SUPPLIER_NAME);
        if (supplier_name == null) {
            throw new IllegalArgumentException("Requires suppliers Name");
        }
        //Check Suppliers phone
        String phone_supplier = values.getAsString(BookEntry.COLUMN_SUPPLIER_PHONE);
        if (phone_supplier == null) {
            throw new IllegalArgumentException("Requires suppliers phone number");
        }

        // Get writeable database
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(BookEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return updateBook(uri, contentValues, selection, selectionArgs);
            case BOOK_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateBook(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateBook(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //Name
        if (values.containsKey(BookEntry.COLUMN_BOOK_NAME)) {
            String name = values.getAsString(BookEntry.COLUMN_BOOK_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Requires Book Name");
            }
        }
        if (values.containsKey(BookEntry.COLUMN_BOOK_AUTHOR)) {
            String author = values.getAsString(BookEntry.COLUMN_BOOK_AUTHOR);
            if (author == null) {
                throw new IllegalArgumentException("Every Book has a Author");
            }
        }
        if (values.containsKey(BookEntry.COLUMN_BOOK_CATEGORY)) {
            String category = values.getAsString(BookEntry.COLUMN_BOOK_CATEGORY);
            if (category == null) {
                throw new IllegalArgumentException("Every Book has a Category");
            }
        }
        if (values.containsKey(BookEntry.COLUMN_PRICE_BOOK)) {
            Double price = values.getAsDouble(BookEntry.COLUMN_PRICE_BOOK);
            if (price == null) {
                throw new IllegalArgumentException("Requires Price");
            }
        }
        if (values.containsKey(BookEntry.COLUMN_SUPPLIER_NAME)) {
            String supplier_name = values.getAsString(BookEntry.COLUMN_SUPPLIER_NAME);
            if (supplier_name == null) {
                throw new IllegalArgumentException("Requires suppliers Name");
            }
        }
        if (values.containsKey(BookEntry.COLUMN_SUPPLIER_PHONE)) {
            String phone_supplier = values.getAsString(BookEntry.COLUMN_SUPPLIER_PHONE);
            if (phone_supplier == null) {
                throw new IllegalArgumentException("Requires suppliers phone number");
            }
        }
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(BookEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOK_ID:
                // Delete a single row given by the ID in the URI
                selection = BookEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;

    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return BookEntry.CONTENT_LIST_TYPE;
            case BOOK_ID:
                return BookEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
