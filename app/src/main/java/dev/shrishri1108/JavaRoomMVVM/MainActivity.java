package dev.shrishri1108.JavaRoomMVVM;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import dev.shrishri1108.JavaRoomMVVM.Adapter.ActorAdapter;
import dev.shrishri1108.JavaRoomMVVM.Adapter.OnClickListener;
import dev.shrishri1108.JavaRoomMVVM.Modal.ActorWithStoredImage;
import dev.shrishri1108.JavaRoomMVVM.ViewModal.ActorViewModal;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final int IMG_REQUEST_CODE = 1011;
    private static final int PERMISSION_CODE = 109;
    private ActorViewModal actorViewModal;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_CODE);
        } else {
            // Permission already granted, proceed with file operations
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final ActorAdapter actorAdapter = new ActorAdapter(this, new ArrayList<ActorWithStoredImage>(), this);
        recyclerView.setAdapter(actorAdapter);

        actorViewModal = new ViewModelProvider(this).get(ActorViewModal.class);

        actorViewModal.AllActorsUpdated.observe(this, new Observer<List<ActorWithStoredImage>>() {
            @Override
            public void onChanged(List<ActorWithStoredImage> actorList) {
                if (actorList != null) {
                    actorAdapter.setAllActors(actorList);
                    Log.d("main", "onChanged: " + actorList);
                }
            }
        });

    }

    @Override
    public void itemClicked(int item_pos, Integer actorId) {
        actorViewModal.updateSelectedItemPost(item_pos, actorId);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMG_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                actorViewModal.updateImage(getContentResolver(), imageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can perform file operations
            } else {
                // Permission denied, show a message to the user
            }
        }
    }

}
