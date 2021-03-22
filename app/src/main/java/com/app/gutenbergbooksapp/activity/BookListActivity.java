package com.app.gutenbergbooksapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.gutenbergbooksapp.R;
import com.app.gutenbergbooksapp.adaptor.BookListAdapter;
import com.app.gutenbergbooksapp.databinding.ActivityBookListBinding;
import com.app.gutenbergbooksapp.model.BookDataModel;
import com.app.gutenbergbooksapp.model.Result;
import com.app.gutenbergbooksapp.util.Constants;
import com.app.gutenbergbooksapp.viewModel.BookListViewModel;
import com.books.gutenberg.interfaces.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class BookListActivity extends AppCompatActivity implements LoadMoreListener {

    ActivityBookListBinding binding;
    String topicName="";
    BookListAdapter bookListAdapter;
    boolean loading = false;
    boolean lastPage = false;
    List<Result> booksList=new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    AlertDialog dialog;
    BookListViewModel model;
    int currentVisiblePosition,lastVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setAppBarTitle();
        initViews();
    }
    private void setAppBarTitle() {
       ActionBar actionBar = getSupportActionBar();
        // retrieving topic name passed as extra
        topicName = getIntent().getStringExtra(Constants.KEY_TOPIC_NAME) ;
        actionBar.setTitle(topicName);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
              onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rvBooksData.setLayoutManager(gridLayoutManager);

        bookListAdapter = new BookListAdapter(this);
        binding.rvBooksData.setAdapter(bookListAdapter);

         model = ViewModelProviders.of(this).get(BookListViewModel.class);

        model.getBooks(topicName).observe(this, getBookObserver());
        setSearcview();
        initScrollListener();

    }
private void setSearcview(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applySearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applySearch(newText);
                return true;
            }
        });
}

private Observer<BookDataModel> getBookObserver(){
        return new Observer<BookDataModel>() {
            @Override
            public void onChanged(@Nullable BookDataModel bookDataModel) {

                loading = false;
                if (bookDataModel != null && bookListAdapter != null && !bookDataModel.getResultsList().isEmpty()) {
                    booksList.clear();
                    booksList.addAll(bookDataModel.getResultsList());
                    bookListAdapter.setItems(booksList, Constants.REQUEST_TYPE.TOPIC_TYPE);
                }
            }
        };
    }

private void applySearch(String searchKeyT){
    model.searchBook(topicName,searchKeyT)
            .observe(this, new Observer<BookDataModel>() {
        @Override
        public void onChanged(@Nullable BookDataModel bookDataModel) {

            loading = false;
            if (bookDataModel != null && bookListAdapter != null && !bookDataModel.getResultsList().isEmpty()) {
                booksList.clear();
                booksList.addAll(bookDataModel.getResultsList());
                bookListAdapter.setItems(booksList, Constants.REQUEST_TYPE.SEARCH_TYPE);
            }
        }
    });
}
    @Override
    public void loadMoreItems() {
        model.loadData(Constants.PAGE_TYPE.NEXT,topicName).observe(BookListActivity.this,getBookObserver());
    }

    @Override
    public void onClick(int position) {
        showBook(position);
    }

    private void showBook(int position){
        String[] listItems = {Constants.BOOK_VIEW_HTML,Constants. BOOK_VIEW_PDF, Constants.BOOK_VIEW_TXT,Constants. BOOK_VIEW_ZIP};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose");

        builder.setItems(listItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToUrl(position,listItems[which]);
            }
        });

         dialog = builder.create();
        dialog.show();
    }

    private void goToUrl(int position, String optionSelected) {
        String url = model.getUrlBasedOnOptionSelected(
                booksList.get(position).getFormats(),
                optionSelected
        );
        if (url != null && !TextUtils.isEmpty(url)) {
            Uri uriUrl  = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } else {
            Toast.makeText(this, getString(R.string.no_version_available_error_msg), Toast.LENGTH_LONG).show();
        }
    }

    private void initScrollListener() {
        binding.rvBooksData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {
                    currentVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading) {
                        if (currentVisiblePosition < lastVisiblePosition)
                            model.loadData(Constants.PAGE_TYPE.PREVIOUS,topicName).observe(BookListActivity.this,getBookObserver());
                        else
                            model.loadData(Constants.PAGE_TYPE.NEXT,topicName).observe(BookListActivity.this,getBookObserver());;
                        loading=true;
                    }

                    lastVisiblePosition = currentVisiblePosition;
                }

            }
        });


    }
}