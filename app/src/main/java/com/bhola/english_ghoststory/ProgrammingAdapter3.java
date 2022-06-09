package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgrammingAdapter3 extends RecyclerView.Adapter<ProgrammingAdapter3.viewholder> {
    List<ModelData_forFavourites> collectonData;


    AlertDialog dialog;
    Context context;

    public ProgrammingAdapter3(List<ModelData_forFavourites> collectonData, Context context) {
        this.collectonData = collectonData;
        this.context = context;

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout, parent, false);
        return new com.bhola.english_ghoststory.ProgrammingAdapter3.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        ModelData_forFavourites modelData_forFavourites = collectonData.get(position);


        holder.title.setText(modelData_forFavourites.getTitle());
        holder.date.setText("2019-10-15");
        holder.heading.setText(modelData_forFavourites.getStory());
        String indexx = String.valueOf(position + 1);
        holder.index.setText(indexx);
        holder.recyclerview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String android_id = Settings.Secure.getString(v.getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                final Vibrator vibe = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(80);//80 represents the milliseconds (the duration of the vibration)


                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View promptView = inflater.inflate(R.layout.delete, null);
                builder.setView(promptView);
                builder.setCancelable(false);
                Button delete = promptView.findViewById(R.id.DELETE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mp = MediaPlayer.create(v.getContext(), R.raw.sound);
                        mp.start();

                        String[] Table_Names={"collection1","collection2","collection3","collection4","collection5","collection6","collection7","collection8","collection9","collection10"};

                        for (int i = 0; i < Table_Names.length; i++) {
                            Cursor cursor = new DatabaseHelper2(v.getContext(), SplashScreen.DB_NAME, SplashScreen.DB_VERSION, Table_Names[i]).readalldata();

                            while (cursor.moveToNext()) {
                                String Title = cursor.getString(1);
                                if (Title.equals(modelData_forFavourites.getTitle())) {

                                    String res = new DatabaseHelper2(v.getContext(), SplashScreen.DB_NAME, SplashScreen.DB_VERSION, Table_Names[i]).updaterecord(modelData_forFavourites.get_id(), 0);
                                    break;

                                }
                            }
                            if (cursor != null) {

                                cursor.close();
                            }


                        }


                        Toast.makeText(v.getContext(), "Removed From Download ", Toast.LENGTH_SHORT).show();
                        collectonData.remove(position);
                        Download.adapter2.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                Button cancel = promptView.findViewById(R.id.CANCEL);


                dialog = builder.create();
                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });


                return false;
            }
        });

        holder.recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StoryPage_Downloads.class);
                intent.putExtra("Story", modelData_forFavourites.getStory());
                intent.putExtra("Title", modelData_forFavourites.getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });

    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title;
        TextView index, heading, date;

        RelativeLayout recyclerview;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            recyclerview = itemView.findViewById(R.id.recyclerviewLayout);
            title = itemView.findViewById(R.id.titlee);
            index = itemView.findViewById(R.id.index);
            date = itemView.findViewById(R.id.date_recyclerview);
            heading = itemView.findViewById(R.id.heading_recyclerview);

        }
    }

    @Override
    public int getItemCount() {
        return collectonData.size();
    }
}
