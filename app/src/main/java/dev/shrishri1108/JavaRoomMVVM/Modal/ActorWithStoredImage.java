package dev.shrishri1108.JavaRoomMVVM.Modal;

import android.graphics.Bitmap;

public class ActorWithStoredImage {
    private Actor actor;
    private Bitmap storedImage;

    public ActorWithStoredImage(Bitmap storedImage, Actor actor) {
        this.storedImage = storedImage;
        this.actor = actor;
    }

    public Bitmap getStoredImage() {
        return storedImage;
    }

    public void setStoredImage(Bitmap storedImage) {
        this.storedImage = storedImage;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
