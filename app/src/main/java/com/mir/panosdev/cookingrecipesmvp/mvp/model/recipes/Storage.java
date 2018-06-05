package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Storage extends SQLiteOpenHelper {

    private static final String TAG = Storage.class.getSimpleName();
    private final static int DATABASE_VERSION = 2;

    @Inject
    public Storage(Context context) {
        super(context, "recipes_db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);

        Log.d(TAG, "old version->" + oldVersion + " new version->" + newVersion);
    }


    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, recipe.getTitle());
        values.put(DESCRIPTION, recipe.getDescription());
        values.put(USER_ID, recipe.getUser().getUser_id());

        try {
            db.insert(TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        db.close();
    }

    public void dropDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public List<Recipe> getSavedRecipes() {
        List<Recipe> recipesList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Recipe recipe = new Recipe();
                            recipe.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                            recipe.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                            recipe.setUser(new User(cursor.getInt(cursor.getColumnIndex(USER_ID)), null, null));
                            recipesList.add(recipe);
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return recipesList;
    }

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String USER_ID = "user_id";
    private static final String TABLE_NAME = "recipes";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + TITLE + " TEXT NOT NULL, "
            + DESCRIPTION + " TEXT NOT NULL, " + USER_ID + " INTEGER NOT NULL);";
}
