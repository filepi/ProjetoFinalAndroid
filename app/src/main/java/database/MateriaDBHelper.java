package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felipe on 27/09/16.
 */

public class MateriaDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dbMaterias";
    private static final int DB_VERSION = 1;


    public MateriaDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQueryCreateTable =
                "CREATE TABLE " + MateriaContract.TABLE_NAME + " (" +
                MateriaContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MateriaContract.COL_MATERIA_DESCRICAO + " TEXT NOT NULL, " +
                MateriaContract.COL_MATERIA_PROFESSOR + " TEXT, " +
                MateriaContract.COL_MATERIA_ATIVO + " INTEGER)";
        db.execSQL(sqlQueryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int i, int i1) {

    }
}
