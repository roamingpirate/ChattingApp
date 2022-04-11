package com.example.chatting_app_final;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Group_info_adapter extends RecyclerView.Adapter<Group_info_adapter.view_holder> {

     ArrayList<Group_info> list;


    public Group_info_adapter() {
        list= new ArrayList<Group_info>();
    }

    @Override
    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.group_info_view,parent,false);
        view_holder holder=new view_holder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(Group_info_adapter.view_holder holder, int position) {
        String group_name=list.get(position).getGroupName();
          holder.group_name.setText(group_name);
         holder.IconText.setText(group_name.substring(0,1));
         holder.layout.setOnClickListener(new GroupClickListener(list.get(position).getGroupCode()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class view_holder extends RecyclerView.ViewHolder
    {
        TextView group_name;
        LinearLayout layout;
        TextView IconText;
        public view_holder( View itemView) {
            super(itemView);
            group_name=itemView.findViewById(R.id.group_name);
             layout=itemView.findViewById(R.id.layout);
            IconText=itemView.findViewById(R.id.icon_text);
        }
    }

    public void  add(Group_info info)
    {
        list.add(info);
        notifyDataSetChanged();
    }

    public void clear()
    {
        list.clear();
        notifyDataSetChanged();
    }
}
