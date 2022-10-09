package com.example.whatsapp.ui.Adapter;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.example.whatsapp.data.model.ChatModel;
import com.example.whatsapp.data.model.MyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatHolder>{


    private List<ChatModel> Message;
    private Boolean meOrNot;
    //private  List<String> OtherMsg;
   //private  ChatModel chatModel = new ChatModel();



   public void addMessage(List<ChatModel>Message , Boolean meOrNot){
        this.Message=Message;
        this.meOrNot=meOrNot;
        notifyItemInserted(Message.size());
    }

   /* public void addOtherMessage(List<String> otherMsg) {
        this.OtherMsg = otherMsg;
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_design,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {

        ChatModel chatModel = Message.get(position);
       // holder.OtherMessage.setVisibility(View.INVISIBLE);
        if(meOrNot)
        {
            holder.MyMessage.setText(chatModel.getMsg());
            holder.OtherMessage.setVisibility(View.GONE);
            holder.MyMessage.setVisibility(View.VISIBLE);
        }else
        {
            holder.OtherMessage.setVisibility(View.VISIBLE);
            holder.MyMessage.setVisibility(View.GONE);
            holder.OtherMessage.setText(chatModel.getMsg());

        }

    }

    @Override
    public int getItemCount() {
        if(Message !=null){
            return Message.size();
        }
        return 0;
    }

    static class ChatHolder extends RecyclerView.ViewHolder{
        TextView MyMessage;
        TextView OtherMessage;

         public ChatHolder(@NonNull View itemView) {
             super(itemView);
             MyMessage=itemView.findViewById(R.id.MyMsg);
            OtherMessage=itemView.findViewById(R.id.OtherMsg);

         }
     }
}
