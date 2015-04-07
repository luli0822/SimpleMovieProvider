package com.ll.android.simplemovieprovider.fragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.ll.android.simplemovieprovider.R;
import com.ll.android.simplemovieprovider.adapter.CustomSearchResultAdapter;
import com.ll.android.simplemovieprovider.model.Movie;
import com.ll.android.simplemovieprovider.widget.ClearableEditText;
import com.ll.android.simplemovieprovider.widget.CustomListView;

public class SearchResultFragment extends Fragment {
	private static final String TAG = "SearchResultFragment";
	private ProgressBar mProgressBar;
	private CustomListView mListView; 
	private ClearableEditText mEditText;
	private CustomSearchResultAdapter mAdapter;
	private Context mContext;
	private ISearchResultChangeListener mListener; // interface between fragments and activity 
	private SearchContentTask mSearchTask; // background search task
	
	public static SearchResultFragment newInstance() {
		SearchResultFragment frag = new SearchResultFragment();
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		setRetainInstance(true);
	}
	
	public interface ISearchResultChangeListener {
		void notifySearchResult(Movie movie);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (ISearchResultChangeListener) activity;
		} catch(Exception e) {			
				Log.e(TAG, "ClassCastException: the activity " + activity + 
					" should implement interface " + mListener, e);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.search_result_fragment, container, false);
		mProgressBar = (ProgressBar) root.findViewById(R.id.loading_progress_bar);	
		mListView = (CustomListView) root.findViewById(R.id.result_list);
		mEditText = (ClearableEditText) root.findViewById(R.id.search_input_field);
		init();
		return root;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private void init() {
		
		mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            	mEditText.manageClearButton();
                StringBuffer strB = new StringBuffer(arg0);
                if(mSearchTask != null) {
                	mSearchTask.cancel(true);
                	mSearchTask = null;
                }
               
            	mSearchTask = new SearchContentTask();
            	mSearchTask.execute(strB.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
	}
	
	private void showLoadingScreen() {
		if(View.VISIBLE != mProgressBar.getVisibility()) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}
	
	private void dismissLoadingScreen() {
		if(View.GONE != mProgressBar.getVisibility()) {
			mProgressBar.setVisibility(View.GONE);
		}
	}
	
	/*
	 * 
	 * This class is dealing with the background searching task
	 * and returns results if successful.
	 * For querying multiple results from iMDB API, the online thread
	 * 'http://stackoverflow.com/questions/1966503/does-imdb-provide-an-api/7744369#7744369'
	 * refers to this API 'http://sg.media-imdb.com/suggests/a/aa.json' as the end point,
	 * search titles containing 'a' for example. But this failed while developing. And hitting
	 * to "http://www.omdbapi.com/?t=" + query, the status code is 503.
	 *
	 */
	class SearchContentTask extends AsyncTask<String, Void, List<Movie>> {
		
		private static final String TAG = "SearchContentTask";
		public static final String HTTP_APPLICATION_JSON_VALUE = "application/json";
		public static final String ACCEPT_HEADER_NAME = "Accept";
	    public static final String HTTP_CONTENT_TYPE_VALUE = "Content-Type";
		public static final String mServerBase = "http://sg.media-imdb.com/suggests/";
		static final String mServerSingle = "http://www.omdbapi.com/?";
		//http://www.omdbapi.com/?t=*keyword*&y=&plot=full&r=json
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingScreen();
		}

		@Override
		protected List<Movie> doInBackground(String... params) {
			String query = params[0];
			List<Movie> result = null;
			if(!"".equals(query)) {
//				String server = mServerBase + query + "/" + query + ".json";
				String server = mServerSingle + "t=*" + query + "*";
				result = doGet(server);
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<Movie> result) {
			super.onPostExecute(result);
			if(result != null) {
				dismissLoadingScreen();
				mAdapter = (CustomSearchResultAdapter) mListView.getAdapter();
				if(mAdapter == null) {
					mAdapter = new CustomSearchResultAdapter(mContext, result);
					mListView.setAdapter(mAdapter);
				}
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Movie movie = mAdapter.getItem(position);
						if(movie != null) {
							mListener.notifySearchResult(movie); // update detail fragment with relevant info
						}
					}
				});
			}
		}
		
		/**
		 * Fetch a list of Movie
		 * @param url
		 * @return
		 */
		private List<Movie> doGet(String url) {
			HttpURLConnection conn = null;		
	        List<Movie> result = null;
	        InputStream in;
	        try {
	        	conn = (HttpURLConnection) new URL(url).openConnection();
	        	conn.setRequestMethod("GET");
	            conn.addRequestProperty(ACCEPT_HEADER_NAME, HTTP_APPLICATION_JSON_VALUE);
	            conn.addRequestProperty(HTTP_CONTENT_TYPE_VALUE,
	                    HTTP_APPLICATION_JSON_VALUE + ";charset=UTF-8");
	            int respCode = conn.getResponseCode();
	            if(HttpURLConnection.HTTP_OK == respCode) {
	            	result = new ArrayList<Movie>();
	                in = new BufferedInputStream(conn.getInputStream());
	                String jsonData = new String(parseInputStream(in));
	                Movie movie = parseJsonData(jsonData);
	                result.add(movie);
	                Log.d(TAG, "doGet() response result: " + result);
	                in.close();
	            }
	        } catch (Exception e) {
	            Log.e(TAG, "doGet() caught exception!", e);
	        } finally {
	            if(conn != null) {
	                conn.disconnect();
	            }
	        }    
	        return result;
		}
		
		/**
		 * Parse json response while typing any character
		 * @param result
		 * @return an instance of Movie
		 * @throws JSONException
		 */
		private Movie parseJsonData(String result) throws JSONException {
			JSONObject json = new JSONObject(result);
			String title = json.getString("Title");
			int year = json.getInt("Year");
			String genre = json.getString("Genre");
			String plot = json.getString("Plot");
			String poster = json.getString("Poster");
			File path = downloadPoster(poster);
			if(path != null) {
				poster = path.getAbsolutePath();
			}
			Movie m = new Movie();
			m.setTitle(title);
			m.setYear(year);
			m.setPlot(plot);
			m.setGenre(genre);
			m.setPoster(poster);
			return m;
		}
		
		private byte[] parseInputStream(InputStream in) throws IOException {
			byte[] buffer = new byte[1024];
	        int data;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        while(-1 != (data = in.read(buffer))) {
	            out.write(buffer, 0, data);
	        }
	        return out.toByteArray();
		}

		/**
		 * Download poster image
		 * @param posterUrl
		 * @return
		 */
		private File downloadPoster(String posterUrl) {
			URLConnection conn;
	        InputStream in = null;
	        OutputStream out = null;
	        File fileDest = null;     
	        try {
	        	fileDest = Environment.getExternalStorageDirectory();	
	            URL url = new URL(posterUrl);
	            conn = url.openConnection();
	            conn.connect();
	            int length = conn.getContentLength();
	            Log.d(TAG, "connection IO length: " + length);
	            in = conn.getInputStream();
	            if(fileDest != null) {
	                out = new BufferedOutputStream(new FileOutputStream(fileDest));
	                byte[] buffer = new byte[2048];
	                int count;
	                while (-1 != (count = in.read(buffer))) {
	                    out.write(buffer, 0, count);
	                }
	                out.flush();
	                
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if(out != null) {
	                try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	            if(in != null) {
	                try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	        
	        return fileDest;
	    }


	}
}
