package com.app.gutenbergbooksapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result{
    Integer id;
    String title;
    @SerializedName("authors")
    List<Author> authorsList = new ArrayList();

    @SerializedName("subjects")
    List<String> subjectsList;

    @SerializedName("bookshelves")
    List<String> bookshelvesList;

    @SerializedName("languages")
    List<String> languagesList;

    Boolean copyright;

    @SerializedName("media_type")
    String mediaType;

    Formats formats = null;
    @SerializedName("download_count")
    Integer downloadCount = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Author> authorsList) {
        this.authorsList = authorsList;
    }

    public List<String> getSubjectsList() {
        return subjectsList;
    }

    public void setSubjectsList(List<String> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public List<String> getBookshelvesList() {
        return bookshelvesList;
    }

    public void setBookshelvesList(List<String> bookshelvesList) {
        this.bookshelvesList = bookshelvesList;
    }

    public List<String> getLanguagesList() {
        return languagesList;
    }

    public void setLanguagesList(List<String> languagesList) {
        this.languagesList = languagesList;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}

