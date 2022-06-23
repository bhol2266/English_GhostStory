package com.bhola.english_ghoststory;

import android.animation.Animator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "TAGA";
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView textView;
    MediaPlayer ring;
    LottieAnimationView lottie;

    public static String Ad_Network_Name = "admob";
    public static String Notification_Intent_Firebase = "inactive";
    public static String Main_App_url1 = "https://play.google.com/store/apps/details?id=com.bhola.english_ghoststory&hl=en&gl=US";
    public static String Refer_App_url2 = "https://play.google.com/store/apps/developer?id=UK+DEVELOPERS";
    public static String Ads_State = "inactive";
    public static String updatingApp_on_Playstore = "active";  // active because  if there is network interruptions and value will be not updated. we will not show the stories
    public static String exit_Refer_appNavigation = "inactive";
    DatabaseReference url_mref;
    public static String Android_ID;
    //   This Should be same as the version From Database
    public static int DB_VERSION_INSIDE_TABLE = 2;
    public static int DB_VERSION = 1;
    public static String DB_NAME = "English_GhostStory";
    public static int Login_Times = 0;
    DatabaseHelper databaseHelper;
    com.facebook.ads.InterstitialAd facebook_IntertitialAds;
    RewardedInterstitialAd mRewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        copyDatabase();
        allUrl();

        sharedPrefrences();
        generateFCMToken();

        // Add date to SQL database
//        List<String> filesname = new ArrayList<>();
//        try {
//            filesname = getFileNames(SplashScreen.this, "story3");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < filesname.size(); i++) {
//            readJSON(filesname.get(i), Integer.toString(i + 1));
//        }

        int[] clips = {R.raw.nmh_scream1, R.raw.ghost02, R.raw.laughhowl1, R.raw.scary, R.raw.wickedwitchlaugh};

        Random rand = new Random();
        int rand_int1 = rand.nextInt(clips.length);
        ring = MediaPlayer.create(getApplicationContext(), clips[rand_int1]);
        ring.start();


        textView = findViewById(R.id.textView_splashscreen);
        lottie = findViewById(R.id.lottie);

        textView.setAnimation(bottomAnim);
        lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LinearLayout progressbar = findViewById(R.id.progressbar);
                progressbar.setVisibility(View.VISIBLE);
                handler_forIntent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        generateNotification();


    }


    private void copyDatabase() {
        databaseHelper = new DatabaseHelper(this, DB_NAME, DB_VERSION, "UserInformation");
        try {
            databaseHelper.CheckDatabases();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sharedPrefrences() {

        //Reading Login Times
        SharedPreferences sh = getSharedPreferences("UserInfo", MODE_PRIVATE);
        int a = sh.getInt("loginTimes", 0);
        Login_Times = a + 1;

        // Updating Login Times data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("loginTimes", a + 1);
        myEdit.commit();

    }

    private void allUrl() {


        url_mref = FirebaseDatabase.getInstance().getReference().child("English_GhostStory");
        url_mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                exit_Refer_appNavigation = (String) snapshot.child("switch_Exit_Nav").getValue().toString().trim();
                Ads_State = snapshot.child("Ads").getValue().toString().trim();
                Refer_App_url2 = snapshot.child("Refer_App_url2").getValue().toString().trim();
                Ad_Network_Name = snapshot.child("Ad_Network").getValue().toString().trim();
                updatingApp_on_Playstore = snapshot.child("updatingApp_on_Playstore").getValue().toString().trim();

                if (Login_Times > 10) {
                    updatingApp_on_Playstore = "inactive";
                }
                handler_forIntent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }


        });

    }


    private void generateNotification() {


        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Sucessfull msg";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void generateFCMToken() {

        if (getIntent() != null && getIntent().hasExtra("KEY1")) {
            if (getIntent().getExtras().getString("KEY1").equals("Notification_Story")) {
                Notification_Intent_Firebase = "active";

            }

        }

//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if(task.isSuccessful()){
//                    String token=task.getResult().getToken();
//                    Log.d("TAGA", "Token:  "+token);
//                }
//                else
//                    Log.d("TAGA", "Exception: "+task.getException());
//
//            }
//        });

    }

    private void showAds() {

        if (SplashScreen.Ad_Network_Name.equals("admob")) {
            ADS_ADMOB rewarded_ads = new ADS_ADMOB(mRewardedVideoAd, this, getString(R.string.Rewarded_ADS_Unit_ID));
            rewarded_ads.RewardedVideoAds();
        } else {
            ADS_FACEBOOK.interstitialAd(this, facebook_IntertitialAds, getString(R.string.Facebbok_InterstitialAdUnit));
        }
    }


    private String encryption(String text) {

        int key = 5;
        char[] chars = text.toCharArray();
        String encryptedText = "";
        String decryptedText = "";

        //Encryption
        for (char c : chars) {
            c += key;
            encryptedText = encryptedText + c;
        }

        //Decryption
        char[] chars2 = encryptedText.toCharArray();
        for (char c : chars2) {
            c -= key;
            decryptedText = decryptedText + c;
        }
        return encryptedText;
    }

    private void readJSON(String filename, String season) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(filename));

            ArrayList<String> coverImage = new ArrayList<String>();
            ArrayList<String> collectionName = new ArrayList<String>();
            ArrayList<String> description = new ArrayList<String>();

            coverImage.add(obj.getString("coverImage"));
            collectionName.add(obj.getString("collectionName"));
            description.add(obj.getString("description"));

            for (int i = 0; i < coverImage.size(); i++) {
                DatabaseHelper insertRecord = new DatabaseHelper(getApplicationContext(), SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "UserInformation");
                String res = insertRecord.addAudioStoriesLinks(coverImage.get(i), collectionName.get(i), season, description.get(i), "Audio_Story_3", "Audio_Story_Category");
                Log.d(TAG, "INSERT DATA: " + res);
            }

        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("story3/" + filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private List<String> getFileNames(Context context, String folderName) throws IOException {
        AssetManager assetManager = context.getAssets();
        String[] files = assetManager.list(folderName);
        return Arrays.asList(files);
    }

    boolean isInternetAvailable(Context context) {
        if (context == null) return false;


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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

    private void handler_forIntent() {
        showAds();
        Intent intent = new Intent(getApplicationContext(), Collection_GridView.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ring.stop();
    }

}


