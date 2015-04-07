package com.ll.android.simplemovieprovider.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ll.android.simplemovieprovider.R;
import com.ll.android.simplemovieprovider.model.Movie;

public class CustomSearchResultAdapter extends BaseAdapter {

	private List<Movie> mMovieList;
	private ViewHolder mCachedView;
	private LayoutInflater mInflater;
	
	public CustomSearchResultAdapter(Context context, List<Movie> movies) {
		this.mMovieList = movies;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mMovieList.size();
	}

	@Override
	public Movie getItem(int position) {
		return mMovieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View root = convertView;
		if(root == null) {
			root = mInflater.inflate(R.layout.list_item_layout, null);
			mCachedView = new ViewHolder();
			mCachedView.title = (TextView) root.findViewById(R.id.list_item_title);
			root.setTag(mCachedView);
		} else {
			mCachedView = (ViewHolder) root.getTag();
		}
		Movie movie = getItem(position);
		mCachedView.title.setText(movie.getTitle());
		return null;
	}

	class ViewHolder {
		TextView title;
	}
}
