package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.tweetapp.model.User;

public class UserDao {

	private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mytweetdb";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "urban";

	private Connection connection;
	
	public UserDao() {
		connection=connect();		
		createTable();
	}

	public void createTable() {
		String sql="create table if not exists user(id int not null auto_increment,first_name varchar(20) not null,"
				+ "last_name varchar(20),gender varchar(20) not null, dob varchar(10),email varchar(30) not null unique, "
				+ "password varchar(20) not null,status boolean,primary key(id))";
		Connection con = connect();		
		try {
		    Statement stmt=con.createStatement();
		    stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public boolean insert(User user) {
		String sql = "insert into user(first_name,last_name,gender,dob,email,password,status) values(?,?,?,?,?,?,?)";
		Connection con = connect();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, user.getFirst_name());
			preparedStatement.setString(2, user.getLast_name());
			preparedStatement.setString(3, user.getGender());
			preparedStatement.setString(4, user.getDob());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getPassword());
			preparedStatement.setBoolean(7, user.isStatus());

			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate == 1) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong! User not created.");
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getAllUsers() {
		String sql = "select * from user";
		ResultSet resultSet = null;
		Connection con = connect();
		try {
			Statement stmt = con.createStatement();
			resultSet = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Unable to get all users. Something went wrong. Please try again..");
			e.printStackTrace();
		}
		return resultSet;
	}

	public Map<String, Integer> login(String email, String password) {
		String sql = "select * from user where email=? and password=?";
		Map<String, Integer> userStatus = new HashMap<>();
		userStatus.put("status", 0);
		Connection con = connect();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				preparedStatement = con.prepareStatement("update user set status=? where email=?");
				preparedStatement.setBoolean(1, true); // set status as true(1)
				preparedStatement.setString(2, resultSet.getString("email"));

				int executeUpdate = preparedStatement.executeUpdate();
				if (executeUpdate == 1) {
					userStatus.put("userId", resultSet.getInt("id"));
					userStatus.put("status", 1);
					System.out.println("User Logged-In");
				}

			}

		} catch (SQLException e) {
			System.out.println("Something went wrong. Please try again..");
			e.printStackTrace();
		}
		return userStatus;
	}

	public boolean resetPassword(int userId, String oldPassword, String newPassword) {
		String sql = "update user set password=? where id=? and password=?";
		Connection con = connect();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, userId);
			preparedStatement.setString(3, oldPassword);

			int executeUpdate = preparedStatement.executeUpdate();
			if (executeUpdate == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean forgotPassword(String email, String newPassword) {
		String sql = "update user set password=? where email=?";
		Connection con = connect();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, email);

			int executeUpdate = preparedStatement.executeUpdate();
			if (executeUpdate == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean logout(int userId) {
		String sql = "update user set status=? where id=?";
		Connection con = connect();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setBoolean(1, false); // logout
			preparedStatement.setInt(2, userId);

			int executeUpdate = preparedStatement.executeUpdate();
			if (executeUpdate == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Something went wrong. Please try again..");
			e.printStackTrace();
		}
		return false;
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
