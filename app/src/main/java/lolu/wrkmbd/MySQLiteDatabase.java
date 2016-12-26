package lolu.wrkmbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 23/11/2016.
 */

public class MySQLiteDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DataPi";

    private static final String TABLE_POSITIONS = "pidata";

    // Books Table Columns names
    private static final String KEY_ID = "valeur";
    private static final String KEY_VALUEMBED = "valeur_mbed";

    public MySQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSITION_TABLE = "CREATE TABLE "+TABLE_POSITIONS+" ( " +
                KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT"+ KEY_VALUEMBED + " INTEGER" + ")";

        Log.d("OnCreate",CREATE_POSITION_TABLE);
        // create books table
        db.execSQL(CREATE_POSITION_TABLE);

    }



    void addValueMbed(MbedModel mbdModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VALUEMBED, mbdModel.getValueMbed());

        // Inserting Row
        db.insert(TABLE_POSITIONS, null, values);
        db.close(); // Closing database connection
    }

    MbedModel getMbedValue(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POSITIONS, new String[] { KEY_ID,
                        KEY_VALUEMBED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MbedModel mbedModel = new MbedModel(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)));
        // return contact
        return mbedModel;
    }

    public List<MbedModel> getAllMbedValues() {
        List<MbedModel> mbedValueList = new ArrayList<MbedModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POSITIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MbedModel mbedValue = new MbedModel();
                mbedValue.setId(Integer.parseInt(cursor.getString(0)));
                mbedValue.setValueMbed(Integer.parseInt(cursor.getString(0)));

                mbedValueList.add(mbedValue);
            } while (cursor.moveToNext());
        }


        return mbedValueList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_POSITIONS);

        this.onCreate(db);

    }
}
