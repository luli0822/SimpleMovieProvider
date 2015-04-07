package com.ll.android.simplemovieprovider.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
	private String mTitle;
	private int mYear;
	private long mReleased;
	private String mRating;
	private long mRuntime;
	private String mGenre;
	private List<String> mDirectors;
	private List<String> mWriters;
	private List<String> mActors;
	private String mPlot;
	private List<String> mLanguages;
	private String mCountry;
	private String mPoster;
	private String mMetaScore;
	private String mIMDBRating;
	private long mIMDBVotes;
	private String mID;
	private String mType;
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public String toString() {
		return super.toString();
	}
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		this.mTitle = title;
	}
	public int getYear() {
		return mYear;
	}
	public void setYear(int year) {
		this.mYear = year;
	}
	public long getReleased() {
		return mReleased;
	}
	public void setReleased(long released) {
		this.mReleased = released;
	}
	public String getRating() {
		return mRating;
	}
	public void setRating(String rating) {
		this.mRating = rating;
	}
	public long getRuntime() {
		return mRuntime;
	}
	public void setRuntime(long runtime) {
		this.mRuntime = runtime;
	}
	public String getGenre() {
		return mGenre;
	}
	public void setGenre(String genre) {
		this.mGenre = genre;
	}
	public List<String> getDirectors() {
		return mDirectors;
	}
	public void setDirectors(List<String> directors) {
		this.mDirectors = directors;
	}
	public List<String> getWriters() {
		return mWriters;
	}
	public void setWriters(List<String> writers) {
		this.mWriters = writers;
	}
	public List<String> getActors() {
		return mActors;
	}
	public void setActors(List<String> actors) {
		this.mActors = actors;
	}
	public String getPlot() {
		return mPlot;
	}
	public void setPlot(String plot) {
		this.mPlot = plot;
	}
	public List<String> getLanguages() {
		return mLanguages;
	}
	public void setLanguages(List<String> languages) {
		this.mLanguages = languages;
	}
	public String getCountry() {
		return mCountry;
	}
	public void setCountry(String country) {
		this.mCountry = country;
	}
	public String getPoster() {
		return mPoster;
	}
	public void setPoster(String poster) {
		this.mPoster = poster;
	}
	public String getMetaScore() {
		return mMetaScore;
	}
	public void setMetaScore(String metaScore) {
		this.mMetaScore = metaScore;
	}
	public String getIMDBRating() {
		return mIMDBRating;
	}
	public void setIMDBRating(String IMDBRating) {
		this.mIMDBRating = IMDBRating;
	}
	public long getIMDBVotes() {
		return mIMDBVotes;
	}
	public void setIMDBVotes(long IMDBVotes) {
		this.mIMDBVotes = IMDBVotes;
	}
	public String getID() {
		return mID;
	}
	public void setID(String id) {
		this.mID = id;
	}
	public String getType() {
		return mType;
	}
	public void setType(String type) {
		this.mType = type;
	}
	
	
}
