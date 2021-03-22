package com.books.gutenberg.interfaces

/**
 * Interface to invoke Recycler view item click
 */
interface RecyclerViewClickListener {

    /**
     * Method called on recycler view item click
     */
    fun onClick(position: Int)
}