package android.example.com.productsinventory.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Products.db";

    private static final String INTEGER_PK = " INTEGER PRIMARY KEY";

    private static final String TEXT_TYPE = " TEXT";

    private static final String REAL_TYPE = " REAL";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BLOB_TYPE = " BLOB";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + ProductsContract.ProductEntry.TABLE_NAME + " (" +
                    ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID + INTEGER_PK + COMMA_SEP +
                    ProductsContract.ProductEntry.COLUMN_NAME_PRODUCT + TEXT_TYPE + COMMA_SEP +
                    ProductsContract.ProductEntry.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP +
                    ProductsContract.ProductEntry.COLUMN_NAME_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    ProductsContract.ProductEntry.COLUMN_NAME_SUPPLIER + TEXT_TYPE + COMMA_SEP +
                    ProductsContract.ProductEntry.COLUMN_NAME_IMAGE + BLOB_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ProductsContract.ProductEntry.TABLE_NAME;


    public ProductsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Get all products stored in database
     */
    public Cursor getAllProducts() {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID,
                ProductsContract.ProductEntry.COLUMN_NAME_PRODUCT,
                ProductsContract.ProductEntry.COLUMN_NAME_PRICE,
                ProductsContract.ProductEntry.COLUMN_NAME_QUANTITY,
                ProductsContract.ProductEntry.COLUMN_NAME_SUPPLIER,
                ProductsContract.ProductEntry.COLUMN_NAME_IMAGE,

        };

        String sortOrder =
                ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID + " ASC";

        Cursor c = db.query(
                ProductsContract.ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return c;
    }


    /**
     * Insert the new row, returning the primary key value of the new row
     */
    public long insertProduct(ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase();

        long newRowId;

        newRowId = db.insert(
                ProductsContract.ProductEntry.TABLE_NAME,
                ProductsContract.ProductEntry.COLUMN_NAME_NULLABLE,
                values);

        return newRowId;
    }


    /**
     * Update specific element from a Database
     */
    public int updateQuantityById(int productId, int newQuantity) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductsContract.ProductEntry.COLUMN_NAME_QUANTITY, newQuantity);

        String selection = ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(productId)};

        int count = db.update(
                ProductsContract.ProductEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public Cursor getProductById(int productId) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID,
                ProductsContract.ProductEntry.COLUMN_NAME_PRODUCT,
                ProductsContract.ProductEntry.COLUMN_NAME_PRICE,
                ProductsContract.ProductEntry.COLUMN_NAME_QUANTITY,
                ProductsContract.ProductEntry.COLUMN_NAME_SUPPLIER,
                ProductsContract.ProductEntry.COLUMN_NAME_IMAGE,

        };


        String selection = "entryId = ?";

        String[] selectionArgs = new String[] {
                String.valueOf(productId),
        };

        String sortOrder =
                ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID + " ASC";

        Cursor c = db.query(
                ProductsContract.ProductEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return c;
    }

    public void deleteElementById(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ProductsContract.ProductEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(productId)};
        db.delete(ProductsContract.ProductEntry.TABLE_NAME, selection, selectionArgs);

    }
}