package YingYingMonster.LetsDo_Phase_III.model;

import java.io.Serializable;
import java.util.Map;

public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9046136027079150454L;
	private String type;
	private Map<String,String>attributes;
	private byte[]data;
	private int width,height;
	private int score;
	
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Tag() {
		super();
	}
	public Tag(String type, Map<String, String> attributes, byte[] data, int width, int height) {
		super();
		this.type = type;
		this.attributes = attributes;
		this.data = data;
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
