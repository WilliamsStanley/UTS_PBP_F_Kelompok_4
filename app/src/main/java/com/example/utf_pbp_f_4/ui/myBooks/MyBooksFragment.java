package com.example.utf_pbp_f_4.ui.myBooks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utf_pbp_f_4.adapter.BooksAdapter;
import com.example.utf_pbp_f_4.database.DatabaseClient;
import com.example.utf_pbp_f_4.model.Books;
import com.example.utf_pbp_f_4.preferences.UserPreferences;
import com.example.utf_pbp_f_4.R;

import java.util.ArrayList;
import java.util.List;

public class MyBooksFragment extends Fragment {
    private RecyclerView rv_booksList;
    private UserPreferences userPreferences;
    private List<Books> booksList;
    private BooksAdapter booksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_mybooks, container, false);
        rv_booksList = root.findViewById(R.id.rv_booksList);

        userPreferences = new UserPreferences(getContext());

        rv_booksList.setLayoutManager(new LinearLayoutManager(getContext()));

        getBooks();

        booksList = new ArrayList<>();

        return root;
    }

    private void getBooks() {
        class GetBooks extends AsyncTask<Void, Void, List<Books>> {

            @Override
            protected List<Books> doInBackground(Void... voids) {
                List<Books> todoList = DatabaseClient.getInstance(getContext())
                        .getDatabase()
                        .todoDao()
                        .getBooksByUserId(userPreferences.getUserLogin().getId());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Books> books) {
                super.onPostExecute(books);
                booksAdapter = new BooksAdapter(books, getContext());
                rv_booksList.setAdapter(booksAdapter);
            }
        }

        GetBooks getBooks = new GetBooks();
        getBooks.execute();
    }
}
