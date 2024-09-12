package dev.shrishri1108.JavaRoomMVVM.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dev.shrishri1108.JavaRoomMVVM.Dao.ActorImageDao;
import dev.shrishri1108.JavaRoomMVVM.Modal.ActorImage;

@Database(entities = {ActorImage.class}, version = 2)
public abstract class ActorDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ActorDatabase";
    private static volatile ActorDatabase INSTANCE;
    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsynTask(INSTANCE);
        }
    };

    public static ActorDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ActorDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ActorDatabase.class,
                                    DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ActorImageDao actorDao();

    static class PopulateAsynTask extends AsyncTask<Void, Void, Void> {
        private final ActorImageDao actorDao;

        PopulateAsynTask(ActorDatabase actorDatabase) {
            actorDao = actorDatabase.actorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            actorDao.deleteAll();
            return null;
        }
    }
}
