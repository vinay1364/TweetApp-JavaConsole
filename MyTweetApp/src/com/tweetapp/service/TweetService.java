package com.tweetapp.service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tweetapp.dao.TweetDao;
import com.tweetapp.model.Tweet;

public class TweetService {

	private TweetDao tweetDao;

	public TweetService() {
		tweetDao = new TweetDao();
	}

	public boolean postTweet(Tweet tweet) {
		Date date=new  Date(System.currentTimeMillis());
		tweet.setCreatedOn(date);
		return tweetDao.insert(tweet);
	}

	public List<Tweet> getTweetsByUserId(int userId) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		ResultSet resultSet = null;
		try {
			resultSet = tweetDao.getTweetsByUserId(userId);
			while (resultSet.next()) {
				Tweet tweet = new Tweet();
				tweet.setDescription(resultSet.getString("description"));
				tweet.setUserId(resultSet.getInt("user_id"));
				tweet.setCreatedOn(resultSet.getDate("created_on"));
				tweets.add(tweet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tweets;
	}

	public List<Tweet> getAllTweets() {
		List<Tweet> allTweets = new ArrayList<Tweet>();
		ResultSet resultSet = null;
		try {
			resultSet = tweetDao.getAllTweets();
			while (resultSet.next()) {
				Tweet tweet = new Tweet();
				tweet.setDescription(resultSet.getString("description"));
				tweet.setUserId(resultSet.getInt("user_id"));
				tweet.setCreatedOn(resultSet.getDate("created_on"));
				allTweets.add(tweet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allTweets;
	}
	
	public void disconnectDB() {
		tweetDao.disconnect();
	}
	

}
