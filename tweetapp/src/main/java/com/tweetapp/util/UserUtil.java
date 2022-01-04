package com.tweetapp.util;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;

public class UserUtil {

	private String first_name;
	private String last_name;
	private String gender;
	private String dob;
	private String email;
	private String password;

	Scanner sc = new Scanner(System.in);
	UserService userService = new UserService();
	TweetService tweetService = new TweetService();

	// Register the user
	public boolean register() {
		boolean loginStatus = false;
		User user = new User();

		while (true) {
			System.out.println("Enter First Name:"); 
			first_name = sc.nextLine();
			if (first_name.length() >= 3) {
				user.setFirst_name(first_name);
				break;
			}
			System.out.println("First Name should have atleast 3 characters.");
		}

		while (true) {
			System.out.println("Enter Last Name:");
			last_name = sc.nextLine();
			if ((last_name == null) || (last_name.length() >= 3)) {
				user.setLast_name(last_name);
				break;
			}
			System.out.println("Last Name should have atleast 3 characters.");
		}

		while (true) {
			System.out.println("Enter Gender:");
			gender = sc.nextLine();
			if (gender.equals("male") || gender.equals("female")) {
				user.setGender(gender);
				break;
			}
			System.out.println("Please enter male or female.");
		}

		while (true) {
			System.out.println("Enter Date Of Birth:");
			dob = sc.nextLine();
			if (dob.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
				user.setDob(dob);
				break;
			}
			System.out.println("Please enter valid date in yyyy-mm-dd format.");
		}

		while (true) {
			System.out.println("Enter Email Id:");
			email = sc.nextLine();
			if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				if(!userService.isEmailPresent(email)) {
					user.setEmail(email);
					break;
				}
				else 
					System.out.println("This email already exists");
				}
				System.out.println("Please enter valid email address.");
			}
			
		

		while (true) {
			System.out.println("Enter Password:"); 
			password = sc.nextLine();
			if (password.length() >= 5 && password.length() <= 10) {
				user.setPassword(password);
				break;
			}
			System.out.println("Password should be 5-10 characters long.");
		}
		user.setStatus(loginStatus);// when user is registered login status=false/0

		return userService.registerUser(user);
	}

	// All users
	public void viewAllUsers() {
		List<User> users = userService.getAllUsers();
			users.forEach((user)->System.out.println(user.getFirst_name()+" "+user.getLast_name()));
	}

	public Map<String, Integer> loginUser() {
		while (true) {
			System.out.println("Enter Email Id:"); 
			email = sc.nextLine();
			if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				break;
			}
			System.out.println("Please enter valid email address.");
		}

		while (true) {
			System.out.println("Enter Password:"); 
			password = sc.nextLine();
			if (password.length() >= 5 && password.length() <= 10) {
				break;
			}
			System.out.println("Password should be 5-10 characters long.");
		}

		return userService.login(email, password);
	}

	public boolean resetPassword(int userId) {
		String oldPassword = "";
		String newPassword = "";
		String confirmPassword="";

		while (true) {
			System.out.println("Enter Password:");
			oldPassword = sc.nextLine();
			if (oldPassword.length() >= 5 && oldPassword.length() <= 10) {
				break;
			}
			System.out.println("Password should be 5-10 characters long.");
		}

		while (true) {
			System.out.println("Enter New Password:");
			newPassword = sc.nextLine();
			if (newPassword.length() >= 5 && newPassword.length() <= 10) {
				break;
			}
			System.out.println("Password should be 5-10 characters long.");
		}
		
		while (true) {
			System.out.println("Confirm New Password:");
			confirmPassword = sc.nextLine();
			if (confirmPassword.length() >= 5 && confirmPassword.length() <= 10) {
				break;
			}
			System.out.println("Password should be 5-10 characters long.");
		}
		
		return userService.resetPassword(userId, oldPassword, newPassword, confirmPassword);
	}

	public boolean forgotPassword() {
		String newPassword = "";
		while (true) {
			System.out.println("Enter Email Id:");
			email = sc.nextLine();
			if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				break;
			}
			System.out.println("Please enter valid email address.");
		}

		while (true) {
			System.out.println("Enter New Password:");
			newPassword = sc.nextLine();
			if (newPassword.length() >= 5 && newPassword.length() <= 10) {
				break;
			}
			System.out.println("Password should be 5-10 characters long.");
		}
		return userService.forgotPassword(email, newPassword);
	}

	public boolean logoutUser(int userId) {
		return userService.logout(userId);
	}
	
	public void disconnectDB() {
		userService.disconnectDB();
		tweetService.disconnectDB();
	}

	// Tweets

	public boolean postTweet(int userId) {
		Tweet tweet = new Tweet();
		String description = "";
		while (true) {
			System.out.println("Enter your Tweet:");
			description = sc.nextLine();
			if (description.length() > 0) {
				break;
			}
			System.out.println("Please enter few characters.");
		}
		tweet.setDescription(description);
		tweet.setUserId(userId);
		return tweetService.postTweet(tweet);
	}

	public boolean viewMyTweets(int userId) {
		List<Tweet> myTweets = tweetService.getTweetsByUserId(userId);
		if(!myTweets.isEmpty()) {
			myTweets.forEach((tweet)->System.out.println("\'" + tweet.getDescription() + "\'" +"\n" + tweet.getCreatedOn() + "\n"));
			return true;
		}
			return false;
	}

	public boolean viewAllTweets() {
		List<Tweet> allTweets = tweetService.getAllTweets();
		if(!allTweets.isEmpty()) {
			allTweets.forEach((tweet)->System.out.println("\'" + tweet.getDescription() + "\'" +"\n" + tweet.getCreatedOn() + "\n"));
			return true;
		}
		return false;
		
	}
	

}
