package com.tweetapp;

import java.util.Map;
import java.util.Scanner;

import com.tweetapp.util.UserUtil;

public class TweetAppMain {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		UserUtil util = new UserUtil();
		int choice = 0;
		int userId = -1;
		boolean loginStatus = false;
		
		while (loginStatus || (choice != 4)) {

			if (!loginStatus) { // user not logged in
				System.out.println("1. Register \n2. Login \n3. Forgot Password \n4. Exit");
				System.out.println("Enter Your Choice:");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					boolean userRegistered=util.register();
					if(userRegistered)
						System.out.println("User Succesfully Registered. Please proceed to Login.");
					else
						System.out.println("Please try again..");
					break;

				case 2:
					Map<String, Integer> map = util.loginUser();
					if (!map.isEmpty() && map.get("status") == 1) {
						userId = map.get("userId");
						loginStatus = true;
						System.out.println("Login Successful.");
					} else
						System.out.println("Invalid Email or password");
					break;

				case 3:
					boolean passwordSet=util.forgotPassword();
					if(passwordSet)
						System.out.println("Password Changed Successfully.");
					else
						System.out.println("Please try again..");
					break;
				case 4:
					util.disconnectDB();
					System.out.println("Exited!");
					break;
				default:
					System.out.println("Please give valid input!!");
				}
			} else { // when user is logged in

				System.out.println("1. Post a Tweet \n2. View My Tweets \n3. View All Tweets \n4. View All Users "
						+ "\n5. Reset Password \n6. Logout");
				System.out.println("Enter Your Choice:");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					util.postTweet(userId);
					break;

				case 2:
					boolean foundTweets=util.viewMyTweets(userId);
					if(!foundTweets)
						System.out.println("Please post a new Tweet.");
					break;

				case 3:
					boolean anyTweetFound=util.viewAllTweets();
					if(!anyTweetFound)
						System.out.println("Please post a new Tweet.");
					break;

				case 4:
					util.viewAllUsers();
					break;

				case 5:
					boolean resetStatus=util.resetPassword(userId);
					if(resetStatus)
						System.out.println("Password Changed Successfully.");
					else
						System.out.println("Couldn't Reset Password. Please try again.");						
					break;

				case 6:
					boolean uStatus=util.logoutUser(userId);
					if(uStatus) {
						userId = -1;
						loginStatus = false;
						System.out.println("User Logged-Out");
					}						
					else
						System.out.println("Couldn't Logout. Please try again.");
					break;
				default:
					System.out.println("Please give valid input!!");
				}
			}
		}
		
	}

}
