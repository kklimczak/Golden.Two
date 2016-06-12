package com.goldentwo.data.Settings;

public class Settings {
	
	private int id;
	private String defaultExportPath;
	private int alarmSound;
	private int lookAndFeelNumber;
	
	public Settings(int id, String defaultExportPath, int alarmSound, int lookAndFeelNumber) {
		this.id = id;
		this.defaultExportPath = defaultExportPath;
		this.alarmSound = alarmSound;
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

	public int getAlarmSound() {
		return alarmSound;
	}

	public void setAlarmSound(int alarmSound) {
		this.alarmSound = alarmSound;
	}

	public int getLookAndFeelNumber() {
		return lookAndFeelNumber;
	}

	public void setLookAndFeelNumber(int lookAndFeelNumber) {
		this.lookAndFeelNumber = lookAndFeelNumber;
	}
	
	
	
}
