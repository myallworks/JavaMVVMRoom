package dev.shrishri1108.JavaRoomMVVM.ViewModal;

import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import dev.shrishri1108.JavaRoomMVVM.Modal.ActorWithStoredImage;
import dev.shrishri1108.JavaRoomMVVM.Repository.ActorRespository;
import dev.shrishri1108.JavaRoomMVVM.Repository.OnListUpdated;

public class ActorViewModal extends AndroidViewModel implements OnListUpdated {

    private final ActorRespository actorRespository;
    public MutableLiveData<List<ActorWithStoredImage>> __AllActor = new MutableLiveData<>();
    public LiveData<List<ActorWithStoredImage>> AllActorsUpdated = __AllActor;
    private int selectedItemPos = -1;
    private int selectedActorId = -1;

    public ActorViewModal(@NonNull Application application) {
        super(application);
        actorRespository = new ActorRespository(application, this);
    }

    public void updateImage(ContentResolver contentResolver, Uri imageUri) {
        try {
            InputStream imageStream = contentResolver.openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageByteArray = byteArrayOutputStream.toByteArray();

            actorRespository.updateImage(selectedItemPos, selectedActorId, imageByteArray, this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateSelectedItemPost(int itemPos, int actorIds) {
        selectedItemPos = itemPos;
        selectedActorId = actorIds;
    }

    @Override
    public void listUpdated(List<ActorWithStoredImage> storedImageList) {
            __AllActor.postValue(storedImageList);
    }

}
