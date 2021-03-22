package com.books.gutenberg.interfaces


/**
 * Genre list interface to pass control and data from presenter to activity
 */
interface BookTypeView {

    /**
     * method to render genre list item
     */
    fun renderView(typeList: List<BookTypeView>)
}