package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;


import java.util.ArrayList;
import java.util.List;

public class Download extends AppCompatActivity {

Context context;
   String android_id,TAG="TAGA";
    List<ModelData_forFavourites> collectonData;
    ImageView back;
    private AdView mAdView;
    public static ProgrammingAdapter3 adapter2;
    DatabaseHelper2 databaseHelper2;
    RecyclerView recyclerView;
    TextView emptyView;

    com.facebook.ads.InterstitialAd facebook_IntertitialAds;
    com.facebook.ads.AdView facebook_adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
        StatusBarTransparent();

        try{
            if(SplashScreen.Ads_State.equals("active"))
            {
                showAds(SplashScreen.Ad_Network_Name,this);

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        actionBar();

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);


        emptyView = findViewById(R.id.emptyView);
         recyclerView = findViewById(R.id.recyclerView_Downloads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        collectonData=new ArrayList<ModelData_forFavourites>();


        String[] Table_Names={"collection1","collection2","collection3","collection4","collection5","collection6","collection7","collection8","collection9","collection10"};

        for(int i=0;i<Table_Names.length;i++)
        { getDataFromDatabase(SplashScreen.DB_NAME,Table_Names[i]);}

        checkCollectionDataEmpty();

        adapter2 = new ProgrammingAdapter3(collectonData,context);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

    }

    private void showAds(String Ad_Network_Name, Context mContext) {


        if(Ad_Network_Name.equals("admob")){
            mAdView = findViewById(R.id.adView);
            ADS_ADMOB.BannerAd(this, mAdView);
        }

        else{
            LinearLayout facebook_bannerAd_layput;
            facebook_bannerAd_layput = findViewById(R.id.banner_container);
            ADS_FACEBOOK.bannerAds(mContext, facebook_adView, facebook_bannerAd_layput, getString(R.string.Facebbok_BannerAdUnit_1));

        }


    }


    private void getDataFromDatabase(String DatabaseName, String Table_Name) {




        Cursor cursor = new DatabaseHelper2(getApplicationContext(), SplashScreen.DB_NAME, SplashScreen.DB_VERSION, Table_Name).readalldata();


        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String Title = cursor.getString(1);
            String Story = cursor.getString(2);
            int Liked = cursor.getInt(3);

            if (Liked == 1) {
                ModelData_forFavourites modelData_forFavourites = new ModelData_forFavourites(_id, Title, Story, Liked);
                collectonData.add(modelData_forFavourites);
            }
        }
        if (cursor != null) {
            Log.d("TAGA", " databaseHelper.db_delete(): ");
            cursor.close();
        }
    }


    private void checkCollectionDataEmpty() {
        if (collectonData.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

        }
    }


    private void actionBar() {
        back=findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Collection_GridView.class));
              finish();
            }
        });

    }



    private void StatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void setWindowFlag(Context activity, final int bits, boolean on) {
        Window win = Download.this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



}