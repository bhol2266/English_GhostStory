package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ftab1 extends Fragment {
    Context context = getActivity();
    CardView collection1, collection2, collection3, collection4, collection5, collection6, collection7, collection8, collection9, collection10;

    public ftab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment

        View view = inflater.inflate(R.layout.fragment_ftab1, container, false);
        collectionGridItems(view);
    


        return view;
    }

    private void collectionGridItems(View view) {
        collection1 = view.findViewById(R.id.collection1);
        collection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-1";
                String DB_TABLENAME = "collection1";
                intent.putExtra("bhola", title);
                intent.putExtra("tableNo", "collection1");
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                startActivity(intent);
            }
        });
        collection2 = view.findViewById(R.id.collection2);
        collection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-2";
                String DB_TABLENAME = "collection2";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection2");
                startActivity(intent);
            }
        });
        collection3 = view.findViewById(R.id.collection3);
        collection3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-3";
                String DB_TABLENAME = "collection3";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection3");
                startActivity(intent);
            }
        });
        collection4 = view.findViewById(R.id.collection4);
        collection4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-4";
                String DB_TABLENAME = "collection4";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection4");
                startActivity(intent);
            }
        });
        collection5 = view.findViewById(R.id.collection5);
        collection5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-5";
                String DB_TABLENAME = "collection5";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection5");
                startActivity(intent);
            }
        });
        collection6 = view.findViewById(R.id.collection6);
        collection6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-6";
                String DB_TABLENAME = "collection6";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection6");
                startActivity(intent);
            }
        });

        collection7 = view.findViewById(R.id.collection7);
        collection7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-7";
                String DB_TABLENAME = "collection7";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection7");
                startActivity(intent);
            }
        });

        collection8 = view.findViewById(R.id.collection8);
        collection8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-8";
                String DB_TABLENAME = "collection8";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection8");
                startActivity(intent);
            }
        });

        collection9 = view.findViewById(R.id.collection9);
        collection9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-9";
                String DB_TABLENAME = "collection9";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection9");
                startActivity(intent);
            }
        });

        collection10 = view.findViewById(R.id.collection10);
        collection10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Collection_detail.class);
                String title = "Collection-10";
                String DB_TABLENAME = "collection10";
                intent.putExtra("bhola", title);
                intent.putExtra("DB_TABLENAME", DB_TABLENAME);
                intent.putExtra("tableNo", "collection10");
                startActivity(intent);
            }
        });


        }
    }




