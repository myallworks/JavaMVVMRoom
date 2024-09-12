package dev.shrishri1108.JavaRoomMVVM.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.shrishri1108.JavaRoomMVVM.Modal.Actor;
import dev.shrishri1108.JavaRoomMVVM.Modal.ActorWithStoredImage;
import dev.shrishri1108.JavaRoomMVVM.R;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {

    private final Context context;
    private List<ActorWithStoredImage> actorList;
    private final OnClickListener onItemClickListener;

    public ActorAdapter(Context context, List<ActorWithStoredImage> actorList, OnClickListener onItemClickListener) {
        this.context = context;
        this.actorList = actorList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActorViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_roe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ActorViewHolder holder, int position) {
        ActorWithStoredImage actorWithStoredImage = actorList.get(position);
        final Actor actor = actorWithStoredImage.getActor();
        holder.id.setText("Id : " + actor.getId());
        holder.name.setText("Name : " + actor.getFirstName() + " " + actor.getLastName());
        holder.email.setText("Email : " + actor.getEmail());

        Bitmap storedImg = actorWithStoredImage.getStoredImage();
        if (storedImg != null) {
            holder.image.setImageBitmap(storedImg);
        } else {
            Glide.with(context)
                    .load(actor.getAvatar())
                    .into(holder.image);
        }

        holder.btnLoads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.itemClicked(holder.getAdapterPosition(), actor.getId());
            }
        });
    }

    public void setAllActors(List<ActorWithStoredImage> actorList) {
        this.actorList = actorList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    public static class ActorViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, email;
        public ImageView image, btnLoads;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            image = itemView.findViewById(R.id.image);
            btnLoads = itemView.findViewById(R.id.btnLoad);
        }
    }
}


