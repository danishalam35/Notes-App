package com.example.mysticky;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<Notes> userData = new ArrayList<>();

    private onItemClickListener listener;
    private onLongClickListener mListener;




    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

        Notes myData = userData.get(position);
        holder.textViewTitle.setText(myData.getTitle());
        holder.desc.setText(myData.getDesc());

    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void setNotes(List<Notes> notes) {
        this.userData = notes;
        notifyDataSetChanged();
    }



    public class UserHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle,desc;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.t1);
            desc = itemView.findViewById(R.id.t2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(userData.get(position));

                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos =getAdapterPosition();

                    if (mListener!=null)
                        mListener.onDeleteItem(userData.get(pos));
                    return false;
                }
            });
        }

    }


    public interface onItemClickListener {
        void onItemClick(Notes note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    public void onLongClickListn(onLongClickListener mListener){
        this.mListener =mListener;
    }
    /**
     * The interface Item click listener.
     */
    // parent activity will implement this method to respond to click events
    public interface onLongClickListener{
        void onDeleteItem(Notes userData);

    }
}
