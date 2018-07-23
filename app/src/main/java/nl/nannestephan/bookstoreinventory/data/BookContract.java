package nl.nannestephan.bookstoreinventory.data;

import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {
    }

    public static final class BookEntry implements BaseColumns {

        //Name DataBase table
        public final static String TABLE_NAME = "books";

        //ID in DataBase
        public final static String _ID = BaseColumns._ID;

        //Name of Book
        public final static String COLUMN_BOOK_NAME = "name";
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
