package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tweetapp.model.Tweet;

public class TweetDao {

	private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mytweetdb";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "pass@word1";

	private Connection connection;
	
	public TweetDao() {
		connection=connect();
		createTable();
	}

	public void createTable() {
		String sql="create table if not exists tweet(id int NOT NULL AUTO_INCREMENT, "
				+ "description varchar(100) NOT NULL, user_id int NOT NULL, created_on date,primary key(id))";
		Connection con = connect();
		
		try {
		    Statement stmt=con.createStatement();
		    stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean insert(Tweet tweet) {
		String sql = "insert into tweet(description, user_id, created_on) values(?,?,?)";
		Connection con = connect();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, tweet.getDescription());
			preparedStatement.setInt(2, tweet.getUserId());
			preparedStatement.setDate(3, tweet.getCreatedOn());

			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate == 1) {
				System.out.println("Tweet Inserted.");
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong! Tweet not created.");
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getTweetsByUserId(int userId) {
		String sql = "select * from tweet where user_id=" + userId;
		ResultSet resultSet = null;
		Connection con = connect();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Unable to get the tweets. Something went wrong. Please try again..");
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getAllTweets() {
		String sql = "select * from tweet";
		ResultSet resultSet = null;
		Connection con = connect();
		try {
			Statement stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Unable to get all Tweets. Something went wrong. Please try again..");
			e.printStackTrace();
		}
		return resultSet;
	}

	public Connection connect() {
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
			} catch (ClassNotFoundException e) {
				System.out.println("Couldn't load the Database Driver.");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Couldn't connect to the Database.");
				e.printStackTrace();
			}
		}
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("Connection with the Database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
