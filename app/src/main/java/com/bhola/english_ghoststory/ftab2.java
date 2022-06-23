package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ftab2 extends Fragment {

    LinearLayout linearLayoutHider;
    RecyclerView recyclerView;
    List<AudioCategoryModel> collectionData;
    Adapter adapter;

    public ftab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ftab2, container, false);
//        if (SplashScreen.updatingApp_on_Playstore.equals("inactive") || SplashScreen.Login_Times > 4) {
//            linearLayoutHider = view.findViewById(R.id.linearLayoutHider);
//            linearLayoutHider.setVisibility(View.VISIBLE);
//        }


        recyclerView = view.findViewById(R.id.recyclerView);
        collectionData = new ArrayList<AudioCategoryModel>();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getDataFromDatabase();
        return view;
    }

    private void getDataFromDatabase() {

        Cursor cursor = new DatabaseHelper(getActivity(), SplashScreen.DB_NAME, SplashScreen.DB_VERSION, "Audio_story_Category").readalldata();

        try {
            while (cursor.moveToNext()) {
                AudioCategoryModel rowData = new AudioCategoryModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                collectionData.add(rowData);

            }
        } finally {
            cursor.close();

        }
        if(SplashScreen.updatingApp_on_Playstore.equals("active")){
            collectionData.subList(0, collectionData.size()).clear();
        }

//        Collections.shuffle(collectionData);
        adapter = new Adapter(collectionData, getActivity());
        recyclerView.setAdapter(adapter);
    }

}


class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {

    List<AudioCategoryModel> collectonData;
    Context context;



    public Adapter(List<AudioCategoryModel> rowDatas, Context context) {
        this.collectonData = rowDatas;
        this.context = context;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout_audio, parent, false);
        return new com.bhola.english_ghoststory.Adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        AudioCategoryModel rowdata = collectonData.get(position);
        holder.title.setText(rowdata.getCollectionName());
        Picasso.get().load(rowdata.getCoverImage()).into( holder.image);

        holder.recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Collection_detail_Audio.class);
                intent.putExtra("ref", rowdata.getRef());
                intent.putExtra("season", rowdata.getSeason());
                intent.putExtra("Title", rowdata.getCollectionName());
                intent.putExtra("coverImage", rowdata.getCoverImage());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return collectonData.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        ImageView image;

        LinearLayout recyclerview;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            recyclerview = itemView.findViewById(R.id.recyclerviewLayout);
            title = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
        }
    }
}



