import java.util.LinkedList;

public class Meme {

	private String origin;
	private String name;
	private String about;
	private int year;
	private LinkedList<String> typeList;
	private LinkedList<String> tagList;

	public Meme(String name, String about, int year, String origin, LinkedList<String> typeList,
			LinkedList<String> tagList) {
		this.name = name;
		this.about = about;
		this.origin = origin;
		this.year = year;
		this.typeList = typeList;
		this.tagList = tagList;
	}

	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
	}

	public int getYear() {
		return year;
	}

	public LinkedList<String> getType() {
		return typeList;
	}

	public LinkedList<String> getTag() {
		return tagList;
	}

	public String getAbout() {
		return about;
	}

}
