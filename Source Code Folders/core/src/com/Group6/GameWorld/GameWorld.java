package com.Group6.GameWorld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.Group6.DCHelpers.AssetLoader;
import com.Group6.GameObjects.Bird;
import com.Group6.GameObjects.ScrollHandler;
//Code Author: Stephen Wilson
public class GameWorld {
	
	public String username;
	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private float runTime = 0;
	private int midPointY;
	private GameRenderer renderer;
	public Connection con = null;
	private GameState currentState;

	public enum GameState {
		MENU, LOGIN, READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midPointY, String Username) throws SQLException, ClassNotFoundException {
		username = Username;
		currentState = GameState.MENU;
		this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 17, 12);
		// The grass should start 66 pixels below the midPointY
		scroller = new ScrollHandler(this, midPointY + 66);
		ground = new Rectangle(0, midPointY + 66, 137, 11);
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection("jdbc:sqlserver://localhost\\:1433;databaseName=DCGame;username=admin;password=password;");
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case READY:
		case MENU:
			updateReady(delta);
			break;

		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}

	}

	private void updateReady(float delta) {
		bird.updateReady(runTime);
		scroller.updateReady(delta);
	}

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) {
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
			renderer.prepareTransition(255, 255, 255, .3f);

			AssetLoader.fall.play();
		}

		if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {

			if (bird.isAlive()) {
				AssetLoader.dead.play();
				renderer.prepareTransition(255, 255, 255, .3f);

				bird.die();
			}

			scroller.stop();
			bird.decelerate();
			insertScore();
			currentState = GameState.GAMEOVER;

			if (score > getHighScore()) {
				currentState = GameState.HIGHSCORE;
			}
		}
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String name){
		username = name;
	}
	public Bird getBird() {
		return bird;

	}

	public int getMidPointY() {
		return midPointY;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void ready() {
		currentState = GameState.READY;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	public void restart() {
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		ready();
	}
	public void insertScore(){
		try{
			PreparedStatement pstmt = con.prepareStatement("Insert INTO Game (Score, StarRating, AcctName) Values(?,?,?)");
			pstmt.setInt(1, score);
			pstmt.setInt(2,0);
			pstmt.setString(3, username);
			pstmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	public int getHighScore(){
		int HighScore = 0;
		try{
			ResultSet rs = null;
			PreparedStatement pstmt = con.prepareStatement("Select PHScore From PlayerHighScores WHERE AcctName = ?");
			pstmt.setString(1,username);
			rs = pstmt.executeQuery();
			while(rs.next()){
				HighScore = rs.getInt(1);
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		return HighScore;
	}
	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

}
