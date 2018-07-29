package nl.nannestephan.bookstoreinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.nannestephan.bookstoreinventory.data.BookContract.BookEntry;

public class BookDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BookDbHelper.class.getSimpleName();

    //Database file Name
    private final static String DATABASE_NAME = "bookshop.db";

    // Database Version
    private final static int DATABASE_VERSION = 2;


    public BookDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating the SQL table
    @Override
    public void onCreate(SQLiteDatabase BookDatabase){
        //the String for the SQLite input to create the Table
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_AUTHOR + " TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_CATEGORY + " TEXT NOT NULL, "
                + BookEntry.COLUMN_PRICE_BOOK + " INTEGER NOT NULL, "
                + BookEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + BookEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + BookEntry.COLUMN_SUPPLIER_PHONE + " INTEGER NOT NULL);";

        BookDatabase.execSQL(SQL_CREATE_BOOKS_TABLE);

    }
    // This is going to Upgrade the BooksDataBase
    @Override
    public void onUpgrade(SQLiteDatabase BookDatabase, int oldVersion, int newVersion){
        // Nothing here yet I think it's for the next lesson :P
    }

}
