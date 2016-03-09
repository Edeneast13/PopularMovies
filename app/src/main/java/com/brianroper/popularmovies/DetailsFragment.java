package com.brianroper.popularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

public class DetailsFragment extends Fragment {

    private String mTitle ="";
    private Bitmap mPoster;
    private String mDescription ="";
    private int mRating = 0;
    private Date mReleaseDate;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FetchDetailsTask detailTask = new FetchDetailsTask();
        detailTask.execute("http://api.themoviedb.org/3/movie/293660?api_key=a0a454fc960bf4f69fa0adf5e13161cf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

}
