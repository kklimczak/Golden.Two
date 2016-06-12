package com.goldentwo.data.Settings;

/**
 * Klasa przechowywujaca wszystkie glowne ustawienia programu, takie jak sciezka do eksportu, dzwiek alarmu jak i wyglad okna.
 */
public class Settings {
	
	/** Id obiektu ustawien w bazie. */
	private int id;
	
	/** Domyslna sciezka eksportu plikow xml/ics. */
	private String defaultExportPath;
	
	/** Numer dzwieku alarmu. */
	private int alarmSound;
	
	/** Numer wygladu okna. */
	private int lookAndFeelNumber;
	
	/**
	 * Podstawowy konstruktor inicjujacy wszystkie pola w obiekcie tej ze klasy.
	 *
	 * @param id Id obiektu ustawien w bazie
	 * @param defaultExportPath Domyslna sciezka eksportu plikow xml/ics
	 * @param alarmSound Numer dzwieku alarmu
	 * @param lookAndFeelNumber Numer wygladu okna
	 */
	public Settings(int id, String defaultExportPath, int alarmSound, int lookAndFeelNumber) {
		this.id = id;
		this.defaultExportPath = defaultExportPath;
		this.alarmSound = alarmSound;
		this.lookAndFeelNumber = lookAndFeelNumber;
	}

	/**
	 * Zwraca id ustawien.
	 *
	 * @return Id ustawien
	 */
	public int getId() {
		return id;
	}

	/**
	 * Ustawia id ustawien.
	 *
	 * @param id Id ustawien
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Zwraca domyslna sciezke eksportu.
	 *
	 * @return Domyslna sciezka eksportu
	 */
	public String getDefaultExportPath() {
		return defaultExportPath;
	}

	/**
	 * Ustawia domyslna sciezke eksportu.
	 *
	 * @param defaultExportPath Domyslna sciezka eksportu
	 */
	public void setDefaultExportPath(String defaultExportPath) {
		this.defaultExportPath = defaultExportPath;
	}

	/**
	 * Zwraca numer alarmu.
	 *
	 * @return Numer alarmu
	 */
	public int getAlarmSound() {
		return alarmSound;
	}

	/**
	 * Ustawia numer alarmu.
	 *
	 * @param alarmSound Numer alarmu
	 */
	public void setAlarmSound(int alarmSound) {
		this.alarmSound = alarmSound;
	}

	/**
	 * Zwraca numer wygladu programu.
	 *
	 * @return Numer wygladu programu
	 */
	public int getLookAndFeelNumber() {
		return lookAndFeelNumber;
	}

	/**
	 * Ustawia numer wygladu programu.
	 *
	 * @param lookAndFeelNumber Numer wygladu programu
	 */
	public void setLookAndFeelNumber(int lookAndFeelNumber) {
		this.lookAndFeelNumber = lookAndFeelNumber;
	}
	
	
	
}
