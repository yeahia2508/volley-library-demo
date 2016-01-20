package com.gitproject.y34h1a.volleyjsonparsingdemo.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.gitproject.y34h1a.volleyjsonparsingdemo.Adapter.PostAdapter;
import com.gitproject.y34h1a.volleyjsonparsingdemo.Handler.RecyclerTouchListener;
import com.gitproject.y34h1a.volleyjsonparsingdemo.Pojo.FeedItem;
import com.gitproject.y34h1a.volleyjsonparsingdemo.R;
import com.gitproject.y34h1a.volleyjsonparsingdemo.Handler.VolleySigleton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    //Arraylist and Recycle View veriable
    private ArrayList<FeedItem> listRecentPost = new ArrayList<>();
    private RecyclerView postRecycleView;
    private PostAdapter postAdapter;

    //Volley Needed Veriable
    private VolleySigleton volleySigleton;
    private RequestQueue requestQueue;
    private ImageView errorImage;
    private TextView errorMsg;

    // JSON NEEDED VERIABLE
    private String postID;
    private String author;
    private String postTitle;
    private String time;
    private String profilePic;
    private String thumbnil;
    private String postDetail;

    // CALLBACK URL
    private String baseUrl = "http://www.androidlime.com/wp-json/posts";
    private Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpFab();
        instantiateVolley();

        //error showing view
        errorImage = (ImageView) findViewById(R.id.imgNetworkError);
        errorMsg = (TextView) findViewById(R.id.tvNetworkError);

        //swipe refresh
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshPosts);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this);

        //setting up recycleview
        postRecycleView = (RecyclerView) findViewById(R.id.recent_posts);
        postRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        postAdapter = new PostAdapter(getApplicationContext());
        postRecycleView.setAdapter(postAdapter);

        //this click event is created by custom way. see RecycleTouchLister class to learn how to implement.
        postRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), postRecycleView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"Clicked Position: " + position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"Long Pressed Position: " + position,Toast.LENGTH_SHORT).show();
            }
        }));

        //load data
        swapeRefresh();
    }

    public void swapeRefresh() {
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        /*
                                        *  swipeRefreshLayout.setRefreshing(true), means  now refresh icon will start moving. watch onRefresh method to understand
                                        * how data is refreshing.
                                        * */
                                        swipeRefreshLayout.setRefreshing(true);

                                        listRecentPost.clear(); // previous data list  clear
                                        fetchPosts(); // new data load
                                    }
                                }
        );
    }

    //FETECH POSTS
    private void fetchPosts(){
        sendJsonRequest();

    }

    private void sendJsonRequest(){

        JsonArrayRequest request;

        //sending json request
        request = new JsonArrayRequest(Request.Method.GET,
                baseUrl,
                (String)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        listRecentPost = parseJsonResponse(response);


                        // If any data is avialable
                        if(!listRecentPost.isEmpty()){


                            postAdapter.setRecentPost(listRecentPost);
                            postAdapter.notifyDataSetChanged();

                            /*
                              suppose data connection is off so error image and text will show
                            * but when my connection will be okk then i need to disable this image and error text
                            * */
                            errorImage.setVisibility(View.GONE);
                            errorMsg.setVisibility(View.GONE);
                        }

                        // If no data is collected
                        else {

                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText("No Post Available");
                        }

                        //disable loading icon
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // enable error iamge and text
                errorImage.setVisibility(View.VISIBLE);
                errorMsg.setVisibility(View.VISIBLE);

                if(error instanceof TimeoutError || error instanceof NoConnectionError){
                    errorMsg.setText(R.string.network_error);

                }else if(error instanceof AuthFailureError){
                    errorMsg.setText("Authentication Failure");

                }else if(error instanceof ServerError){
                    errorMsg.setText(R.string.server_error);

                }else if(error instanceof NetworkError){
                    errorMsg.setText("Network Error");

                }else if(error instanceof ParseError){
                    errorMsg.setText(R.string.network_error);
                }

                //disable loading icon
                swipeRefreshLayout.setRefreshing(false);

                //again try to load data after 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swapeRefresh();
                    }
                }, 2000);
            }

        });

        //queueing request
        requestQueue.add(request);
    }


    // PARSHING ALL DATA FROM JASON FILE
    private ArrayList<FeedItem> parseJsonResponse(JSONArray response) {
        ArrayList<FeedItem> arrayList = new ArrayList<>();

        if(response != null || response.length() > 0) {
            try {
                for (int i = 0; i < response.length(); i++) {

                    JSONObject rootJsonObj = response.getJSONObject(i);
                    //post Id parse
                    postID = ""+rootJsonObj.getInt("ID");
                    //Post Title name Parse
                    postTitle = rootJsonObj.getString("title");

                    //Author parse
                    JSONObject authorObj = rootJsonObj.getJSONObject("author");
                    author = authorObj.getString("name");


                    //profile Pic parse
                    profilePic = authorObj.getString("avatar");

                    //time parse
                    time = rootJsonObj.getString("modified_gmt");


                    //Thumbnail Parse
                    JSONObject featured_imageObj = rootJsonObj.getJSONObject("featured_image");
                    thumbnil = featured_imageObj.getString("guid");


                    //Adding Data to FeedItem Class and ArrayList
                    FeedItem feedItem = new FeedItem();
                    feedItem.setId(postID);
                    feedItem.setAuthor(author);
                    feedItem.setStatus(postTitle);
                    feedItem.setProfilePic(profilePic);
                    feedItem.setTimeStamp(time);
                    feedItem.setThumnail(thumbnil);

                    //Add to ArryList
                    arrayList.add(feedItem);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return arrayList;

    }




    private void instantiateVolley() {
        volleySigleton = VolleySigleton.getInstance();
        requestQueue = volleySigleton.getRequestQueue();
    }

    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }


    @Override
    public void onRefresh() {
        postAdapter.notifyDataSetChanged();
        listRecentPost.clear();
        fetchPosts();
    }

    private void sendMail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "yeahia.arif@gmail.com");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
