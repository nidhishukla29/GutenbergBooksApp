package com.app.gutenbergbooksapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.app.gutenbergbooksapp.R
import com.app.gutenbergbooksapp.util.Constants
import com.books.gutenberg.interfaces.LoadMoreListener
import com.app.gutenbergbooksapp.model.Result


import com.bumptech.glide.Glide

/**
 * Adapter to display books list as per genre
 */
class BookListAdapter(var loadMoreListener: LoadMoreListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var booksList: MutableList<Result> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType ==Constants. VIEW_TYPE_ITEM) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(
                    R.layout.books_list_item,
                    parent, false
                )
            BookListViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent, false
                )
            LoadingViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        if (booksList.size == 0) {
            return 1
        }
        // Add extra view to show the loader view
        return booksList.size.plus(1)
    }

    override fun getItemViewType(position: Int) =
        if (position == booksList.size) Constants. VIEW_TYPE_LOADING else Constants. VIEW_TYPE_ITEM

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookListViewHolder) {
            holder.bindData(booksList[position], position)
        } else if (holder is LoadingViewHolder) {
            holder.showProgressBar()
        }
    }

    /**
     * Set Recycler view item and notify adapter
     */
    fun setItems(
        bookList: List<Result>,
        requestType: Constants.REQUEST_TYPE) {
        if (requestType == Constants.REQUEST_TYPE.SEARCH_TYPE) {
            this.booksList.clear()
        }
        this.booksList.addAll(bookList)
        notifyDataSetChanged()
    }

    inner class BookListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        var ivBookLogo: ImageView = itemView.findViewById(R.id.ivBookLogo)
        var tvBookAuthor: TextView = itemView.findViewById(R.id.tvBookAuthor)

        fun bindData(bookData: Result, position: Int) {
            tvBookTitle.text = bookData.title
            if (bookData.authorsList.isNotEmpty()) {
                tvBookAuthor.text = bookData.authorsList[0].name
            }

            if (bookData.formats?.imageJpeg != null) {
                Glide.with(itemView.context).load(
                    bookData.formats?.imageJpeg
                ).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(ivBookLogo)
            }

            itemView.setOnClickListener { loadMoreListener.onClick(position) }

        }
    }

    inner class LoadingViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        fun showProgressBar() {
            progressBar.visibility = View.VISIBLE
            if (booksList.size > 1) {
//                loadMoreListener.loadMoreItems()
                progressBar.visibility = View.GONE
            }

        }

    }
}