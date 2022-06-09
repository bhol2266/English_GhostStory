package com.bhola.english_ghoststory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Collection_detail extends AppCompatActivity {
    String TAG = "taga";
    List<RowData> COllectionData;
    List<FirebaseData> collectonData;
    DatabaseReference mref;
    AlertDialog dialog;
    int counter = admin_panel.counter;
    String ActionBar_titleView, Database_tableNo, Collection_number;

    ProgrammingAdapter adapter;
    Context context;
    ImageView back, share_ap;
    private AdView mAdView;

    RecyclerView recyclerView;

    com.facebook.ads.InterstitialAd facebook_IntertitialAds;
    com.facebook.ads.AdView facebook_adView;
    int counterr=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);
        StatusBarTransparent();

        try {
            if (SplashScreen.Ads_State.equals("active")) {
                showAds(SplashScreen.Ad_Network_Name, this);

            }
        } catch (Exception e) {

        }

        actionBar();


        COllectionData = new ArrayList<RowData>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





            getDataFromDatabase();



    }

    private void showAds(String Ad_Network_Name, Context mContext) {


        if (Ad_Network_Name.equals("admob")) {
//NO BANNER AD IN STORY LIST

            mAdView = findViewById(R.id.adView);
            ADS_ADMOB.BannerAd(this, mAdView);
        } else {

            LinearLayout facebook_bannerAd_layput;
            facebook_bannerAd_layput = findViewById(R.id.banner_container);
            ADS_FACEBOOK.bannerAds(mContext, facebook_adView, facebook_bannerAd_layput, getString(R.string.Facebbok_BannerAdUnit_1));

        }


    }


    private void getDataFromDatabase() {

        mref = FirebaseDatabase.getInstance().getReference();
        mref.keepSynced(true);
        Log.d("AAAA", "getDataFromDatabase: " + Database_tableNo);

        Cursor cursor = new DatabaseHelper(this, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, Database_tableNo).readalldata();

        try {
            while (cursor.moveToNext()) {
                RowData rowData = new RowData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                COllectionData.add(rowData);

            }
        } finally {
            cursor.close();

        }


        Collections.shuffle(COllectionData);
        adapter = new ProgrammingAdapter(COllectionData, context, ActionBar_titleView, Database_tableNo);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void actionBar() {
        TextView title;
        Database_tableNo = getIntent().getStringExtra("DB_TABLENAME");

        Collection_number = getIntent().getStringExtra("tableNo");

        title = findViewById(R.id.title_collection);
        ActionBar_titleView = getIntent().getStringExtra("bhola");
        title.setText(ActionBar_titleView);
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditText passwordEdittext;
                Button passwordLoginBtn;


                AlertDialog dialog;

                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
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
        Window win = Collection_detail.this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}


