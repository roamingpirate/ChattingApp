package com.example.chatting_app_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Random number generate
    Random randomCode;
    Boolean check;

    //DialogBOX
    AlertDialog.Builder PopUpBuilder;
    AlertDialog PopUp;
    FloatingActionButton PopBtn;
    View PopUpView;

    //constants
    public static  final int RC_SIGN_IN=1;

    //Signed in user info
    private String username;
    private String user_id;
    private ArrayList<Group_info> list;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference user_databaseReference;
    private DatabaseReference group_databaseReference;
    private DatabaseReference user_reference;
    private DatabaseReference UserGroupReference;

    private FirebaseAuth user_Auth;
    private FirebaseAuth.AuthStateListener user_Auth_listener;
    private RecyclerView recyclerView;
    private Group_info_adapter group_adapter;
    private ChildEventListener childEventListener;

    private user_info User_info;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        //Initialize Random
        randomCode=new Random();

        //Pop Button Listener
        PopBtn =findViewById(R.id.PopBtn);
        PopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchPopUp();
            }
        });

        //initializing user info
        username=null;
        user_id=null;
        list=new ArrayList<Group_info>();


        firebaseDatabase =FirebaseDatabase.getInstance();
        user_databaseReference=firebaseDatabase.getReference().child("users");
        group_databaseReference=firebaseDatabase.getReference().child("Groups");
        User_info =new user_info();

        //recycler view set up
        recyclerView=findViewById(R.id.group_info_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        group_adapter = new Group_info_adapter();
       recyclerView.setAdapter(group_adapter);


        //Setting up user authorization functions
        user_Auth=FirebaseAuth.getInstance();
        user_Auth_listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null) {
                    //means user is SIGNED IN
                    username=user.getDisplayName();
                    editor.putString("UserName",username);
                    editor.apply();
                    user_id=user.getUid();
                    SignUpInitialize();
                }
                else{
                    //User has Signed Out
                    //SignOutCleanUp();
                    LaunchAuthUI();
                }
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        user_Auth.removeAuthStateListener(user_Auth_listener);
        DetachGroupDatabaseListener();
        group_adapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_Auth.addAuthStateListener(user_Auth_listener);
        //AttachGroupDatabaseListener();
    }

    public void AttachGroupDatabaseListener()
    {
         childEventListener =new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                 Group_info gi=snapshot.getValue(Group_info.class);
                 list.add(gi);
                 group_adapter.add(gi);
             }

             @Override
             public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

             }

             @Override
             public void onChildRemoved(DataSnapshot snapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

             }

             @Override
             public void onCancelled(DatabaseError error) {

             }
         };

         UserGroupReference.addChildEventListener(childEventListener);
    }

    public void DetachGroupDatabaseListener()
    {
        UserGroupReference.removeEventListener(childEventListener);
    }


    public void SignUpInitialize()
    {
        user_reference=user_databaseReference.child(user_id);
        user_reference.child("username").setValue(username);
        user_reference.child("userid").setValue(user_id);
        UserGroupReference =user_reference.child("Group");
        AttachGroupDatabaseListener();
    }

    public void SignOutCleanUp()
    {
        DetachGroupDatabaseListener();
        username="null";
        user_id="null";
    }
    public void LaunchAuthUI()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.EmailBuilder().build()))
                        .build(),RC_SIGN_IN);
    }

    public void LaunchPopUp()
    {
        PopUpBuilder=new AlertDialog.Builder(this);
        PopUpView = getLayoutInflater().inflate(R.layout.popup,null);
        EditText GroupName =PopUpView.findViewById(R.id.GroupName);
        EditText GroupCode=PopUpView.findViewById(R.id.GroupCode);

        TextView create_option=PopUpView.findViewById(R.id.create);
        TextView Join_option=PopUpView.findViewById(R.id.join);
        LinearLayout OptionLayout=PopUpView.findViewById(R.id.options);
        LinearLayout CreateLayout=PopUpView.findViewById(R.id.CreateMenu);
        LinearLayout JoinLayout =PopUpView.findViewById(R.id.JoinMenu);
        CreateLayout.setVisibility(View.GONE);
        JoinLayout.setVisibility(View.GONE);
        OptionLayout.setVisibility(View.VISIBLE);



        create_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionLayout.setVisibility(View.GONE);
                CreateLayout.setVisibility(View.VISIBLE);
            }
        });

        Join_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionLayout.setVisibility(View.GONE);
                JoinLayout.setVisibility(View.VISIBLE);
            }
        });

        TextView Create_info =PopUpView.findViewById(R.id.create_info);
        Create_info.setVisibility(View.INVISIBLE);
        Button CreateBtn =PopUpView.findViewById(R.id.create_btn);
        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create_info.setVisibility(View.VISIBLE);
                if(GroupName.getText().toString().matches(""))
                {
                    Create_info.setText("Please enter a valid Name!");
                    return;
                }
                AddGroup(GroupName.getText().toString());
            }
        });

        TextView Join_info=PopUpView.findViewById(R.id.join_info);
        Join_info.setVisibility(View.INVISIBLE);
        Button JoinBtn=PopUpView.findViewById(R.id.join_btn);
        JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Join_info.setVisibility(View.VISIBLE);
                JoinGroup(GroupCode.getText().toString());
            }
        });

        PopUpBuilder.setView(PopUpView);
        PopUp=PopUpBuilder.create();
        PopUp.show();
    }

    public void AddGroup(String Name)
    {
              String Code = String.valueOf(randomCode.nextInt(10000));
                DatabaseReference gr = group_databaseReference.child(Code);
                gr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(!snapshot.hasChildren())
                        {
                            Group_info gi = new Group_info(Name, Code);
                            UserGroupReference.push().setValue(gi);
                            gr.child("GroupName").setValue(Name);
                            gr.child("code").setValue(Code);
                            gr.child("Admin").setValue(username);
                            PopUp.dismiss();
                            Toast.makeText(getApplicationContext(),"Group Added Succesfully!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            AddGroup(Name);
                        }
                        gr.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }

    public void JoinGroup(String Code)
    {
        DatabaseReference gr=group_databaseReference.child(Code);
        TextView info=PopUpView.findViewById(R.id.join_info);

        info.setVisibility(View.VISIBLE);
        if(Code.matches(""))
        {
            info.setText("Please enter a valid Code!");
            return;
        }

        if(isAlreadyPresent(Code)){
            Toast.makeText(getApplicationContext(),"You are already in the group!",Toast.LENGTH_SHORT).show();
            PopUp.dismiss();
            return;
        }
        check=true;
        gr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                 if(snapshot.hasChildren()&&check)
                 {
                     gr.removeEventListener(this);
                     gr.child("Members").push().setValue(username);
                     String name=snapshot.child("GroupName").getValue(String.class);
                     Group_info gi=new Group_info(name,Code);
                     UserGroupReference.push().setValue(gi);
                     PopUp.dismiss();
                     check=false;
                     Toast.makeText(getApplicationContext(),"Group joined Succesfully!",Toast.LENGTH_SHORT).show();
                 }
                 else{
                     info.setText("Group with that code Don't exist");
                 }


            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public boolean isAlreadyPresent(String code)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getGroupCode().matches(code))
            {
                return true;
            }
        }
        return false;
    }

}