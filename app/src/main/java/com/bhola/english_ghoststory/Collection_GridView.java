package com.bhola.english_ghoststory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import com.google.android.material.navigation.NavigationView;

public class Collection_GridView extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    AlertDialog dialog;
    private AdView mAdView, mAdView2;
    MediaPlayer ring;
    String Android_ID;
    com.facebook.ads.InterstitialAd facebook_IntertitialAds;
    com.facebook.ads.AdView facebook_adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection__grid_view);

        StatusBarTransparent();

        navigationDrawer();
//        backgroundMusic();
        actionBar();


        try {
            checkLoginTimes_Databases();
            if (SplashScreen.Ads_State.equals("active")) {
                showAds();
            }
        } catch (Exception ignored) {
        }

    }

    private void checkLoginTimes_Databases() {

        Cursor cursor1 = new DatabaseHelper(this, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "DB_VERSION").readalldata();
        while (cursor1.moveToNext()) {
            int DB_VERSION_FROM_DATABASE = cursor1.getInt(1);

            if (DB_VERSION_FROM_DATABASE != SplashScreen.DB_VERSION) {
                DatabaseHelper databaseHelper2 = new DatabaseHelper(this, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "DB_VERSION");
                databaseHelper2.db_delete();
            }

        }
        cursor1.close();
        String res = new DatabaseHelper(this, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "UserInformation").updaterecord_Login_Times(1, SplashScreen.Login_Times + 1);

    }

    private void showAds() {

        if (SplashScreen.Ad_Network_Name.equals("admob")) {

            mAdView = findViewById(R.id.adView);
            ADS_ADMOB.BannerAd(this, mAdView);


        } else {


            LinearLayout facebook_bannerAd_layput;
            facebook_bannerAd_layput = findViewById(R.id.banner_container);
            ADS_FACEBOOK.bannerAds(this, facebook_adView, facebook_bannerAd_layput, getString(R.string.Facebbok_BannerAdUnit_1));


        }
    }


    private void backgroundMusic() {
        ring = MediaPlayer.create(getApplicationContext(), R.raw.swamp_background_web);
        ring.setLooping(true);
        ring.start();
    }

    @Override
    public void onBackPressed() {


        exit_dialog();
    }

    private void exit_dialog() {

        TextView appLink;
        Button RateUs, exit;
        LinearLayout ratingLinearLayout;

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(nav.getContext());
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View promptView = inflater.inflate(R.layout.exit_dialog, null);
        builder.setView(promptView);
        builder.setCancelable(true);

        GiveRating(promptView);


        try {
            if ((SplashScreen.Ads_State.equals("active") && SplashScreen.Ad_Network_Name.equals("admob"))) {
                AdView mAdView2;
                mAdView2 = promptView.findViewById(R.id.adView2);
                ADS_ADMOB.BannerAd(this, mAdView2);
            } else if ((SplashScreen.Ads_State.equals("active") && SplashScreen.Ad_Network_Name.equals("facebook"))) {
                LinearLayout facebook_bannerAd_layput = promptView.findViewById(R.id.banner_container);
                ADS_FACEBOOK.bannerAds(this, facebook_adView, facebook_bannerAd_layput, getString(R.string.Facebbok_BannerAdUnit_1));
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }




        RateUs = promptView.findViewById(R.id.exit_button1);
        RateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Intent.ACTION_VIEW);
                k.setData(Uri.parse(SplashScreen.Main_App_url1));
                startActivity(k);
            }
        });

        exit = promptView.findViewById(R.id.exit_button2);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SplashScreen.exit_Refer_appNavigation.equals("active") && SplashScreen.Login_Times < 2 && SplashScreen.Refer_App_url2.length() > 10) {
                    String res = new DatabaseHelper(Collection_GridView.this, SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "UserInformation").updaterecord_Login_Times(1, SplashScreen.Login_Times + 1);
                    Intent j = new Intent(Intent.ACTION_VIEW);
                    j.setData(Uri.parse(SplashScreen.Refer_App_url2));
                    startActivity(j);
                    finish();
                } else {

                    finishAffinity();
                    System.exit(0);

                }

            }
        });

        ratingLinearLayout = promptView.findViewById(R.id.rating);
        ratingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Intent.ACTION_VIEW);
                k.setData(Uri.parse(SplashScreen.Main_App_url1));
                startActivity(k);
            }
        });


        dialog = builder.create();
        dialog.show();
    }


    public void collection1(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-1";
        String DB_TABLENAME = "collection1";
        intent.putExtra("bhola", title);
        intent.putExtra("tableNo", "collection1");
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        startActivity(intent);
    }

    public void collection2(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-2";
        String DB_TABLENAME = "collection2";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection2");
        startActivity(intent);
    }

    public void collection3(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-3";
        String DB_TABLENAME = "collection3";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection3");
        startActivity(intent);
    }

    public void collection4(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-4";
        String DB_TABLENAME = "collection4";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection4");
        startActivity(intent);
    }

    public void collection5(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-5";
        String DB_TABLENAME = "collection5";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection5");
        startActivity(intent);
    }

    public void collection6(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-6";
        String DB_TABLENAME = "collection6";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection6");
        startActivity(intent);
    }

    public void collection7(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-7";
        String DB_TABLENAME = "collection7";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection7");
        startActivity(intent);
    }

    public void collection8(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-8";
        String DB_TABLENAME = "collection8";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection8");
        startActivity(intent);
    }

    public void collection9(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-9";
        String DB_TABLENAME = "collection9";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection9");
        startActivity(intent);
    }


    public void collection10(View view) {
        Intent intent = new Intent(this, Collection_detail.class);
        String title = "Collection-10";
        String DB_TABLENAME = "collection10";
        intent.putExtra("bhola", title);
        intent.putExtra("DB_TABLENAME", DB_TABLENAME);
        intent.putExtra("tableNo", "collection10");
        startActivity(intent);
    }


    private void actionBar() {
        ImageView share_ap, share_icon;


//        share_ap = findViewById(R.id.share_app);
//        share_ap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (SplashScreen.Refer_App_url2.length() > 10) {
//                    Intent j = new Intent(Intent.ACTION_VIEW);
//                    j.setData(Uri.parse(SplashScreen.Refer_App_url2));
//                    startActivity(j);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//
//                }
//            }
//        });


    }

    private void navigationDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav = (NavigationView) findViewById(R.id.navmenu);
        nav.setItemIconTintList(null);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.DefaultTextColor));
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_downloads:
                        startActivity(new Intent(getApplicationContext(), Download.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_contacts:


                        TextView email;
                        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Collection_GridView.this);
                        LayoutInflater inflater = LayoutInflater.from(Collection_GridView.this);
                        View promptView = inflater.inflate(R.layout.navigation_menu_contacts, null);
                        builder.setView(promptView);
                        builder.setCancelable(true);

                        email = promptView.findViewById(R.id.email);
                        email.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("label", "bhola2266@gmail.com");
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(v.getContext(), "COPIED EMAIL", Toast.LENGTH_SHORT).show();
                            }
                        });


                        dialog = builder.create();
                        dialog.show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.menu_rating:

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(SplashScreen.Main_App_url1));
                        startActivity(i);
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
//                    case R.id.menu_notificaton:
//                        if (SplashScreen.Refer_App_url2.length() > 10) {
//                            Intent j = new Intent(Intent.ACTION_VIEW);
//                            j.setData(Uri.parse(SplashScreen.Refer_App_url2));
//                            startActivity(j);
//                            drawerLayout.closeDrawer(GravityCompat.START);
//                            break;
//                        }


                    case R.id.menu_share_app:
                        String share_msg = "Hi I have downloaded Hindi Ghost Story App.\n" +
                                "It is a best app for Real Desi Ghost Story.\n" +
                                "You should also try\n" +
                                SplashScreen.Main_App_url1;
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, share_msg);
                        intent.setType("text/plain");
                        intent = Intent.createChooser(intent, "Share By");
                        startActivity(intent);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_shayari_app:

                        if (SplashScreen.Refer_App_url2.length() > 10 && SplashScreen.exit_Refer_appNavigation.equals("active")) {
                            Intent j = new Intent(Intent.ACTION_VIEW);
                            j.setData(Uri.parse(SplashScreen.Refer_App_url2));
                            startActivity(j);
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;

                    case R.id.Privacy_Policy:

                        Intent i5 = new Intent(Intent.ACTION_VIEW);
                        i5.setData(Uri.parse("https://sites.google.com/view/englishghoststory/home"));
                        startActivity(i5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.About_Us:

                        final androidx.appcompat.app.AlertDialog.Builder builder2 = new androidx.appcompat.app.AlertDialog.Builder(nav.getContext());
                        LayoutInflater inflater2 = LayoutInflater.from(getApplicationContext());
                        View promptView2 = inflater2.inflate(R.layout.about_us, null);
                        builder2.setView(promptView2);
                        builder2.setCancelable(true);


                        dialog = builder2.create();
                        dialog.show();

                        break;


                    case R.id.Terms_and_Condition:
                        Intent intent27 = new Intent(getApplicationContext(), TermsAndConditions.class);
                        startActivity(intent27);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });
    }


    private void GiveRating(View view) {

        ImageView rating1, rating2, rating3, rating4, rating5;

        rating1 = view.findViewById(R.id.rating1);
        rating2 = view.findViewById(R.id.rating2);
        rating3 = view.findViewById(R.id.rating3);
        rating4 = view.findViewById(R.id.rating4);
        rating5 = view.findViewById(R.id.rating5);


        rating1.setVisibility(View.GONE);
        rating2.setVisibility(View.GONE);
        rating3.setVisibility(View.GONE);
        rating4.setVisibility(View.GONE);
        rating5.setVisibility(View.GONE);


        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     rating1.setVisibility(View.VISIBLE);

                                 }
                             }, 200
        );


        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                rating2.setVisibility(View.VISIBLE);
            }
        }, 400);

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                rating3.setVisibility(View.VISIBLE);
            }
        }, 600);

        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                rating4.setVisibility(View.VISIBLE);
            }
        }, 750);

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                rating5.setVisibility(View.VISIBLE);
            }
        }, 900);
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
        Window win = Collection_GridView.this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}