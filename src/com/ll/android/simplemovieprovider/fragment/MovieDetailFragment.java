package com.ll.android.simplemovieprovider.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ll.android.simplemovieprovider.R;
import com.ll.android.simplemovieprovider.model.Movie;

public class MovieDetailFragment extends Fragment {
	
	private TextView mMovieTitle;
	private TextView mMoviePlot;
	private ImageView mPosterView;
	private String mTitle;
	private String mPlot;
	private String mPoster;
	private static final String MOVIE_TITLE_KEY = "movie_title_key";
	private static final String MOVIE_PLOT_KEY = "movie_plot_key";
	private static final String MOVIE_POSTER_KEY = "movie_poster_key";
	
	public static MovieDetailFragment newInstance(String title, String plot, String poster) {
		MovieDetailFragment frag = new MovieDetailFragment();
		Bundle args = new Bundle();
		args.putString(MOVIE_TITLE_KEY, title);
		args.putString(MOVIE_PLOT_KEY, plot);
		args.putString(MOVIE_POSTER_KEY, plot);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if(args != null) {
			mTitle = args.getString(MOVIE_TITLE_KEY);
			mPlot = args.getString(MOVIE_PLOT_KEY);
			mPoster = args.getString(MOVIE_POSTER_KEY);
		}
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.movie_detail_fragment, container);
		mMovieTitle = (TextView) root.findViewById(R.id.movie_title_label);
		mMoviePlot = (TextView) root.findViewById(R.id.movie_description_label);
		mPosterView = (ImageView) root.findViewById(R.id.movie_poster);
		init();
		return root;
	}
	
	private void init() {
		updateView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void update(Movie movie) {
		mTitle = movie.getTitle();
		mPlot = movie.getPlot();
		mPoster = movie.getPoster();
		updateView();
	}
	
	private void updateView() {
		if(mTitle != null) {
			mMovieTitle.setText(mTitle);
		}
		
		if(mPlot != null) {
			mMoviePlot.setText(mPlot);
		}
		
		setPosterBitmap();
	}
	
	private void setPosterBitmap() {
		if(mPoster == null) {
			return;
		}
		Bitmap bmp = BitmapFactory.decodeFile(mPoster);
		if(bmp != null) {
			mPosterView.setImageBitmap(bmp);
		}
	}
	
}
