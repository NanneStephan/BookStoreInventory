package nl.nannestephan.bookstoreinventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import nl.nannestephan.bookstoreinventory.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflating the listView
        return LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Find the Fields to populate
        TextView BookTextView = view.findViewById(R.id.book);
        TextView AuthorTextView = view.findViewById(R.id.author);
        TextView CategoryTextView = view.findViewById(R.id.category);
        TextView QuantityTextView = view.findViewById(R.id.quantity);
        TextView IdTextView = view.findViewById(R.id.id);

        //buttons
        Button IncreaseButton = view.findViewById(R.id.increase_supply);
        Button DecreaseButton = view.findViewById(R.id.decrease_supply);

        // get the column
        final int idColumnIndex = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));
        final int quantityColumnIndex = cursor.getInt(cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY));
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int categoryColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_CATEGORY);

        //link String with ColumnIndex
        String book = cursor.getString(nameColumnIndex);
        String author = cursor.getString(authorColumnIndex);
        String category = cursor.getString(categoryColumnIndex);

        //Set the Text
        BookTextView.setText(book);
        AuthorTextView.setText(author);
        CategoryTextView.setText(category);
        // +1 because 0 looks strange
        IdTextView.setText(Integer.toString(cursor.getPosition() + 1));
        QuantityTextView.setText(String.valueOf(quantityColumnIndex));

        // buttons
        IncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, idColumnIndex);

                int quantityDecrease = quantityColumnIndex + 1;

                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_QUANTITY, quantityDecrease);

                context.getContentResolver().update(uri, values, null, null);
            }
        });
        DecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, idColumnIndex);

                int quantityDecrease = quantityColumnIndex - 1;

                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_QUANTITY, quantityDecrease);

                context.getContentResolver().update(uri, values, null, null);

            }
        });
    }
}
