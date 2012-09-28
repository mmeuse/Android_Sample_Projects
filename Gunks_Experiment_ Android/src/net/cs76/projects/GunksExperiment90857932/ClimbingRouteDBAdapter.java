/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ClimbingRouteDBAdapter
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ROUTENAME = "routeName";
    public static final String KEY_DIFFICULTY = "difficulty";
    public static final String KEY_VERTICALFT = "verticalFeet";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PITCHCOUNT = "pitchCount";
    public static final String KEY_ONWISHLIST = "onWishList";
    public static final String KEY_ONTICKLIST = "onTickList";
    public static final String KEY_IMAGENAME = "imageName";

    private static final String TAG = "NotesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE =
        "create table routes (_id integer primary key autoincrement, " 
        		+ "routeName text not null, difficulty text not null, verticalFeet text not null, description text not null, pitchCount text not null, onWishList text not null, onTickList text not null, imageName text not null);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "routes";
    private static final int DATABASE_VERSION = 2;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
            ClimbDictionary.PopulateClimbs(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS routes");
            onCreate(db);
        }
    }

    public ClimbingRouteDBAdapter(Context ctx) 
    {
        this.mCtx = ctx;
    }

    public ClimbingRouteDBAdapter open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        mDbHelper.close();
        mDb.close();
    }

    public static void createClimb(SQLiteDatabase db,String routeName, String routeDifficulty, String verticalFt, String description, String pitchCount, String OnWishList, String OnTickList, String ImageName) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROUTENAME, routeName);
        initialValues.put(KEY_DIFFICULTY, routeDifficulty);
        initialValues.put(KEY_VERTICALFT, verticalFt);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_PITCHCOUNT, pitchCount);
        initialValues.put(KEY_ONWISHLIST, OnWishList);
        initialValues.put(KEY_ONTICKLIST, OnTickList);
        initialValues.put(KEY_IMAGENAME, ImageName);
        
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteClimb(long rowId) 
    {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllClimbsAlphabetically() 
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ROUTENAME,KEY_DIFFICULTY,KEY_VERTICALFT,KEY_DESCRIPTION,KEY_PITCHCOUNT,KEY_ONWISHLIST,KEY_ONTICKLIST}, null, null, null, null,KEY_ROUTENAME);
    }
    
    public Cursor fetchAllClimbsByDifficulty() 
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ROUTENAME,KEY_DIFFICULTY,KEY_VERTICALFT,KEY_DESCRIPTION,KEY_PITCHCOUNT,KEY_ONWISHLIST,KEY_ONTICKLIST}, null, null, null, null, KEY_DIFFICULTY);
    }
    
    public Cursor fetchAllClimbsFromWishList() 
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ROUTENAME,KEY_DIFFICULTY,KEY_VERTICALFT,KEY_DESCRIPTION,KEY_PITCHCOUNT,KEY_ONWISHLIST,KEY_ONTICKLIST},KEY_ONWISHLIST + "='Yes'", null, null, null, null);
    }

    public Cursor fetchAllClimbsFromTickList() 
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ROUTENAME,KEY_DIFFICULTY,KEY_VERTICALFT,KEY_DESCRIPTION,KEY_PITCHCOUNT,KEY_ONWISHLIST,KEY_ONTICKLIST}, KEY_ONTICKLIST + "='Yes'", null, null, null, null);
    }
    
    public Cursor fetchClimb(long rowId) throws SQLException
    {
        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ROUTENAME,KEY_DIFFICULTY,KEY_VERTICALFT,KEY_DESCRIPTION,KEY_PITCHCOUNT,KEY_ONWISHLIST,KEY_ONTICKLIST,KEY_IMAGENAME}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        
        if (mCursor != null) 
        {
            mCursor.moveToFirst();
        }
        
        return mCursor;
    }

    public boolean updateClimb(long rowId, String OnWishList, String OnTickList) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ONWISHLIST, OnWishList);
        initialValues.put(KEY_ONTICKLIST, OnTickList);

        return mDb.update(DATABASE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
