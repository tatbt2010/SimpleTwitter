package com.example.davidtran.simpletwitter.Adapters;

/**
 * Created by davidtran on 7/1/17.
 */

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.davidtran.simpletwitter.Models.Tweet;
import com.example.davidtran.simpletwitter.Models.Tweet_exEntities;
import com.example.davidtran.simpletwitter.R;
import com.example.davidtran.simpletwitter.Utils.ScreenCalculator;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by davidtran on 7/1/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public TweetAdapter(List<Tweet> tweetList, Context context) {
        this.tweetList = tweetList;
        this.context = context;
    }

    List<Tweet> tweetList;
    Context context;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);






        return new tweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        tweetViewHolder viewHolder = (tweetViewHolder) holder;
        Tweet tweet = tweetList.get(position);


        Picasso.with(context)
                .load(tweet.getUser().getProfileImageUrl())
                //.transform(new RoundedCornersTransformation(3,3))
                .into(viewHolder.picProfile);
        viewHolder.tvUserFullName.setText(tweet.getUser().getName());
        viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
        viewHolder.tvTimePost.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.tvTweetContent.setText(tweet.getText());

        bindPictures(viewHolder,position,tweet);


    }
    private void bindPictures(tweetViewHolder viewHolder, int position,Tweet tweet){
        if(tweet.getExEntities()!=null) {
            if(tweet.getExEntities().getMedia()!=null) {
                Tweet_exEntities.Media media = tweet.getExEntities().getMedia().get(0);

                ViewGroup.LayoutParams layoutParams = viewHolder.tweetPicture.getLayoutParams();
                int realWidth = media.getSizes().getMedium().getW();
                int realHeight = media.getSizes().getMedium().getH();
                int width = ScreenCalculator.getScreenWidth() * 2/3;


                layoutParams.width = width;
                layoutParams.height = width * realHeight/realWidth;
                Picasso.with(context)
                        .load(tweet.getExEntities().getMedia().get(0).media_url)
                        .transform(new RoundedCornersTransformation(3,3))
                        .into(viewHolder.tweetPicture);
            }
        }
    }

    /*private String formatDateTime(String DateTime) {
        String[] strings = DateTime.split("\\s");
        return strings[1] + " " + strings[2] + " " + strings[5];
    }*/

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    static class tweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.picProfile)
        ImageView picProfile;
        @BindView(R.id.tvUserFullName)
        TextView tvUserFullName;
        @BindView(R.id.tvScreenName)
        TextView tvScreenName;
        @BindView(R.id.tvTimePost)
        TextView tvTimePost;
        @BindView(R.id.tvTweetContent)
        TextView tvTweetContent;
        @BindView(R.id.tweetPicture)
        ImageView tweetPicture;

        public tweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

