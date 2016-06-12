package com.goldentwo.service;

import com.goldentwo.data.Settings.Settings;

/**
 * Interfejs definiujacy wszystkie meotdy zwiazane z logika ustawien.
 */
public interface SettingsService {
	
	/**
	 * Zwraca obiekt ustawien pobrany z bazy, w przypadku gdy tych ustawien nie ma jeszcze w bazie, stworzy je.
	 *
	 * @return Obiekt ustawien
	 */
	Settings getSettings();
	
	/**
	 * Aktualizuje ustawienia w repozytorium.
	 *
	 * @param settings Obiekt ustawien
	 * @return Status powodzenia
	 */
	boolean updateSettings(Settings settings);
}
