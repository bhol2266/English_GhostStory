package com.bhola.english_ghoststory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class ftab2 extends Fragment {

    LinearLayout linearLayoutHider;
    LinearLayout HHS, EKAB, KWST, EPK;

    public ftab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ftab2, container, false);
        if (SplashScreen.updatingApp_on_Playstore.equals("inactive") || SplashScreen.Login_Times > 4) {
            linearLayoutHider = view.findViewById(R.id.linearLayoutHider);
            linearLayoutHider.setVisibility(View.VISIBLE);
        }


        HHS = view.findViewById(R.id.HHS);
        HHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail_Audio.class);
                intent.putExtra("category", "HHS");
                intent.putExtra("actionBar", "Hindi Horror Stories");
                startActivity(intent);
            }
        });
        EKAB = view.findViewById(R.id.EKAB);
        EKAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail_Audio.class);
                intent.putExtra("category", "EKAB");
                intent.putExtra("actionBar", "Ek Kahani Aishi Bhi");
                startActivity(intent);
            }
        });
        KWST = view.findViewById(R.id.KWST);
        KWST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail_Audio.class);
                intent.putExtra("category", "KWST");
                intent.putExtra("actionBar", "Kya Wo Sach Tha");
                startActivity(intent);
            }
        });
        EPK = view.findViewById(R.id.EPK);
        EPK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail_Audio.class);
                intent.putExtra("category", "EPK");
                intent.putExtra("actionBar", "Ek Purani Kahani");
                startActivity(intent);
            }
        });


        return view;
    }


}


