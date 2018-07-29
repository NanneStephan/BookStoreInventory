package nl.nannestephan.bookstoreinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {
    }
    public static final String CONTENT_AUTHORITY = "nl.nannestephan.bookstoreinventory";

    public static final Uri BASE_CONTENT_URI = Uri.parse( "content://" + CONTENT_AUTHORITY);

    public static final String PATH_BOOKS = "bookstoreinvetory";

    public static final class BookEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        //Name DataBase table
        public final static String TABLE_NAME = "books";

        //ID in DataBase
        public final static String _ID = BaseColumns._ID;
        //Name of Book
        public final static String COLUMN_BOOK_NAME = "name";
        // Author of Book
        public final static String COLUMN_BOOK_AUTHOR = "author";
        //category
        public final static String COLUMN_BOOK_CATEGORY = "category";
        //Price of book
        public final static String COLUMN_PRICE_BOOK = "price";
        //Quantity
        public final static String COLUMN_QUANTITY = "quantity";
        //Supplier of the book
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        //Phone of hte supplier
        public final static String COLUMN_SUPPLIER_PHONE = "phone_supplier";

    }
}
