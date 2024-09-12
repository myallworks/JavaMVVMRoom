package dev.shrishri1108.JavaRoomMVVM.Repository;


import java.util.List;

import dev.shrishri1108.JavaRoomMVVM.Modal.ActorWithStoredImage;

public interface OnListUpdated {
    void listUpdated(List<ActorWithStoredImage> actorWithStoredImageList);
}
