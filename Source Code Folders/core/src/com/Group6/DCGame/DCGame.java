package com.Group6.DCGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.Group6.Screens.SplashScreen;
import com.Group6.DCHelpers.AssetLoader;
//Code by - Caleb Roark
public class DCGame extends Game {
	public Connection con = null;
	//Had to use admin as to connect to MS SQL server, it wouldn't allow me otherwise.
	public DCGame() throws SQLException, ClassNotFoundException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;databaseName=DCGame;username=admin;password=password;");
	}
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
