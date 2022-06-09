package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgrammingAdapter2 extends RecyclerView.Adapter<ProgrammingAdapter2.viewholder> {

    List<RowData> collectonData;
    Context context;
    String Collection_Number;


    public ProgrammingAdapter2(List<RowData> collectonData, Context context, String message) {

        this.collectonData = collectonData;
        this.context=context;
        this.Collection_Number=message;
    }




    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout, parent, false);
        return new com.bhola.english_ghoststory.ProgrammingAdapter2.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        RowData firebaseData = collectonData.get(position);
        holder.title.setText(firebaseData.getTitle());

        String keyy="1";
        holder.recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),StoryPage.class);
                intent.putExtra("Story",firebaseData.getStory());
                intent.putExtra("Collection",Collection_Number);
                intent.putExtra("Titlee",firebaseData.getTitle());
                Log.d("AAA", "title id: "+firebaseData.getTitle());
                intent.putExtra("Collection2","Notification");
                intent.putExtra("key",keyy);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });


    }




    public class viewholder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;

        LinearLayout recyclerview;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            recyclerview=itemView.findViewById(R.id.recyclerviewLayout);
            title=itemView.findViewById(R.id.titlee);
            date=itemView.findViewById(R.id.date_recyclerview);



//Copy_button= itemView.findViewById(R.id.copy_button);
//Copy_button.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//        ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText("label", name.getText().toString());
//        clipboard.setPrimaryClip(clip);
//        Toast.makeText(v.getContext(), "COPIED", Toast.LENGTH_SHORT).show();
//
//    }
//});

//            share_Button=itemView.findViewById(R.id.share_button);
//            share_Button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent();
//                    intent.setAction(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT,name.getText().toString());
//                    intent.setType("text/plain");
//                    intent=Intent.createChooser(intent,"Share By");
//
//                    v.getContext().startActivity(intent);
//                }
//            });

        }


    }

    @Override
    public int getItemCount() {
        return collectonData.size();
    }
}
