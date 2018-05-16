import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;

public class UserQueryManager {
	// This booleans keeps track of whether the user is still engaged with the
	// program
	private static boolean runningProgram = true;
	// Scanner is used to handle any user input in lieu of any GUI input
	private static Scanner scanner = new Scanner(System.in);

	public static HashMap<String, Meme> memeMap;
	public static ArrayList<String> memeNameList;
	public static ArrayList<Users> usersList;
	private LinkedList<String> commonTagList;

	public void Run() {

		usersList = new ArrayList<Users>();
		memeMap = new HashMap<String, Meme>();
		memeNameList = new ArrayList<String>();
		System.out.println("Please enter the number of pages you would like to scrape: ");
		while (!scanner.hasNextInt()) {
			String userNotInt = scanner.next();
			System.out.println("User Input '" + userNotInt + "' is not an integer. Please input integer");
		}
		int userInput = scanner.nextInt();
		scanner.nextLine();
		for (int i = 1; i <= userInput; i++) {
			JSoupExample jse = new JSoupExample(i);
			jse.getElements();
		}
		// This starts and run the UrlManager - which will download and parse
		// the data
		// for subsequent queries

		while (runningProgram) {
			showQueryMenu();

			// Check for integer, will not move on until it receives an integer
			while (!scanner.hasNextInt()) {
				String userNotInt = scanner.next();
				System.out
						.println("User Input '" + userNotInt + "' is not an integer. Please input integer between 1-4");

				// IMPROVE this is redundant code? should restructure flow
				showQueryMenu();
			}

			int userChoiceInInt = scanner.nextInt();
			// throw the rest of the info in the same line away in case user
			// inputs something like "1 xyz", it will only take 1
			scanner.nextLine();

			switch (userChoiceInInt) {

			case 1:
				Part1();
				break;
			case 2:
				Part2();
				break;
			
			case 0:
				System.out.println("You have chosen to quit the program.");
				System.out.println("Thank you for using this program!");
				runningProgram = false;
				scanner.close();
				break;
			default:
				System.out.println(
						"User Input " + userChoiceInInt + " is not a valid integer. Please input integer between 1-4");
				break;
			}
		}
	}

	private void showQueryMenu() {
		System.out.println();

		System.out.println("1) Meme Dictionary");
		System.out.println("2) Social Network Simulation");
		System.out.println("0) Quit Program");
	}

	private void Part1() {
		System.out.println("Please select what you would like to do");
		System.out.println("1) See 10 random meme names");
		System.out.println("2) Get details on a meme");
		System.out.println("3) Compare 2 memes: which one is older?");
		System.out.println("4) Get the tags associated with a meme");
		System.out.println("5) Compare 2 memes: do they share any common tags?");
		System.out.println("6) Test your knowledge of viral memes!");

		while (!scanner.hasNextInt()) {
			String userNotInt = scanner.next();
			System.out.println("User Input '" + userNotInt + "' "
					+ "is not an integer. Please input integer");
		}
		int userInput = scanner.nextInt();
		scanner.nextLine();
		switch (userInput) {

		case 1:
			randomMemes();
			break;

		case 2:
			getMemeDetail();
			break;

		case 3:
			compare2memes();
			break;
			
		case 4:
			getMemeTags();
			break;
			
		case 5:
			compareMemeTags();
			break;
			
		case 6:
			triviaMeme();
			break;
			
			
		default:
			System.out.println("User Input " + userInput + " is not a valid integer. Please input integer between 1-3");
			break;
		}
	}
	
	
	private void Part2() {
		System.out.println("Please enter in the number of random users in this network");
		
		while (!scanner.hasNextInt()) {
			String userNotInt = scanner.next();
			System.out.println("User Input '" + userNotInt + "' "
					+ "is not an integer. Please input integer");
		}
		
		int userInputOriginal = scanner.nextInt();
		scanner.nextLine();
		
		generateRandomUsers(userInputOriginal);
		
		
		System.out.println("You are User0");
		System.out.println("Please select what you would like to do");
		System.out.println("1) Get a recommendation for a friend through a common meme");
		System.out.println("2) Get a recommendation for a meme through a friend");
		System.out.println("3) Show me the profile of a specific user");
		

		while (!scanner.hasNextInt()) {
			String userNotInt = scanner.next();
			System.out.println("User Input '" + userNotInt + "' "
					+ "is not an integer. Please input integer");
		}
		
		int userInput = scanner.nextInt();
		scanner.nextLine();
		switch (userInput) {

		case 1:
			//randomMemes();
			System.out.println("Please type out a meme name EXACTLY AS IT APPEARED ABOVE");
			String userRequest= scanner.nextLine();
			
			
			String temporaryOriginal = getFriendThroughMeme(userRequest);
			System.out.println("We recommend this friend for you: " + temporaryOriginal);
			
			break;

		case 2:
			String temporary = getMemeThroughFriend();
			System.out.println("We recommend this meme for you: " + temporary);
			break;

			
		case 3:
			System.out.println("Please enter in a user ID (integer) below the number of "
					+ "users just entered");
			while (!scanner.hasNextInt()) {
				String userNotInt = scanner.next();
				System.out.println("User Input '" + userNotInt + "' "
						+ "is not an integer. Please input integer");
			}
			
			int userQuery = scanner.nextInt();
			scanner.nextLine();
			
			if (userQuery >= userInputOriginal) {
				System.out.println("Invalid Entry - Returning to Starting Page");
				
			} else {
				printOutUserDetails(userQuery);
			}
			
			break;
			
		default:
			System.out.println("User Input " + userInput + " is not a valid integer. Please input integer between 1-3");
			break;
		}
		
	}

	

	private void randomMemes() {
		Collections.shuffle(memeNameList);
		for (int i = 0; i < 10; i++) {
			System.out.println(memeNameList.get(i));
		}
	}

	private void compare2memes() {
		System.out.println("Please enter the first meme:");
		while (!scanner.hasNext()) {
			String userInput2 = scanner.nextLine();

		}
		String userInput = scanner.nextLine();
		// scanner.nextLine();
		System.out.println("Please enter the first meme");
		while (!scanner.hasNext()) {
			String userInput2 = scanner.nextLine();
		}
		String userInput2 = scanner.nextLine();
		// scanner.nextLine();

		if (memeMap.containsKey(userInput) && memeMap.containsKey(userInput2)) {

			int year1 = memeMap.get(userInput).getYear();
			int year2 = memeMap.get(userInput2).getYear();

			if (year1 < year2) {
				System.out.println(userInput + "(" + year1 + ")" + " is older than " + userInput2 + "(" + year2 + ")");
			} else if (year1 > year2) {
				System.out.println(userInput2 + "(" + year2 + ")" + " is older than " + userInput + "(" + year1 + ")");
			} else {
				System.out.println("They are from the same year!" + year1);
			}

		} else {
			System.out.println("Invalid input! Please try again");
		}

	}

	private void getMemeDetail() {
		System.out.println("Please enter the meme you would like to know more about");

		String userInput = scanner.nextLine();

		if (memeMap.containsKey(userInput)) {
			Meme currentMeme = memeMap.get(userInput);
			System.out.println("About " + currentMeme.getName() + " ... ");
			System.out.println(currentMeme.getAbout());
			System.out.println("Year: ");
			System.out.println(currentMeme.getYear());
			System.out.println("Origin: ");
			System.out.println(currentMeme.getOrigin());
			System.out.println("Type: ");
			List<String> types = currentMeme.getType();
			for (String s : types) {
				System.out.println(s);
			}

		} else {
			System.out.println("Cannot find the specified meme!");
		}

	}
	
	private void printOutUserDetails (int UserID) {
		System.out.println(usersList.get(UserID).getName());
		ArrayList<String> tempUsers = new ArrayList<String>();
		tempUsers = usersList.get(UserID).getFriends();
		
		ArrayList<String> tempMemes = new ArrayList<String>();
		
		tempMemes = usersList.get(UserID).getMemes();
		
		System.out.println("Friends:");
		
		
		for (int j = 0; j < tempUsers.size(); j++) {
			System.out.println(tempUsers.get(j));
		}
		
		System.out.println("Memes:");
		for (int j = 0; j < tempMemes.size(); j++) {
			System.out.println("Meme:" + tempMemes.get(j));
		}
		
	}
	
	private void getMemeTags() {
		System.out.println("Please input a meme name and we'll return associated tags"
				+ "in the meme database");
		String userInput = scanner.nextLine();
		if (memeMap.containsKey(userInput)) {
			Meme currentMeme = memeMap.get(userInput);
			System.out.println("Tags of " + currentMeme.getName() + " are: ");
			System.out.println(currentMeme.getTag());
		}
	}
	
	private void compareMemeTags() {
		commonTagList = new LinkedList<String>();
		System.out.println("Enter the first meme you wish to compare");
		String userInput1 = scanner.nextLine();
		System.out.println("Enter the second meme you wish to compare");
		String userInput2 = scanner.nextLine();
		if (memeMap.containsKey(userInput1) && memeMap.containsKey(userInput2)) {
			Meme meme1 = memeMap.get(userInput1);
			Meme meme2 = memeMap.get(userInput2);
			for (String tagName : meme1.getTag()) {
				for (String tagName2 : meme2.getTag()) {
					if (tagName.equals(tagName2)) {
						commonTagList.add(tagName);
					}
				}
			}
			System.out.println(commonTagList);
		}
		else {
			System.out.println("One of the memes you inputted does not exist");
		}
	}
	
	private void triviaMeme() {
		System.out.println("Do you know your memes?");
		Collections.shuffle(memeNameList);
		String memeTrivia = memeNameList.get(0);
		if (memeMap.containsKey(memeTrivia)) {
			Meme meme = memeMap.get(memeTrivia);
			int yearOfOrigin = meme.getYear();
			System.out.println("What year did " + memeTrivia + " go viral?");
			int userInput = scanner.nextInt();
			if (userInput == yearOfOrigin) {
				System.out.println("Good job! You are indeed a meme connosieur! :)");
			}
			else {
				System.out.println("That's ok! try again for a different meme!");
			}
		}
		
	}
	
	private void generateRandomUsers (int userInputOriginal) {
		
		//System.out.println("Received" + userInputOriginal);
		int [][] randomGeneration = new int [userInputOriginal][userInputOriginal];
		
		for (int i = 0; i < userInputOriginal; i ++) {
			for (int j = 0; j < userInputOriginal; j ++) {
				int tester = (int)(Math.random()*10);
				if (tester > 8) {
					if (i != j) {
						randomGeneration[i][j] = 1;
						randomGeneration[j][i] = 1;
					}
				}
				
			}
		}
		
		for (int i = 0; i < userInputOriginal; i++) {
			Users user = new Users(i);
			int j = 0;
			ArrayList<Integer> memesNumbers = new ArrayList<Integer>();
			
			while (j < userInputOriginal) {
				if (randomGeneration[i][j] == 1) {
					user.setFriends("User"+j);
				}
				j++;
			}
			
			for (int k = 0; k < memeNameList.size(); k ++) {
				memesNumbers.add(k, k);
			}
			
			Collections.shuffle(memesNumbers);
			
			for (int index = 0; index < memeNameList.size() && index < 10; index++) {
				user.setMeme(memeNameList.get(memesNumbers.get(index)));
			}
			
			
			usersList.add(user);
			
		}
				
		tester(userInputOriginal);
		
	}
	
	
	
	private String getMemeThroughFriend() {
		ArrayList<String>tempFriends = new ArrayList<String>();
		tempFriends = usersList.get(0).getFriends();
		ArrayList<String>tempMemes = new ArrayList<String>();
	
		
		int randomFriend = (int)(Math.random() * tempFriends.size());
		System.out.println(tempFriends.get(randomFriend) + " is chosen as the recommender");
		
		String temporaryUser = tempFriends.get(randomFriend);
		temporaryUser = temporaryUser.substring(4);
		int temporary = Integer.parseInt(temporaryUser);
		
		tempMemes = usersList.get(temporary).getMemes();
		
		int randomMeme = (int)(Math.random() * tempMemes.size());
		return tempMemes.get(randomMeme);
	}
	
	private String getFriendThroughMeme(String userRequest) {
		ArrayList<String> tempMemes = new ArrayList<String>();
		String output = "";
		ArrayList<Users> tempUsers = new ArrayList<Users>();
		
		for (int k = 0; k < usersList.size(); k++) {
			tempUsers.add(usersList.get(k));
		}
		
		for (int z = 0; z < usersList.get(0).getFriends().size(); z ++) {
			String uniqueSortingFriends = usersList.get(0).getFriends().get(z);
			//uniqueSortingFriends = uniqueSortingFriends.substring(4);
			for (int y = 0; y <tempUsers.size(); y++) {
				if (tempUsers.get(y).getName().equals(uniqueSortingFriends)) {
					tempUsers.remove(y);
				}
			}
			//int temporary = Integer.parseInt(uniqueSortingFriends);
			
			
		}
		
		Collections.shuffle(tempUsers);
		
		for (int i = 0; i < tempUsers.size(); i ++) {
			tempMemes = tempUsers.get(i).getMemes();
			//Collections.shuffle(tempMemes);
			
			for (int j = 0; j < tempMemes.size(); j ++) {
				if (tempMemes.get(j).equals(userRequest)) {
					output = tempUsers.get(i).getName();
					return output;
				}
			}	
		}
		
		
		return "";
		
	}
	
	private void tester(int limits) {
		for (int i = 0; i < limits; i++ ) {
			System.out.println(usersList.get(i).getName());
			ArrayList<String> tempUsers = new ArrayList<String>();
			ArrayList<String> tempMemes = new ArrayList<String>();
			tempUsers = usersList.get(i).getFriends();
			tempMemes = usersList.get(i).getMemes();
			
			for (int j = 0; j < tempUsers.size(); j++) {
				System.out.println("Friend:" + tempUsers.get(j));
			}
			
			for (int j = 0; j < tempMemes.size(); j++) {
				System.out.println("Meme:" + tempMemes.get(j));
			}
			
	
		}
	}

}
