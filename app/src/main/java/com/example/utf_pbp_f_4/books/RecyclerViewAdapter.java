package com.example.utf_pbp_f_4.books;

import static com.example.utf_pbp_f_4.MyApplication.CHANNEL_1_ID;

import android.app.Notification;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utf_pbp_f_4.R;
import com.example.utf_pbp_f_4.database.DatabaseClient;
import com.example.utf_pbp_f_4.databinding.ActivityRecyclerViewAdapterBinding;
import com.example.utf_pbp_f_4.model.Books;
import com.example.utf_pbp_f_4.preferences.UserPreferences;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    List<DataBuku> BukuList;
    private DatabaseClient databaseClient;
    private Context context;
    private List<Books> booksList;
    private UserPreferences userPreferences;
    private NotificationManagerCompat notificationManager;

    public RecyclerViewAdapter(List<DataBuku> BukuList, Context context){
        this.BukuList = BukuList;
        this.context = context;
        this.userPreferences = new UserPreferences(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ActivityRecyclerViewAdapterBinding activityRecyclerViewAdapterBinding = ActivityRecyclerViewAdapterBinding.inflate(layoutInflater, parent, false);

        notificationManager = NotificationManagerCompat.from(context);

        return new viewHolder(activityRecyclerViewAdapterBinding);
    }



    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DataBuku dataBuku = BukuList.get(position);

        holder.activityRecyclerViewAdapterBinding.setBuku(dataBuku);
        holder.activityRecyclerViewAdapterBinding.executePendingBindings();

        holder.activityRecyclerViewAdapterBinding.btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert(dataBuku.getISBN(),
                        dataBuku.getNama_buku(),
                        dataBuku.getGenre(),
                        dataBuku.getImgURL(),
                        userPreferences.getUserLogin().getId());

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Peminjaman")
                        .setContentText("Berhasil meminjam buku")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1, notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BukuList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ActivityRecyclerViewAdapterBinding activityRecyclerViewAdapterBinding;

        public viewHolder(@NonNull ActivityRecyclerViewAdapterBinding activityRecyclerViewAdapterBinding) {
            super(activityRecyclerViewAdapterBinding.getRoot());
            this.activityRecyclerViewAdapterBinding = activityRecyclerViewAdapterBinding;

            databaseClient = DatabaseClient.getInstance(context);
        }
    }

    private void insert(String ISBN, String nama_buku ,String genre, String imgURL, int user_id){

        class InsertBooks extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Books books = new Books();
                books.setISBN(ISBN);
                books.setNama_buku(nama_buku);
                books.setGenre(genre);
                books.setImgURL(imgURL);
                books.setUser_id(user_id);

                DatabaseClient.getInstance(context)
                        .getDatabase()
                        .todoDao()
                        .insertBooks(books);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(context, "Berhasil meminjam buku", Toast.LENGTH_SHORT).show();
            }

        }
        InsertBooks insertBooks = new InsertBooks();
        insertBooks.execute();
    }
}
