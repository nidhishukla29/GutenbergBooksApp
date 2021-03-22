package com.app.gutenbergbooksapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.gutenbergbooksapp.R
import com.app.gutenbergbooksapp.model.BookTypeDataModel

import com.books.gutenberg.interfaces.RecyclerViewClickListener

import com.bumptech.glide.Glide

/**
 * Genre list adapter
 */
class GenreListAdapter(var recyclerViewClickListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<GenreListAdapter.GenreViewHolder>() {

    var genreList: List<BookTypeDataModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.type_list_item,
            parent, false
        )
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genreList.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindData(genreList[position], position)
    }

    /**
     * update genre list data
     */
    fun updateData(genreList: List<BookTypeDataModel>) {
        this.genreList = genreList
        notifyDataSetChanged()
    }


    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvGenreName: TextView = itemView.findViewById(R.id.tvGenreName)
        var ivGenreLogo: ImageView = itemView.findViewById(R.id.ivGenreLogo)
        var buttonNext: ImageView = itemView.findViewById(R.id.buttonNext)

        fun bindData(genreDataModel: BookTypeDataModel, position: Int) {
            tvGenreName.text = genreDataModel.bookTypeName.toUpperCase()

            Glide.with(itemView.context).load(
                itemView.context.resources.getIdentifier(
                    genreDataModel.imagePath,
                    "drawable",
                    itemView.context.packageName
                )
            ).into(ivGenreLogo)

            buttonNext.setOnClickListener { recyclerViewClickListener.onClick(position = position) }

        }

    }
}


