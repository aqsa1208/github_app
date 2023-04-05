package com.example.github_230325;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.InputStream;

public class RepositoryAdapter extends ArrayAdapter<RepositoryData> {

    public RepositoryAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Context context = parent.getContext();

        if (view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.repo_item, parent, false);
        }

        ImageView ownerAvatar = view.findViewById(R.id.ownerAvatar);
        TextView repoName = view.findViewById(R.id.repoName);
        TextView repoDesc = view.findViewById(R.id.repoDesc);
        TextView repoOwner = view.findViewById(R.id.repoOwner);
        TextView repoStars = view.findViewById(R.id.repoStars);

        RepositoryData currentRepositoryData = getItem(position);

        String ownerAvatar_url = currentRepositoryData.getOwnerAvatar_url();
        String repoUrl = currentRepositoryData.getrepoUrl();
        repoName.setText(currentRepositoryData.getRepoName());
        repoDesc.setText(currentRepositoryData.getRepoDesc());
        repoOwner.setText(currentRepositoryData.getRepoOwner());
        repoStars.setText(currentRepositoryData.getRepoStars());

        Glide.with(context)
                .load(ownerAvatar_url)
                .into(ownerAvatar);


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        view.startAnimation(animation);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl));
                context.startActivity(intent);
            }
        });




        return view;
    }
}
