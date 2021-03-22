package com.app.gutenbergbooksapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.gutenbergbooksapp.R;
import com.app.gutenbergbooksapp.adaptor.GenreListAdapter;
import com.app.gutenbergbooksapp.databinding.ActivityBookTypeBinding;
import com.app.gutenbergbooksapp.model.BookTypeDataModel;
import com.app.gutenbergbooksapp.util.Constants;
import com.books.gutenberg.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is UI class that contain list of book type.
 */
public class BookTypeActivity extends AppCompatActivity implements RecyclerViewClickListener {

    ActivityBookTypeBinding binding;
    List<BookTypeDataModel> bookTypeDataModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookTypeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        prepareGenreList(Arrays.asList(getResources().getStringArray(R.array.genre_array)));
        initUI();
    }

    void initUI(){
        getSupportActionBar().hide();
        binding.rvGenre.setHasFixedSize(true);
        binding.rvGenre.setLayoutManager(new LinearLayoutManager(this));
//
        GenreListAdapter adapter = new GenreListAdapter(this);
        binding.rvGenre.setAdapter(adapter);
        adapter.updateData(bookTypeDataModelList);
    }

    @Override
    public void onClick(int position) {
        Intent intent= new Intent(this, BookListActivity.class);
        // passing in genre name as extra
        intent.putExtra(Constants.KEY_TOPIC_NAME, bookTypeDataModelList.get(position).getBookTypeName());
        startActivity(intent);
 }
    private void prepareGenreList(List<String>  typeArray) {
        bookTypeDataModelList= new ArrayList<BookTypeDataModel>();
        for (String book : typeArray) {
            bookTypeDataModelList.add(new BookTypeDataModel(book, "ic_" + book.toLowerCase()));
        }


    }
}