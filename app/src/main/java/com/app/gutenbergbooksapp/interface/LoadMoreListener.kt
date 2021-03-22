package com.books.gutenberg.interfaces

/**
 * Load More content interface
 */
interface LoadMoreListener : RecyclerViewClickListener {

    /**
     * Method to load new items when user scrolls the recycler view till end
     */
    fun loadMoreItems()
}