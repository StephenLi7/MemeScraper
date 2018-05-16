import java.util.*;


public class Users {
	
	
	String name;
	ArrayList<String> listOfFriends;
	ArrayList<String> listOfInterestedMemes;
	
	
	
	public Users (int name) {
		this.name = "User" + name;
		listOfFriends = new ArrayList<String>();
		listOfInterestedMemes = new ArrayList<String>();
	}
	
	
	public void setFriends (String newFriend) {
		listOfFriends.add(newFriend);
	}
	
	public void setMeme (String newMeme) {
		listOfInterestedMemes.add(newMeme);
	}
	
	public String getName () {
		return name;
	}
	
	public ArrayList<String> getFriends () {
		return listOfFriends;
	}
	
	public ArrayList<String> getMemes () {
		return listOfInterestedMemes;
	}
	
	
}
