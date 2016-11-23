package lolu.wrkmbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public MySQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSITION_TABLE = "CREATE TABLE "+TABLE_POSITIONS+" ( " +
                KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT )";

        Log.d("OnCreate",CREATE_POSITION_TABLE);
        // create books table
        db.execSQL(CREATE_POSITION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_POSITIONS);

        this.onCreate(db);

    }
}
