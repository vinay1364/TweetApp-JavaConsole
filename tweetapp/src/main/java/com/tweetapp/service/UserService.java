package com.tweetapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tweetapp.dao.UserDao;
import com.tweetapp.model.User;

public class UserService {
	
	private UserDao userDao;
	private ResetPasswordService resetPasswordService;
	
	public UserService() {	
		userDao=new UserDao();
		resetPasswordService=new ResetPasswordService();
	}
	
	public boolean registerUser(User user){
		return userDao.insert(user);	
	}
	
	public List<User> getAllUsers(){
		List<User> listUsers=new ArrayList<User>();
		ResultSet resultSet = null;
		try {
			resultSet=userDao.getAllUsers();
			while(resultSet.next()) {
				User user=new User();
				user.setFirst_name(resultSet.getString("first_name"));
				user.setLast_name(resultSet.getString("last_name"));
				user.setGender(resultSet.getString("gender"));
				user.setDob(resultSet.getString("dob"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				
				listUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listUsers;
	}
	
	public Map<String, Integer> login(String email, String password) {
		 return userDao.login(email, password);
	}
	
	public boolean resetPassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
		int resetStatus=resetPasswordService.resetPassword(oldPassword, newPassword, confirmPassword);
		if(resetStatus!=1)
			return false;	//All conditions for resetting are not satisfied.
		
		//if all conditions are met, then reset/update password
		return userDao.resetPassword(userId, oldPassword, newPassword);	
	}
	
	public boolean forgotPassword(String email, String newPassword) {
		return userDao.forgotPassword(email, newPassword);
	}
	
	public boolean logout(int userId) {
		return userDao.logout(userId);
	}
	
	public void disconnectDB() {
		userDao.disconnect();		
	}
	
	public boolean isEmailPresent(String email) {
		List<User> allUsers = getAllUsers();
		Map<String, String> emailMap=allUsers.stream()
				.collect(Collectors.toMap(User::getEmail, User::getFirst_name));//key->email, value->first_name
		return emailMap.containsKey(email);
	}
	
}
