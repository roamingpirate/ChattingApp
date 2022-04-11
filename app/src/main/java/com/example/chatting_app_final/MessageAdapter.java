package com.example.chatting_app_final;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<message_info> {

    String username;


    public MessageAdapter(Context context, int resource, List<message_info> objects) {
        super(context, resource, objects);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        username=preferences.getString("UserName","");
    }


    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.messageview, parent, false);
        }
        message_info info=getItem(position);
        TextView text=convertView.findViewById(R.id.message);
        TextView user=convertView.findViewById(R.id.user);
        RelativeLayout layout=convertView.findViewById(R.id.layout);
        LinearLayout ll=convertView.findViewById(R.id.linear);

        RelativeLayout.LayoutParams pram=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);



        if(info.getUser().matches(username))
        {
            pram.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
            text.setBackgroundResource(R.drawable.rectangle_right);
        }
        else{
            pram.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
            text.setBackgroundResource(R.drawable.rectangle_left);
        }
       // layout.setLayoutParams(pram);
        ll.setLayoutParams(pram);
        //user.setLayoutParams(pram);



        text.setText(info.getText());
        user.setText(info.getUser());


        return  convertView;

    }
}
