package com.example.ping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ping.Models.Message;
import com.example.ping.R;
import com.example.ping.databinding.RecieveMessageBinding;
import com.example.ping.databinding.SendMessageBinding;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MessagesAdaptor extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;
    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    public MessagesAdaptor(Context context, ArrayList<Message> messages){
    this.context = context;
    this.messages = messages;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.send_message, parent, false);
            return  new SendViewHolder(view);

        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.recieve_message, parent, false);
            return  new ReceiverViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_SENT;
        }else {
            return ITEM_RECEIVE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
    Message message = messages.get(position);
        if (holder.getClass() == SendViewHolder.class){
        SendViewHolder viewHolder = (SendViewHolder)holder;
       viewHolder.binding.message.setText(message.getMessage());
         }else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            viewHolder.binding.message.setText(message.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SendViewHolder extends RecyclerView.ViewHolder {

    SendMessageBinding binding;

    public SendViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        binding = SendMessageBinding.bind(itemView);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        RecieveMessageBinding binding;
        public ReceiverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = RecieveMessageBinding.bind(itemView);
        }
    }
}