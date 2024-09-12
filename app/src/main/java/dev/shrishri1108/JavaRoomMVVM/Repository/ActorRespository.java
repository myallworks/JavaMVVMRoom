package dev.shrishri1108.JavaRoomMVVM.Repository;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.shrishri1108.JavaRoomMVVM.Dao.ActorImageDao;
import dev.shrishri1108.JavaRoomMVVM.Database.ActorDatabase;
import dev.shrishri1108.JavaRoomMVVM.Modal.Actor;
import dev.shrishri1108.JavaRoomMVVM.Modal.ActorImage;
import dev.shrishri1108.JavaRoomMVVM.Modal.ActorWithStoredImage;
import dev.shrishri1108.JavaRoomMVVM.Modal.api_response.ApiResponse;
import dev.shrishri1108.JavaRoomMVVM.Network.Api;
import dev.shrishri1108.JavaRoomMVVM.Network.Retrofit;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public class ActorRespository {
    private final ActorDatabase database;
    private List<ActorWithStoredImage> AllActorsFinalList;

    public ActorRespository(Application application, OnListUpdated onListUpdated) {
        database = ActorDatabase.getInstance(application);
        new FetchApiTask(onListUpdated).execute();
    }

    public void updateImage(final int item_pos, final int oldActorId, final byte[] imageByteArray, OnListUpdated onListUpdated) {
        new InsertAsynTask(database, item_pos, onListUpdated).execute(new ActorImage(oldActorId, imageByteArray));
    }

    class InsertAsynTask extends AsyncTask<ActorImage, Void, ActorImage> {
        private final OnListUpdated onListUpdated;
        private final ActorImageDao actorDao;
        private final int item_position;

        InsertAsynTask(ActorDatabase actorDatabase, int item_position, OnListUpdated onListUpdated) {
            actorDao = actorDatabase.actorDao();
            this.item_position = item_position;
            this.onListUpdated = onListUpdated;
        }

        @Override
        protected ActorImage doInBackground(ActorImage... params) {
            actorDao.insertActorImage(params[0]);
            return params[0];
        }

        @Override
        protected void onPostExecute(ActorImage actorImage) {
            byte[] storeImage = actorImage.getActorImage();
            final Bitmap bitmaps = BitmapFactory.decodeByteArray(storeImage, 0, storeImage.length);
            if (AllActorsFinalList != null) {
                AllActorsFinalList.get(item_position).setStoredImage(bitmaps);
                onListUpdated.listUpdated(AllActorsFinalList);
            }
        }
    }

    public class FetchApiTask extends AsyncTask<Integer, Void, List<ActorWithStoredImage>> {
        private final OnListUpdated onListUpdated;

        public FetchApiTask(OnListUpdated onListUpdated) {
            this.onListUpdated = onListUpdated;
        }

        @Override
        protected List<ActorWithStoredImage> doInBackground(Integer... params) {

            try {
                Api apiService = new Retrofit().api;
                Response<ApiResponse> response = apiService.getAllActors().execute();

                if (response.isSuccessful() && response.body() != null) {
                    List<Actor> newApiLstData = response.body().getActors();
                    List<ActorWithStoredImage> newActorsList = new ArrayList<>();
                    List<ActorImage> actorImageList = database.actorDao().getAllImages();
                    for (int i = 0; i < newApiLstData.size(); i++) {
                        Actor actor = newApiLstData.get(i);
                        byte[] actorStoredImage = null;
                        if (actorImageList != null)
                            for (int j = 0; j < actorImageList.size(); j++) {
                                if (actorImageList.get(j).getActorId() == actor.getId()) {
                                    actorStoredImage = actorImageList.get(j).getActorImage();
                                    break;
                                }
                            }
                        ActorWithStoredImage newActorWStoredImage = new ActorWithStoredImage(null, actor);
                        if (actorStoredImage == null) {
                            if (AllActorsFinalList != null)
                                for (int k = 0; k < AllActorsFinalList.size(); k++) {
                                    ActorWithStoredImage ithActor = AllActorsFinalList.get(k);
                                    if (Objects.equals(ithActor.getActor().getId(), actor.getId())) {
                                        newActorWStoredImage.setStoredImage(ithActor.getStoredImage());
                                        break;
                                    }
                                }
                        } else {
                            final Bitmap bitmap = BitmapFactory.decodeByteArray(actorStoredImage, 0, actorStoredImage.length);
                            newActorWStoredImage.setStoredImage(bitmap);
                        }
                        newActorsList.add(newActorWStoredImage);
                    }
                    return newActorsList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ArrayList();
        }

        @Override
        protected void onPostExecute(List<ActorWithStoredImage> newActorsList) {
            if (newActorsList != null) {

                AllActorsFinalList = newActorsList;
                onListUpdated.listUpdated(AllActorsFinalList);
            }
        }
    }

}
