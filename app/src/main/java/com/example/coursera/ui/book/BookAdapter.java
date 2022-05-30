package com.example.coursera.ui.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MyBusiness.MyApp.R;
import com.MyBusiness.MyApp.databinding.RowItemBinding;
import com.example.coursera.model.Book;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.ViewHolder> {
//    ArrayList<Book> mainModels;
    Context context;

    public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options){
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        RowItemBinding rowItemBinding = RowItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(rowItemBinding.getRoot());
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.imageView.setImageResource(mainModels.get(position).getLangLogo());
//
//        holder.textView.setText(mainModels.get(position).getLangName());
//    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Book model) {
//        holder.imageView.setImageResource(mainModels.get(position).gettitle());
        holder.textView1.setText(model.getTitle());
        holder.textView2.setText(model.getDescription());
    }
//
//    @Override
//    public int getItemCount() {
//        return mainModels.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowItemBinding rowItemBinding;
//        ImageView imageView;
        TextView textView1, textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
        }
    }
}
