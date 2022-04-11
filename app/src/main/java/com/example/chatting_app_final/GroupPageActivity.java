package com.example.chatting_app_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroupPageActivity extends AppCompatActivity {

    EditText editText;
    Button SendBtn;

    String GroupCode;
    String UserName;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference GroupReference;
    DatabaseReference MessageReference;

    ListView MessageView;
    ArrayAdapter<message_info> adapter;

    ChildEventListener messageChildEventListener;

    ArrayList<message_info> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        GroupCode=getIntent().getStringExtra("GroupCode");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        UserName=preferences.getString("UserName","");

        //interface
        editText=findViewById(R.id.messageEditText);
        SendBtn=findViewById(R.id.sendButton);
        //setting up firebase reference
        firebaseDatabase=FirebaseDatabase.getInstance();
        GroupReference=firebaseDatabase.getReference().child("Groups").child(GroupCode);
        MessageReference=GroupReference.child("messages");

        //list setup
        list=new ArrayList<message_info>();
        message_info mi=new message_info("lio","pop");
        //list.add(mi);
        adapter=new MessageAdapter(this,R.layout.messageview,list);
        MessageView= findViewById(R.id.messageListView);
        MessageView.setAdapter(adapter);

        MessageDatabaseListener();



        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=String.valueOf(editText.getText());
                message_info mi=new message_info(text,UserName);
                MessageReference.push().setValue(mi);
                editText.setText("");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void MessageDatabaseListener()
    {
        messageChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot,String previousChildName) {
                   message_info info=snapshot.getValue(message_info.class);
                //message_info info =new message_info("jok","mok");
                  adapter.add(info);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot,String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot,String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        };

        MessageReference.addChildEventListener(messageChildEventListener);
    }
}

