package com.bhola.english_ghoststory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.viewholder> {

    List<RowData> collectonData;
    Context context;
    String Collection_Number;
    String DB_TABLENUMBER;



    public ProgrammingAdapter(List<RowData> rowDatas, Context context, String title1, String database_tableNo) {
        this.collectonData = rowDatas;
        this.context=context;
        this.Collection_Number=title1;
        this.DB_TABLENUMBER=database_tableNo;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout, parent, false);
        return new com.bhola.english_ghoststory.ProgrammingAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        RowData rowdata = collectonData.get(position);
        holder.title.setText(rowdata.getTitle());
        holder.date.setText("2019-10-15");
        holder.recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),StoryPage.class);
                intent.putExtra("_id",rowdata.get_id());
                intent.putExtra("Story",rowdata.getStory());
                intent.putExtra("DB_TABLENUMBER",DB_TABLENUMBER);
                intent.putExtra("Collection2","Notification");
                intent.putExtra("Title",rowdata.getTitle());

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

        LinearLayout recyclerview;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            recyclerview=itemView.findViewById(R.id.recyclerviewLayout);
            title=itemView.findViewById(R.id.titlee);
            date=itemView.findViewById(R.id.date_recyclerview);

        }
    }
}
