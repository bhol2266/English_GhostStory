package com.bhola.english_ghoststory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notification_Story extends AppCompatActivity {
    String TAG="taga";
    List<RowData> collectonData;
    DatabaseReference mref;
    AlertDialog dialog;
    int counter=admin_panel.counter;
    String title1;
    Context context;
    ImageView back,share_ap;
    private AdView mAdView;
    ProgrammingAdapter adapter;
    String ActionBar_titleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        try{
            if(SplashScreen.Ads_State.equals("active"))
            {

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        actionBar();




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
collectonData=new ArrayList<RowData>();
        mref = FirebaseDatabase.getInstance().getReference().child("Notification");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    RowData rowData = ds.getValue(RowData.class);
                    collectonData.add(rowData);
                }

                adapter = new ProgrammingAdapter(collectonData,context,ActionBar_titleView, "Database_tableNo");
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    private boolean checkConnectivity() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {


            return true;

            /* New Handler to start the Menu-Activity
             * and close this Splash-Screen after some seconds.*/

        } else {

            return false;


        }

    }

    void connectivityDialog()
    {
        final androidx.appcompat.app.AlertDialog.Builder builder2=new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        builder2.setView(inflater.inflate(R.layout.intertnconnectivitycheck,null));
        builder2.setCancelable(false);
        builder2.setMessage("No Internet Connection");
        builder2.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                admin_panel.counter=0;
                startActivity(new Intent(getApplicationContext(), Collection_GridView.class));

            }
        });
        builder2.setNegativeButton("Try Again!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                admin_panel.counter=0;
                Intent intent=new Intent(getApplicationContext(),Collection_detail.class);
                String text=title1;
                intent.putExtra("bhola",text);
                startActivity(intent);


            }
        });
        dialog=builder2.create();
        dialog.show();


    }


    void loadAlertdialog()
    {

        final androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progressbar_dialog,null));
        builder.setCancelable(false);

        dialog=builder.create();
        dialog.show();
    }
    void dismissDialog()
    { dialog.dismiss(); }

    void handlerDialog()
    {

        Handler handler=new Handler();
        if(counter==0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                    admin_panel.counter++;
//                    if( !checkConnectivity())
//                    {admin_panel.counter=1;
//                        connectivityDialog();
//                    }
                }
            }, 1500);
        }
        else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
//                    if( !checkConnectivity())
//                    {admin_panel.counter=0;
//                        connectivityDialog();
//                    }
                }
            }, 300);
        }

    }



    private void actionBar() {
        TextView title=findViewById(R.id.title_collection);
        title.setText("Notifications");

        back=findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Collection_GridView.class));
            }
        });
        share_ap=findViewById(R.id.share_app);
        share_ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_ap.setImageResource(R.drawable.favourite_active);
                startActivity(new Intent(getApplicationContext(),Download.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Collection_GridView.class));
    }
}


