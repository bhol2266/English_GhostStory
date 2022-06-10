package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class Collection_detail_Audio extends AppCompatActivity {
    String TAG = "taga";
    List<AudioCategoryModel> collectionData;

    int counter = admin_panel.counter;
    String ActionBar_titleView, Database_tableNo, Collection_number;

    ProgrammingAdapter adapter;
    Context context;
    ImageView back, share_ap;
    private AdView mAdView;


    com.facebook.ads.InterstitialAd facebook_IntertitialAds;
    com.facebook.ads.AdView facebook_adView;

    String category;
    AudioStory_Details_Adapter adapter2;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);
        StatusBarTransparent();

        if (SplashScreen.Ads_State.equals("active")) {
            showAds();
        }

        actionBar();


        collectionData = new ArrayList<AudioCategoryModel>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadAudioDatabase();


    }

    private void showAds() {


        if (SplashScreen.Ad_Network_Name.equals("admob")) {
//NO BANNER AD IN STORY LIST

            mAdView = findViewById(R.id.adView);
            ADS_ADMOB.BannerAd(this, mAdView);
        } else {

            LinearLayout facebook_bannerAd_layput;
            facebook_bannerAd_layput = findViewById(R.id.banner_container);
            ADS_FACEBOOK.bannerAds(this, facebook_adView, facebook_bannerAd_layput, getString(R.string.Facebbok_BannerAdUnit_1));

        }


    }


    private void loadAudioDatabase() {
        List<String> name_array = new ArrayList<String>();
        List<String> date_array = new ArrayList<String>();
        List<String> link_array = new ArrayList<String>();


        Cursor cursor2 = new DatabaseHelper(this, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, getIntent().getStringExtra("ref")).readalldata();
        while (cursor2.moveToNext()) {
            if(cursor2.getString(2).equals(getIntent().getStringExtra("season"))){
            name_array.add(cursor2.getString(1));
            date_array.add(cursor2.getString(3));
            link_array.add(cursor2.getString(4));
            }
        }
        ArrayList<Object> collectionData = new ArrayList<Object>();
        for (int i = 0; i < name_array.size(); i++) {
            AudioModel model = new AudioModel(name_array.get(i), "13:45", date_array.get(i), link_array.get(i));
            collectionData.add(model);
        }
        adapter2 = new AudioStory_Details_Adapter(collectionData, this);
        recyclerView.setAdapter(adapter2);
    }


    private void actionBar() {
        TextView title = findViewById(R.id.title_collection);
        title.setText(getIntent().getStringExtra("Title"));
        title.setTextSize(24);
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditText passwordEdittext;
                Button passwordLoginBtn;


                AlertDialog dialog;

                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View promptView = inflater.inflate(R.layout.admin_panel_entry, null);
                builder.setView(promptView);
                builder.setCancelable(true);


                passwordEdittext = promptView.findViewById(R.id.passwordEdittext);
                passwordLoginBtn = promptView.findViewById(R.id.passwordLoginBtn);

                passwordLoginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (passwordEdittext.getText().toString().equals("Ssss5555")) {
                            startActivity(new Intent(getApplicationContext(), admin_panel.class));

                        } else {
                            Toast.makeText(v.getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                dialog = builder.create();
                dialog.show();
                return false;
            }
        });
        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Collection_GridView.class));
            }
        });
        share_ap = findViewById(R.id.share_app);
        share_ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Download.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Collection_GridView.class));
    }


    private void StatusBarTransparent() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public void setWindowFlag(Context activity, final int bits, boolean on) {
        Window win = Collection_detail_Audio.this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}


class AudioStory_Details_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    RewardedInterstitialAd mRewardedVideoAd;
    com.facebook.ads.InterstitialAd facebook_IntertitialAds;
    ArrayList<Object> collectionData = new ArrayList<Object>();


    public AudioStory_Details_Adapter(ArrayList<Object> data, FragmentActivity activity) {
        this.context = activity;
        this.collectionData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View Story_ROW_viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);
        return new Story_ROW_viewHolder(Story_ROW_viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int POSITION = position;

        AudioModel model = (AudioModel) collectionData.get(position);
        ((Story_ROW_viewHolder) holder).title.setText(model.getName());
        ((Story_ROW_viewHolder) holder).title.setTextSize(18);
        ((Story_ROW_viewHolder) holder).imageview.setImageResource(R.drawable.folder);
        ((Story_ROW_viewHolder) holder).date.setText("Added on : "+model.getDate().replace("Date Added:",""));
      
        ((Story_ROW_viewHolder) holder).recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetAvailable()) {
                    Intent intent = new Intent(context, AudioPlayer.class);
                    intent.putExtra("storyURL", model.getLink());
                    intent.putExtra("storyName", model.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(context, "Check Internet Connection!" + System.lineSeparator() +
                            "इंटरनेट कनेक्शन चेक करे", Toast.LENGTH_SHORT).show();
                }

            }

            boolean isInternetAvailable() {
                if (context == null) return false;


                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager != null) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                        if (capabilities != null) {
                            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                                return true;
                            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                                return true;
                            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                                return true;
                            }
                        }
                    } else {

                        try {
                            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                                Log.i("update_statut", "Network is available : true");
                                return true;
                            }
                        } catch (Exception e) {
                            Log.i("update_statut", "" + e.getMessage());
                        }
                    }
                }
                Log.i("update_statut", "Network is available : FALSE ");
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return collectionData.size();
    }


    public class Story_ROW_viewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView  date;

        ImageView imageview;
        LinearLayout recyclerview;


        public Story_ROW_viewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerview = itemView.findViewById(R.id.recyclerviewLayout);
            imageview = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.titlee);
            date = itemView.findViewById(R.id.date_recyclerview);

        }
    }
}




