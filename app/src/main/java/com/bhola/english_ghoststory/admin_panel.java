package com.bhola.english_ghoststory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_panel extends AppCompatActivity {

    DatabaseReference mref;
    Button Refer_App_url_BTN;
    Switch switch_Exit_Nav, switch_Activate_Ads, switch_UpdatingMode;
    Button Ad_Network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        mref = FirebaseDatabase.getInstance().getReference().child("English_GhostStory");
        appControl();
        Ad_Network_Selection();


    }

    private void appControl() {
        checkButtonState();


        switch_Exit_Nav = findViewById(R.id.switch_Exit_Nav);
        switch_Activate_Ads = findViewById(R.id.Activate_Ads);
        switch_UpdatingMode = findViewById(R.id.updatingSwitch);

        EditText Refer_App_url2;
        Refer_App_url2 = findViewById(R.id.Refer_App_url2);
        Refer_App_url_BTN = findViewById(R.id.Refer_App_url_BTN);


        Refer_App_url_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Refer_App_url2.length() > 2) {
                    mref.child("Refer_App_url2").setValue(Refer_App_url2.getText().toString());
                    Toast.makeText(admin_panel.this, "Refer_App_url2 ADDED", Toast.LENGTH_SHORT).show();
                    Refer_App_url2.setText("");
                } else
                    Toast.makeText(admin_panel.this, "Field is Empty", Toast.LENGTH_SHORT).show();


            }

        });

        switch_Exit_Nav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    mref.child("switch_Exit_Nav").setValue("active");
                } else {
                    mref.child("switch_Exit_Nav").setValue("inactive");
                }

            }
        });

        switch_Activate_Ads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mref.child("Ads").setValue("active");
                } else {
                    mref.child("Ads").setValue("inactive");
                }

            }
        });


        switch_UpdatingMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mref.child("updatingApp_on_Playstore").setValue("active");
                } else {
                    mref.child("updatingApp_on_Playstore").setValue("inactive");
                }

            }
        });


    }

    private void checkButtonState() {
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String match = (String) snapshot.child("switch_Exit_Nav").getValue().toString().trim();

                if (match.equals("active")) {
                    switch_Exit_Nav.setChecked(true);
                    switch_Exit_Nav.setBackgroundColor(Color.parseColor("#4CAF50"));

                } else {
                    switch_Exit_Nav.setChecked(false);
                    switch_Exit_Nav.setBackgroundColor(Color.parseColor("#FF0000"));
                }


                String Ads = (String) snapshot.child("Ads").getValue().toString().trim();

                if (Ads.equals("active")) {
                    switch_Activate_Ads.setChecked(true);
                    switch_Activate_Ads.setBackgroundColor(Color.parseColor("#4CAF50"));

                } else {
                    switch_Activate_Ads.setChecked(false);
                    switch_Activate_Ads.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                String updatingApp_on_Playstore = (String) snapshot.child("updatingApp_on_Playstore").getValue().toString().trim();

                if (updatingApp_on_Playstore.equals("active")) {
                    switch_UpdatingMode.setChecked(true);
                    switch_UpdatingMode.setBackgroundColor(Color.parseColor("#4CAF50"));

                } else {
                    switch_UpdatingMode.setChecked(false);
                    switch_UpdatingMode.setBackgroundColor(Color.parseColor("#FF0000"));
                }

                String AdNetwork = (String) snapshot.child("Ad_Network").getValue().toString().trim();

                Ad_Network.setText(AdNetwork);
                if (Ad_Network.getText().toString().equals("admob")) {
                    Ad_Network.setBackgroundColor(Color.parseColor("#D11A1A"));
                    Ad_Network.setTextColor(Color.parseColor("#000000"));

                } else {
                    Ad_Network.setBackgroundColor(Color.parseColor("#4267B2"));
                    Ad_Network.setTextColor(Color.parseColor("#ffffff"));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void Ad_Network_Selection() {

        Ad_Network = findViewById(R.id.Ad_Network);
        Ad_Network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Ad_Network.getText().toString().equals("admob")) {
                    mref.child("Ad_Network").setValue("facebook");
                    Ad_Network.setBackgroundColor(Color.parseColor("#D11A1A"));
                    Ad_Network.setTextColor(Color.parseColor("#ffffff"));

                } else {
                    mref.child("Ad_Network").setValue("admob");
                    Ad_Network.setBackgroundColor(Color.parseColor("#4267B2"));
                    Ad_Network.setTextColor(Color.parseColor("#000000"));
                }


            }
        });

    }


}