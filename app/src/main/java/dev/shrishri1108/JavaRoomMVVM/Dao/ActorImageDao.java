package dev.shrishri1108.JavaRoomMVVM.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import dev.shrishri1108.JavaRoomMVVM.Modal.ActorImage;

@Dao
public interface ActorImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActorImage(ActorImage actorImage);

    @Query("SELECT actorImage FROM actor_images WHERE id = :actorId")
    byte[] getImageByActorId(int actorId);

    @Query("DELETE FROM actor_images")
    void deleteAll();

    @Query("SELECT * FROM actor_images")
    List<ActorImage> getAllImages();

}
