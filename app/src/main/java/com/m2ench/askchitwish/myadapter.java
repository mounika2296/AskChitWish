package com.m2ench.askchitwish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by sai on 1/14/2017.
 */

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder>{
    private ArrayList<item> listItems;
    private Context context;

    public myadapter(ArrayList<item> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_iten,parent,false);
        return new ViewHolder(v,context,listItems);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item listItem=listItems.get(position);
        holder.Textviewhead.setText(listItem.getTopic_name());
        holder.textViewDesc.setText(listItem.getShort_desc());
       /* holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView Textviewhead;
        public TextView textViewDesc;
        public LinearLayout linearLayout;
        ArrayList<item> listItems=new ArrayList<item>();
        Context contex;


        public ViewHolder(View itemView,final Context context, ArrayList<item> listItems) {
            super(itemView);
            this.contex=context;
            this.listItems=listItems;
            Textviewhead=(TextView)itemView.findViewById(R.id.Textviewhead);
            textViewDesc=(TextView)itemView.findViewById(R.id.textViewDesc);
            linearLayout =(LinearLayout)itemView.findViewById(R.id.linearlayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(contex, description.class);
            item it= listItems.get(getAdapterPosition());
            intent.putExtra("head",it.getTopic_name());
            intent.putExtra("desc",it.getShort_desc());
            v.getContext().startActivity(intent);

        }
    }
}
