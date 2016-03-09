package com.brianroper.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieFragment extends Fragment{

    private ImageView iv;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> movieIdArray = new ArrayList<String>();
        ArrayList<String> posterUrlArray = new ArrayList<String>();
        ArrayList<Movie> movieObjectArray = new ArrayList<Movie>();
        String posterUrl = "";
        String movieId = "";

        try {

            final String BASE_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
            final String API_KEY_PARAM = "?api_key=a0a454fc960bf4f69fa0adf5e13161cf";
            String fullMovieUrl = BASE_MOVIE_URL+movieId+API_KEY_PARAM;

            /* To DO: finish implementing FetchPosterTask and populate to object array */
            //takes the html poster url and uses Async Task to download the image
            //FetchPosterTask posterTask = new FetchPosterTask();
            //posterTask.execute(posterUrl);

            //retrieves html data from themoviedb.org and sets it to the htmlData variable
            FetchMovieTask movieTask = new FetchMovieTask();
            String htmlData = movieTask.execute("https://www.themoviedb.org/movie").get();

            //splits the webpage source code to ignore unnecessary code
            String[] splitHtmlData = htmlData.split("<div class=\"pagination\">");

            /*To Do: Fix page source split to properly populate movie id array list without double id's */
            //picks out movie id's from web page source code
            Pattern idPattern = Pattern.compile("id=\"movie_(.*?)\"");
            Matcher idMatcher = idPattern.matcher(splitHtmlData[0]);

            while(idMatcher.find()){

                movieIdArray.add(idMatcher.group(1));
            }

            /*for (int i = 0; i < movieIdArray.size(); i++) {

                System.out.println(movieIdArray.get(i).toString());
                //how to increment i by 2??
            }*/
            Pattern posterUrlPattern = Pattern.compile("srcset=\"(.*?) 1x");
            Matcher posterUrlMatcher = posterUrlPattern.matcher(splitHtmlData[0]);

            while(posterUrlMatcher.find()){

                posterUrlArray.add(posterUrlMatcher.group(1));
            }

            /*for (int i = 0; i < posterUrlArray.size(); i++) {

                System.out.println(posterUrlArray.get(i).toString());
            }*/

            for (int i = 0; i < posterUrlArray.size(); i++) {

                Movie movie = new Movie();
                movieId = movieIdArray.get(i);
                posterUrl = posterUrlArray.get(i);
                movie.setPosterUrl(posterUrl);
                movie.setId(movieId);
                Log.i("Movie", "Id: " + movie.getId().toString() + " " + "URL: " + movie.getPosterUrl().toString());
            }
            /* TO DO: create for loop to populate the gridview with movie posters from movie objects */
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        iv = (ImageView) v.findViewById(R.id.movie_pop1);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.movie_pop1:

                        Intent i = new Intent(getActivity(), DetailsFragment.class);
                        startActivity(i);
                }
            }
        });

        return v;
    }
}

