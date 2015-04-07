package com.ll.android.simplemovieprovider;

import com.ll.android.simplemovieprovider.fragment.MovieDetailFragment;
import com.ll.android.simplemovieprovider.fragment.SearchResultFragment;
import com.ll.android.simplemovieprovider.fragment.SearchResultFragment.ISearchResultChangeListener;
import com.ll.android.simplemovieprovider.model.Movie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;


public class MoviePosterScreen extends FragmentActivity implements ISearchResultChangeListener{
	private SearchResultFragment mSearchFragment;
	private MovieDetailFragment mDetailFragment;
	private FrameLayout mSearchContainer; // search fragment container;
	private FrameLayout mDetailContainer; // detail fragment container
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_layout);
		init();
		if(null == savedInstanceState) {
			mSearchFragment = SearchResultFragment.newInstance();
			getSupportFragmentManager().beginTransaction().add(
					mSearchContainer.getId(), mSearchFragment, "SearchResultFragment").commit();
		} 
			 
    }
    
    private void init() {
		mSearchContainer = (FrameLayout) findViewById(R.id.search_results_container);
		mDetailContainer = (FrameLayout) findViewById(R.id.detailed_info_container);
	}

    /*
     * Update the detailed Fragment with selected result
     */
	@Override
	public void notifySearchResult(Movie movie) {
		String title = movie.getTitle();
		String plot = movie.getPlot();
		String poster = movie.getPoster();
		if(null == mDetailFragment) {
			mDetailFragment = MovieDetailFragment.newInstance(title, plot, poster);
			getSupportFragmentManager().beginTransaction().replace(
					mDetailContainer.getId(), mDetailFragment).commit();
		} else {
			mDetailFragment.update(movie);
		}
		
	}

	
}
