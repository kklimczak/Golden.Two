package com.goldentwo.data.Settings;

public class Settings {
	
	private int id;
	private String defaultExportPath;
	private int currentDayBorderColor;
	private int eventDayColor;
	private int lookAndFeelNumber;
	
	public Settings(int id, String defaultExportPath, int currentDayBorderColor, int eventDayColor, int lookAndFeelNumber) {
		this.id = id;
		this.defaultExportPath = defaultExportPath;
		this.currentDayBorderColor = currentDayBorderColor;
		this.eventDayColor = eventDayColor;
		this.lookAndFeelNumber = lookAndFeelNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefaultExportPath() {
		return defaultExportPath;
	}

	public void setDefaultExportPath(String defaultExportPath) {
		this.defaultExportPath = defaultExportPath;
	}

	public int getCurrentDayBorderColor() {
		return currentDayBorderColor;
	}

	public void setCurrentDayBorderColor(int currentDayBorderColor) {
		this.currentDayBorderColor = currentDayBorderColor;
	}

	public int getEventDayColor() {
		return eventDayColor;
	}

	public void setEventDayColor(int eventDayColor) {
		this.eventDayColor = eventDayColor;
	}

	public int getLookAndFeelNumber() {
		return lookAndFeelNumber;
	}

	public void setLookAndFeelNumber(int lookAndFeelNumber) {
		this.lookAndFeelNumber = lookAndFeelNumber;
	}
	
	
	
}
