package YingYingMonster.LetsDo_Phase_III.model;

import java.io.Serializable;

public class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5201496832063150204L;
	private byte[]data;
	private int width,height;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
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
	
	
}
