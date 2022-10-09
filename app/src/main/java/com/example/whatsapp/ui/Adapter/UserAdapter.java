package com.example.whatsapp.ui.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsapp.R;
import com.example.whatsapp.data.model.UserModel;
import com.example.whatsapp.databinding.ItemUserLayoutBinding;
import com.example.whatsapp.ui.RecycleViewOnItemClick;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    private static List<UserModel> users;
    private static RecycleViewOnItemClick recycleViewOnItemClick;

    public UserAdapter(RecycleViewOnItemClick recycleViewOnItemClick) {
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    public void addUsers(List<UserModel> users){
        this.users=users;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserLayoutBinding binding= ItemUserLayoutBinding.bind(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_layout,parent,false));
        return new UserHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserModel usermodel = users.get(position);
        holder.binding.userName.setText(usermodel.getName());
        Glide.with(holder.binding.userImg.getContext())
                .load(usermodel.getImg())
                .into(holder.binding.userImg);
    }

    @Override
    public int getItemCount() {
       // return users !=null ? users.size() : 0;
        if(users!=null)
            return users.size(); 
        return 0;
    }

    static class UserHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        ItemUserLayoutBinding binding;
        public UserHolder(@NonNull ItemUserLayoutBinding binding) {
            super(binding.getRoot());
//            userImage = view.findViewById(R.id.user_img);
//            userName = view.findViewById(R.id.user_name);
             this.binding=binding;

            binding.mainContainer.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   recycleViewOnItemClick.OnItemClicked(getAdapterPosition());

               }

           });

            binding.mainContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    recycleViewOnItemClick.OnLongItemClicked(getAdapterPosition());
                    return true;
                }
            });
        }
/*
        @Override
        public void onClick(View view) {
            /*int position = getAdapterPosition();
            UserModel userModel = users.get(position);*/

        }
     /*   @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }*/
}


