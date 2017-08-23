package de.dominikwieners.androidhive.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.dominikwieners.androidhive.model.Post;


/**
 * Created by dominikwieners on 23.08.17.
 */

public class PostDB {

    private  Context context;
    private static PostDB myInstance;

    public PostDB (Context context){
        this.context = context;
    }

    public static PostDB getInstance (Context context){
        if (myInstance == null){
            myInstance = new PostDB(context);
        }

        return myInstance;
    }


    //Definition der Attribute der DB
    public static abstract class PostItem implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLNAME_POSTID = "postID";
        public static final String COLNAME_TITLE = "title";
        public static final String COLNAME_ISFAV = "isFavorite";
    }

    //String zum erstellen der DB
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PostItem.TABLE_NAME + "(" +
                    PostItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PostItem.COLNAME_POSTID + " INT," +
                    PostItem.COLNAME_TITLE + " TEXT," +
                    PostItem.COLNAME_ISFAV + " TINYINT(1)" +
                    ")";

    //String zum entfernen der DB
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PostItem.TABLE_NAME;

    //Datenbank Helfer zum Erzeugen der DB
    public class TodoItemDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Post.db";

        public TodoItemDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

        /********
         * READ
         */

    public boolean getDbPostIsFav(int postID){

        TodoItemDbHelper helper = new TodoItemDbHelper(context);

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        ArrayList<Post> postList = new ArrayList<>();

        boolean isFavorite = false;

        try {

            Cursor cursor = sqLiteDatabase.query(PostItem.TABLE_NAME, new String[]{PostItem._ID, PostItem.COLNAME_POSTID, PostItem.COLNAME_TITLE, PostItem.COLNAME_ISFAV}, null, null, null, null, null);

            try {
                while (cursor.moveToNext()) {
                    Post tmpPost = new Post(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3));
                    postList.add(tmpPost);
                }


            } finally {
                cursor.close();
            }

        }finally {
            sqLiteDatabase.close();
        }

        for(Post post: postList ){
            if(post.getWpPostId() == postID){
                Log.d("SelectedItem", post.getWpTitle());
                 isFavorite = post.isFavorite();
            }
        }

        return isFavorite;



    }

    public long insert (int wpPostID, String wpTitle, boolean isFavorite){

        //Data heraus holen aus der DB
        TodoItemDbHelper helper = new TodoItemDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        try{
            ContentValues values = new ContentValues();
            //Zuordnung spalten und values
            values.put(PostItem.COLNAME_POSTID, wpPostID);
            values.put(PostItem.COLNAME_TITLE, wpTitle);
            values.put(PostItem.COLNAME_ISFAV, isFavorite);

            return db.insert(PostItem.TABLE_NAME, PostItem.COLNAME_TITLE, values);
        }finally {
            db.close();
        }


    }


    public int update(Post post){

        TodoItemDbHelper helper = new TodoItemDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        try{
            ContentValues values = new ContentValues();
            values.put(PostItem.COLNAME_TITLE, post.getWpTitle());
            values.put(PostItem.COLNAME_ISFAV, post.isFavorite());

            //Ist die ID die ID aus dem Post Objekt
            String whereClause = PostItem._ID + " LIKE ?";
            String[] whereArgs = {String.valueOf(post.getId())};

            return db.update(PostItem.TABLE_NAME, values, whereClause, whereArgs);

        }finally {
            db.close();
        }

    }


    public int delete(int postID){

        TodoItemDbHelper helper = new TodoItemDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        try{

            //Ist die ID die ID aus dem Post Objekt
            String whereClause = PostItem.COLNAME_POSTID + " LIKE ?";
            String[] whereArgs = {String.valueOf(postID)};

            return db.delete(PostItem.TABLE_NAME, whereClause, whereArgs);

        }finally {
            db.close();
        }

    }


}





