package com.app.gutenbergbooksapp.viewModel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gutenbergbooksapp.model.BookDataModel;
import com.app.gutenbergbooksapp.model.Formats;
import com.app.gutenbergbooksapp.model.Result;
import com.app.gutenbergbooksapp.network.ApiInterface;
import com.app.gutenbergbooksapp.network.RetrofitClientInstance;
import com.app.gutenbergbooksapp.util.Constants;

import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is view model class.
 */
public class BookListViewModel extends ViewModel {

    private MutableLiveData<BookDataModel> bookLiveData;
    private int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int lastVisiblePage  = PAGE_START;

    public BookListViewModel(){
        bookLiveData = new MutableLiveData<BookDataModel>();
    }

    public LiveData<BookDataModel> getBooks(String topic) {

            bookLiveData = requestBook(topic,PAGE_START);

        return bookLiveData;
    }

    /**
     * Get book data from server
     * @param topic
     * @param page
     * @return
     */
    public MutableLiveData<BookDataModel> requestBook(String topic,int page) {
        final MutableLiveData<BookDataModel> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiService =
                new RetrofitClientInstance().getRetrofitInstance().create(ApiInterface.class);

        apiService.getTopicWiseBookList(topic,page).enqueue(new Callback<BookDataModel>() {
            @Override
            public void onResponse(Call<BookDataModel> call, Response<BookDataModel> response) {
                Log.e("Respomse", " response = "+response );

                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e("Success", " response="+response.body() );

                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BookDataModel> call, Throwable t) {
                Log.e("Response Failed", "onFailure" + call.toString());
            }
        });

        return mutableLiveData;
    }

    /**
     * Get data fo search request.
     * @param topic
     * @param search
     * @return
     */

    public MutableLiveData<BookDataModel> searchBook(String topic,String search) {
        final MutableLiveData<BookDataModel> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiService =
                new RetrofitClientInstance().getRetrofitInstance().create(ApiInterface.class);

        apiService.getSearchWiseBookList(search,topic).enqueue(new Callback<BookDataModel>() {
            @Override
            public void onResponse(Call<BookDataModel> call, Response<BookDataModel> response) {
                Log.e("Respomse", " response = "+response );

                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e("Success", " response="+response.body() );
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BookDataModel> call, Throwable t) {
                Log.e("Response Failed", "onFailure" + call.toString());
            }
        });

        return mutableLiveData;
    }

    public MutableLiveData<BookDataModel> loadData( Constants.PAGE_TYPE pageType,String topicName){

            if(pageType==Constants.PAGE_TYPE.NEXT){
                Log.d("Next",bookLiveData.getValue().getNext());
                if(bookLiveData.getValue().getNext()!=null&&bookLiveData.getValue().getNext().trim().length()!=0){
                  bookLiveData=  requestBook(topicName,getpageNumber(bookLiveData.getValue().getNext()));
                }

            }else if(pageType==Constants.PAGE_TYPE.PREVIOUS){
                if(bookLiveData.getValue().getPrevious()!=null&&bookLiveData.getValue().getPrevious().trim().length()!=0){
                    Log.d("previous",bookLiveData.getValue().getPrevious());
                    bookLiveData= requestBook(topicName,getpageNumber(bookLiveData.getValue().getNext()));
                    return bookLiveData;
                }
                currentPage=PAGE_START;
                bookLiveData=requestBook(topicName,currentPage);
                Log.d("previous",1+"");

            }
            return bookLiveData;
    }

    public  int getpageNumber(String urlString) {
        if(urlString!=null) {
            URL url = null;
            try {
                url = new URL(urlString);
            if(url!=null) {
                Map<String, String> query_pairs = new LinkedHashMap<String, String>();
                String query = url.getQuery();
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                }
                return Integer.parseInt(query_pairs.get("page"));
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return PAGE_START;
    }

    /**
     * Method to get url based on option coosen by user to open in web browser
     */
    public String getUrlBasedOnOptionSelected(Formats formats, String optionSelected ) {
        String f="";
         switch (optionSelected) {
             case "HTML" :
               if(formats.getTextHtml()!=null&&!formats.getTextHtml().isEmpty()){
                   f=formats.getTextHtml();
               } else if(formats.getTextHtmlCharsetIso88591()!=null&&!formats.getTextHtmlCharsetIso88591().isEmpty()){
                 f=formats.getTextHtmlCharsetIso88591();
             }else if(formats.getTextHtmlCharsetUsAscii()!=null&&!formats.getTextHtmlCharsetUsAscii().isEmpty()){
                   f=formats.getTextHtmlCharsetUsAscii();
               }else {
                   f=formats.getTextHtmlCharsetUtf8();
               }

                break;
             case  "PDF":
                 if(formats.getApplicationPdf()!=null&&!formats.getApplicationPdf().isEmpty()){
                     f=formats.getApplicationPdf();
                 } else if(formats.getApplicationRdfXml()!=null&&!formats.getApplicationRdfXml().isEmpty()){
                     f=formats.getApplicationRdfXml();
                 }else {
                     f=formats.getApplicationXMobipocketEbook();
                 }

                 break;
//
             case   "TXT":
                 if(formats.getTextPlain()!=null&&!formats.getTextPlain().isEmpty()){
                     f=formats.getTextPlain();
                 } else if(formats.getTextPlainCharsetIso88591()!=null&&!formats.getTextPlainCharsetIso88591().isEmpty()){
                     f=formats.getTextPlainCharsetIso88591();
                 }else if(formats.getTextPlainCharsetUsAscii()!=null&&!formats.getTextPlainCharsetUsAscii().isEmpty()){
                     f=formats.getTextPlainCharsetUsAscii();
                 }
                 else if(formats.getTextPlainCharsetUtf8()!=null&&!formats.getTextPlainCharsetUtf8().isEmpty()){
                     f=formats.getTextPlainCharsetUtf8();
                 }
                 else {
                     f=formats.getTextRtf();
                 }

                 break;

             case "ZIP" :
                 if(formats.getApplicationZip()!=null&&!formats.getApplicationZip().isEmpty()){
                     f=formats.getApplicationZip();
                 } else {
                     f=formats.getApplicationEpubZip();
                 }

                 break;
             default:
                 f=formats.getApplicationPrsTex();


        }

        return f;
    }
}
