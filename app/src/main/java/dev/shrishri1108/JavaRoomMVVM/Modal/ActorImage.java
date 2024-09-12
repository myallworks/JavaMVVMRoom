package dev.shrishri1108.JavaRoomMVVM.Modal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "actor_images", indices = @Index(value = {"id"}, unique = true))
public class ActorImage {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int actorId;

    @ColumnInfo(name = "actorImage")
    private final byte[] actorImage;

    public ActorImage(int actorId, byte[] actorImage) {
        this.actorId = actorId;
        this.actorImage = actorImage;
    }

    // Getters and setters
    public int getActorId() {
        return actorId;
    }

    public byte[] getActorImage() {
        return actorImage;
    }
}
