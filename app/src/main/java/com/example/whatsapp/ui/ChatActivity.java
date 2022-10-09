package com.example.whatsapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.whatsapp.R;
import com.example.whatsapp.data.model.ChatModel;
import com.example.whatsapp.data.model.UserModel;
import com.example.whatsapp.ui.Adapter.ChatMessagesAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {


    TextView UserNameText;
    ImageView UserImage,Back;
    DatabaseReference databaseReference;
    FrameLayout send;
    EditText MessageText;
    String MyName,MyPhone;
    RecyclerView recyclerView;
    ChatMessagesAdapter chatMessagesAdapter;
    Boolean meOrNot = true;
    ArrayList<ChatModel> Messages = new ArrayList<>();
    ChatModel chatModel= new ChatModel();
    //ArrayList<String> OtherMessage = new ArrayList<>();

    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().hide();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        InitUI();
        userModel = (UserModel) getIntent().getExtras().getSerializable("user");
        UserNameText.setText(userModel.getName());
        Glide.with(this).load(userModel.getImg()).into(UserImage);
        ReceiveMyData();
        //DisplayMyMessagesForOnce();
        //UserImage.setImageURI(UserModel);

        //DisplayMyMessagesForOnce();
        send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                     SendMessages();

           }
       });
        DisplayMyMessages();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ChatActivity.this,MainActivity.class));
            }
        });

    }

    private void DisplayMyMessagesForOnce() {
        databaseReference.child("Chats").child("Messages").child(MyName+"_"+userModel.getName()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Messages.add(snapshot1.getValue(ChatModel.class));
                        }
                        chatMessagesAdapter.addMessage(Messages,true);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        databaseReference.child("Chats").child("Messages").child(userModel.getName()+"_"+MyName).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Messages.add(snapshot1.getValue(ChatModel.class));
                        }
                        chatMessagesAdapter.addMessage(Messages,false);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }

    private void SendMessages() {
        DatabaseReference ChatsRE=databaseReference.child("Chats");
        DatabaseReference MyMessagesRF=ChatsRE.child("Messages");

            MyMessagesRF.child(MyName+"_"+userModel.getName()).push()
                    .setValue(new ChatModel(MessageText.getText().toString(),meOrNot));
            MessageText.getText().clear();
    }



    private void InitUI() {

        UserNameText=findViewById(R.id.Chat_name);
        UserImage=findViewById(R.id.chat_img);
        Back=findViewById(R.id.back_icon);
        send=findViewById(R.id.send_msg);
        MessageText=findViewById(R.id.Msg_edittext);
        recyclerView=findViewById(R.id.chat_messages);
        chatMessagesAdapter=new ChatMessagesAdapter();
        recyclerView.setAdapter(chatMessagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void DisplayMyMessages(){

        databaseReference.child("Chats").child("Messages").child(MyName+"_"+userModel.getName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.w("nnnnnn","new Child Added :"+snapshot.getValue());
                Messages.add(snapshot.getValue(ChatModel.class));
                chatMessagesAdapter.addMessage(Messages,true);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.w("mmmmmm",""+userModel.getName());
        databaseReference.child("Chats").child("Messages").child(userModel.getName()+"_"+MyName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.w("nnnnnn","new Child Added :"+snapshot.getValue());
                Messages.add(snapshot.getValue(ChatModel.class));
                chatMessagesAdapter.addMessage(Messages,false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ReceiveMyData() {
        SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
        MyName=sharedPreferences.getString("name",null);
        MyPhone=sharedPreferences.getString("phone",null);
    }
}