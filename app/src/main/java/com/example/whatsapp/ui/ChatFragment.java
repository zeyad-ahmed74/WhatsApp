package com.example.whatsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.data.model.UserModel;
import com.example.whatsapp.ui.Adapter.UserAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class ChatFragment extends Fragment implements RecycleViewOnItemClick{

    RecyclerView userRecycler;
    ArrayList<UserModel> user = new ArrayList<>();
    UserAdapter userAdapter;
    ImageView imageView;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    String MyName;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ReceiveData();


        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_chat, container, false);
        return inflate;


    }


    private void ReceiveData() {
        SharedPreferences sharedPreferences =getContext().getSharedPreferences("share", Context.MODE_PRIVATE);
         MyName=sharedPreferences.getString("name",null);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fakeData();
        initUI(view);
        getUsers();

    }



    public void initUI(View view) {
        userRecycler = view.findViewById(R.id.chat_list);
        userAdapter = new UserAdapter(this);
        userRecycler.setAdapter(userAdapter);
        userRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        imageView = view.findViewById(R.id.user_img);


    }

    private void getUsers(){
       databaseReference.child("Users").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               Log.d("tttt","New Child added"+snapshot);
               user.add(snapshot.getValue(UserModel.class));
               userAdapter.addUsers(user);
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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                Toast.makeText(getContext(), "Pineed", Toast.LENGTH_LONG).show();
                return true;
            case R.id.option2:
                Toast.makeText(getContext(), "Muted", Toast.LENGTH_LONG).show();
                return true;
            case R.id.option3:
                Toast.makeText(getContext(), "Archived", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void OnItemClicked(int position) {
        Intent in = new Intent(getActivity(),ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",new UserModel(user.get(position).getKey()
                ,user.get(position).getImg(),user.get(position).getName()));
        in.putExtras(bundle);
        startActivity(in);
    }

    @Override
    public void OnLongItemClicked(int position) {

    }

//    @Override
//    public void onClickItem(View view, int position) {
//
//        UserModel userModel = user.get(position);
//        Toast.makeText(getActivity(), ""+userModel.getName(), Toast.LENGTH_SHORT).show();
//
//    }
}