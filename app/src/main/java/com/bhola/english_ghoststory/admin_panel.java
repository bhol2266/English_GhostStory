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
    public static int counter=0;

    DatabaseReference mref;
    Button AddControl;
    Switch switch_Exit_Nav,switch_Activate_Ads;
    static String text;
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


        switch_Exit_Nav=findViewById(R.id.switch_Exit_Nav);
        switch_Activate_Ads=findViewById(R.id.Activate_Ads);

        EditText Refer_App_url2;
        Refer_App_url2=findViewById(R.id.Refer_App_url2);
        AddControl=findViewById(R.id.AddControl);


        AddControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mref.child("Refer_App_URL").setValue(Refer_App_url2.getText().toString());

                Toast.makeText(admin_panel.this, "Field is Empty", Toast.LENGTH_SHORT).show();


            }

        });

        switch_Exit_Nav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                mref.child("switch_Exit_Nav").setValue("active");
            }
                else
                {
                    mref.child("switch_Exit_Nav").setValue("inactive");
                }

            }
        });

        switch_Activate_Ads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    mref.child("Ads").setValue("active");
                }
                else
                {
                    mref.child("Ads").setValue("inactive");
                }

            }
        });


    }

    private void checkButtonState() {
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String match= (String) snapshot.child("switch_Exit_Nav").getValue().toString().trim();

                if(match.equals("active"))
                {
                    switch_Exit_Nav.setChecked(true);
                    switch_Exit_Nav.setBackgroundColor(Color.parseColor("#4CAF50"));

                }
                 else
                {

                    switch_Exit_Nav.setChecked(false);
                    switch_Exit_Nav.setBackgroundColor(Color.parseColor("#FF0000"));
                }


                if(snapshot.child("control").exists())
                {
                    AddControl.setText("Delete Control");
                    AddControl.setBackgroundColor(Color.parseColor("#4CAF50"));
                }
                else {
                    AddControl.setText("Add Control");
                    AddControl.setBackgroundColor(Color.parseColor("#FF0000"));
                }

                String Ads= (String) snapshot.child("Ads").getValue().toString().trim();

                if(Ads.equals("active"))
                {
                    switch_Activate_Ads.setChecked(true);
                    switch_Activate_Ads.setBackgroundColor(Color.parseColor("#4CAF50"));

                }
                else
                {
                    switch_Activate_Ads.setChecked(false);
                    switch_Activate_Ads.setBackgroundColor(Color.parseColor("#FF0000"));
                }

                Ad_Network.setText(SplashScreen.Ad_Network_Name);
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

        Ad_Network=findViewById(R.id.Ad_Network);
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