package com.example.davidtran.simpletwitter.Utils;

import com.example.davidtran.simpletwitter.Models.OwnerUser;
import com.example.davidtran.simpletwitter.Models.Tweet;
import com.example.davidtran.simpletwitter.Models.Tweet_exEntities;
import com.example.davidtran.simpletwitter.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by davidtran on 7/1/17.
 */

public class mJsonParser {
    public static User fromJsonObjectToUser(JsonObject jsonUserObject){
        User user = new User();
        if(jsonUserObject.has("id_str"))
        {
            user.setIdStr(jsonUserObject.get("id_str").getAsString());
        }
        if(jsonUserObject.has("id")){
            user.setId(jsonUserObject.get("id").getAsInt());
        }
        if(jsonUserObject.has("name")){
            user.setName(jsonUserObject.get("name").getAsString());
        }
        if(jsonUserObject.has("screen_name")){
            user.setScreenName("@"+jsonUserObject.get("screen_name").getAsString());
        }

        if(jsonUserObject.has("profile_image_url")){
            user.setProfileImageUrl(jsonUserObject.get("profile_image_url").getAsString());
        }
        return user;

    }
    public static Tweet fromJsonObjectToTweet(JsonObject jsonTweetObject){
        Tweet tweet = new Tweet();
        if (jsonTweetObject.has("created_at")){
            tweet.setCreatedAt(jsonTweetObject.get("created_at").getAsString());
        }
        if (jsonTweetObject.has("id")){
            tweet.setId(jsonTweetObject.get("id").getAsInt());
        }
        if (jsonTweetObject.has("id_str")){
            tweet.setIdStr(jsonTweetObject.get("id_str").getAsString());
        }
        if (jsonTweetObject.has("text")){
            tweet.setText(jsonTweetObject.get("text").getAsString());
        }
        if(jsonTweetObject.has("user")){
            tweet.setUser(mJsonParser.fromJsonObjectToUser(jsonTweetObject.get("user").getAsJsonObject()));
        }

        return tweet;
    }
    public static OwnerUser fromJsonObjectToOwnerUser(JsonObject jsonOwnerUserObject){
        OwnerUser ownerUser = new OwnerUser();
        if(jsonOwnerUserObject.has("name")){
            ownerUser.setName(jsonOwnerUserObject.get("name").getAsString());
        }
        if(jsonOwnerUserObject.has("screen_name")){
            ownerUser.setScreenName("@"+jsonOwnerUserObject.get("screen_name").getAsString());
        }
        if(jsonOwnerUserObject.has("profile_image_url")){
            ownerUser.setProfileImageUrl(jsonOwnerUserObject.get("profile_image_url").getAsString());
        }
        return ownerUser;
    }
    public static Tweet_exEntities fromJsonObjectToTweetExEntities(JsonObject jsonObject){
        Tweet_exEntities exEntities = new Tweet_exEntities();


        if(jsonObject.has("extended_entities")){
            Tweet_exEntities.Media media = new Tweet_exEntities.Media();
            ArrayList<Tweet_exEntities.Media> medias = new ArrayList<>();
            JsonObject exEntitiesObject = jsonObject.getAsJsonObject("extended_entities");
            if(exEntitiesObject.has("media")) {
                //JsonObject mediaObject = exEntitiesObject.getAsJsonObject("media");
                JsonArray jsonArray = exEntitiesObject.getAsJsonArray("media");
                for (int i = 0; i < jsonArray.size(); i++) {
                    media = fromJsonObjectToMedia(jsonArray.get(i).getAsJsonObject());
                    medias.add(media);
                }
            }
            exEntities.setMedia(medias);
        }

        return exEntities;
    }
    private static Tweet_exEntities.Media fromJsonObjectToMedia(JsonObject jsonObject){
        Tweet_exEntities.Media media = new Tweet_exEntities.Media();
        if(jsonObject.has("id_str")) {
            media.setId_str(jsonObject.get("id_str").getAsString());
        }
        if(jsonObject.has("media_url")) {
            media.setMedia_url(jsonObject.get("media_url").getAsString());
        }
        if(jsonObject.has("sizes")) {
            JsonObject jsonSize = jsonObject.getAsJsonObject("sizes");

            if(jsonSize.has("medium")){
                JsonObject jsonSizeMedium = jsonSize.getAsJsonObject("medium");
                int w = jsonSizeMedium.get("w").getAsInt();
                int h = jsonSizeMedium.get("h").getAsInt();
                Tweet_exEntities.Medium medium = new Tweet_exEntities.Medium();
                medium.setH(h);
                medium.setW(w);
                Tweet_exEntities.Sizes sizes = new Tweet_exEntities.Sizes();
                sizes.setMedium(medium);
                media.setSizes(sizes);

            }
            media.setMedia_url(jsonObject.get("media_url").getAsString());
        }
        return media;
    }
}
