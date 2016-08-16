package com.Group6.DCGame.desktop;

import java.sql.SQLException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Group6.DCGame.DCGame;
//Code Author: Stephen Wilson
public class DesktopLauncher {
	public static void main (String[] arg) throws ClassNotFoundException, SQLException {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Death Crow";
		cfg.width = 1080 / 3;
		cfg.height = 1920 / 3;
		new LwjglApplication(new DCGame(), cfg);
	}
}
