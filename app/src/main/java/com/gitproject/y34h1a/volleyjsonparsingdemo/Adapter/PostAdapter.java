package com.gitproject.y34h1a.volleyjsonparsingdemo.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gitproject.y34h1a.volleyjsonparsingdemo.Pojo.FeedItem;
import com.gitproject.y34h1a.volleyjsonparsingdemo.R;
import com.gitproject.y34h1a.volleyjsonparsingdemo.Handler.VolleySigleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yeahi on 19/01/2016.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolderPosts> {
    private ArrayList<FeedItem> arrayList = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private VolleySigleton volleySigleton;
    private ImageLoader imageLoader;
    private static Context context;

    //Consturctor
    public PostAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySigleton = VolleySigleton.getInstance();
        imageLoader = volleySigleton.getImageLoader();

    }

    public void setRecentPost(ArrayList<FeedItem> arrayList){
        this.arrayList = arrayList;
        notifyItemRangeChanged(0,arrayList.size());
        notifyDataSetChanged();
    }
    @Override
    public ViewHolderPosts onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycleview_items,parent,false);
        ViewHolderPosts holderPosts = new ViewHolderPosts(view);
        return holderPosts;
    }

    @Override
    public void onBindViewHolder(final ViewHolderPosts holder, int position) {
        FeedItem recentPostItem = arrayList.get(position);

        //setting data form FeedItem
        holder.authorName.setText(recentPostItem.getAuthor());

        String date = recentPostItem.getTimeStamp();
        date = reformatDate(date);
        holder.postTime.setText(date);


        holder.postTitle.setText(recentPostItem.getStatus());
        String profilePicUrl = recentPostItem.getProfilePic();

        //loading profile pic from url
        if(profilePicUrl!= null){
            imageLoader.get(profilePicUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.profilePic.setImageBitmap(response.getBitmap());

                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

        String thumbnail = recentPostItem.getThumnail();
        if(thumbnail!=null){
            imageLoader.get(thumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.thumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolderPosts extends RecyclerView.ViewHolder{

        private final Typeface typeface;
        private ImageView thumbnail;
        private ImageView profilePic;
        private TextView authorName;
        private TextView postTime;
        private TextView postTitle;



        public  ViewHolderPosts(View itemView){
            super(itemView);
            //set custom font in textview
            typeface = Typeface.createFromAsset(context.getAssets(), "solaimanlipi.ttf");
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbernail);
            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
            authorName = (TextView) itemView.findViewById(R.id.authorName);
            postTime = (TextView) itemView.findViewById(R.id.timestamp);
            postTitle = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            postTitle.setTypeface(typeface);

        }
    }

    String reformatDate (String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {

            date = formatter.parse(dateString);
            dateString = formatter.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }
}
