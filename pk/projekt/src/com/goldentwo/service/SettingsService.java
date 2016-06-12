package com.goldentwo.service;

import com.goldentwo.data.Settings.Settings;

public interface SettingsService {
	Settings getSettings();
	boolean updateSettings(Settings settings);
}
