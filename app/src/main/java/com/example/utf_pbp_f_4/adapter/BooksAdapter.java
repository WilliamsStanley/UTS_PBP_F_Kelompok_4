package com.example.utf_pbp_f_4.adapter;

import static com.example.utf_pbp_f_4.MyApplication.CHANNEL_2_ID;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.utf_pbp_f_4.R;
import com.example.utf_pbp_f_4.database.DatabaseClient;
import com.example.utf_pbp_f_4.model.Books;
import com.example.utf_pbp_f_4.preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private List<Books> booksList;
    private Context context;
    private DatabaseClient databaseClient;
    private UserPreferences userPreferences;
    private NotificationManagerCompat notificationManager;

    public BooksAdapter(List<Books> booksList, Context context) {
        this.booksList = booksList;
        this.context = context;
        this.userPreferences = new UserPreferences(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooks,parent,false);

        notificationManager = NotificationManagerCompat.from(context);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.tvISBN.setText(books.getISBN());
        holder.tvNama_buku.setText(books.getNama_buku());
        holder.tvGenre.setText(books.getGenre());
        Glide.with(context).load(books.getImgURL()).into(holder.tvImgURL);
        databaseClient = DatabaseClient.getInstance(context);

        holder.btnKembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseClient.getDatabase()
                        .todoDao()
                        .deleteBooks(books);
                Toast.makeText(context, "Buku "+books.getNama_buku()+" dikembalikan", Toast.LENGTH_SHORT).show();
                booksList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Pengembalian")
                        .setContentText("Berhasil mengembalikan buku")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(2, notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvISBN, tvNama_buku, tvGenre;
        private ImageView tvImgURL;
        private MaterialButton btnKembalikan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvISBN = itemView.findViewById(R.id.tvISBN);
            tvNama_buku = itemView.findViewById(R.id.tvNamaBuku);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvImgURL = itemView.findViewById(R.id.tvImgURL);
            btnKembalikan = itemView.findViewById(R.id.btnKembalikan);

        }
    }
}
