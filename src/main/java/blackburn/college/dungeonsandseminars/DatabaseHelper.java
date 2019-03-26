package blackburn.college.dungeonsandseminars;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    //defines database name
    private static String DATABASE_NAME = "Dungeons_and_Seminars";
    //version of database
    private static final int DATABASE_VERSION = 1;
    //defines the tables of the database
    private static final String TABLE_CHARACTERS = "Player_Characters";
    //defines the keys used in the database
    private static final String STRENGTH = "strength";
    private static final String DEXTERITY = "dexterity";
    private static final String CONSTITUTION = "constitution";
    private static final String INTELLIGENCE = "intelligence";
    private static final String WISDOM = "wisdom";
    private static final String CHARISMA = "charisma";
    private static final String KEY_NAME = "name";

    private static final String CREATE_TABLE_PC = "CREATE TABLE "
            + TABLE_CHARACTERS + "("  + KEY_NAME + " TEXT, "
            + STRENGTH + " TEXT, " + DEXTERITY + " TEXT, " + CONSTITUTION + " Text," + INTELLIGENCE + " TEXT, "
            + WISDOM + " TEXT, " + CHARISMA + " TEXT " + ");";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", CREATE_TABLE_PC);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS'" + TABLE_CHARACTERS +  "'");
        onCreate(db);
    }

    public long createPlayerCharacter(String name, int s, int d, int con, int i, int w, int c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(STRENGTH, s);
        values.put(DEXTERITY, d);
        values.put(CONSTITUTION, con);
        values.put(INTELLIGENCE, i);
        values.put(WISDOM, w);
        values.put(CHARISMA, c);

        long insert = db.insert(TABLE_CHARACTERS, null, values);
        db.close();
        return insert;
    }

    public void removePlayerCharacter(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHARACTERS, "name=?" , new String[] {name}); //remember this
    }

    public ArrayList<String> getPCList(){
        ArrayList<String> pcList = new ArrayList<>();
        String pc;
        String selectQuery = "SELECT * FROM " + TABLE_CHARACTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                pc = c.getString(c.getColumnIndex(KEY_NAME));
                pcList.add(pc);
            } while(c.moveToNext());
            Log.d("array", pcList.toString());
        }
        db.close();
        c.close();
        return pcList;
    }

    public String[] getCharacterStats(String name){
        String[] pcStats = new String[6];
        String selectQuery = "SELECT * FROM " + TABLE_CHARACTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        while (true) {
            if (c.getString(c.getColumnIndex(KEY_NAME)).equals(name)) {
                pcStats[0] = c.getString(c.getColumnIndex(STRENGTH));
                pcStats[1] = c.getString(c.getColumnIndex(DEXTERITY));
                pcStats[2] = c.getString(c.getColumnIndex(CONSTITUTION));
                pcStats[3] = c.getString(c.getColumnIndex(INTELLIGENCE));
                pcStats[4] = c.getString(c.getColumnIndex(WISDOM));
                pcStats[5] = c.getString(c.getColumnIndex(CHARISMA));
                break;
            } else {
                c.moveToNext();
            }
        }
        db.close();
        c.close();
        return pcStats;
    }

}
