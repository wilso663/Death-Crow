package com.Group6.GameWorld;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.Group6.DCHelpers.AssetLoader;
import com.Group6.DCHelpers.InputHandler;
import com.Group6.GameObjects.Bird;
import com.Group6.GameObjects.Grass;
import com.Group6.GameObjects.Pipe;
import com.Group6.GameObjects.ScrollHandler;
import com.Group6.TweenAccessors.Value;
import com.Group6.TweenAccessors.ValueAccessor;
import com.Group6.ui.SimpleButton;
// Code Author: Stephen Wilson
public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointY;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;

	// Game Assets
	private TextureRegion bg, grass, birdMid, skullUp, skullDown, bar, ready,
			dcLogo, gameOver, highScore, scoreboard, star, noStar, retry;
	private Animation birdAnimation;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	private List<SimpleButton> menuButtons;
	private Color transitionColor;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;

		this.midPointY = midPointY;
		this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getMenuButtons();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();

		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
	}

	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
		ready = AssetLoader.ready;
		dcLogo = AssetLoader.dcLogo;
		gameOver = AssetLoader.gameOver;
		highScore = AssetLoader.highScore;
		scoreboard = AssetLoader.scoreboard;
		retry = AssetLoader.retry;
		star = AssetLoader.star;
		noStar = AssetLoader.noStar;
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
				frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(),
				backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawSkulls() {

		batcher.draw(skullUp, pipe1.getX() - 1,
				pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe1.getX() - 1,
				pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe3.getX() - 1,
				pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe3.getX() - 1,
				pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}

	private void drawPipes() {
		batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
				pipe1.getHeight());
		batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
				pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

		batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
				pipe2.getHeight());
		batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
				pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

		batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
				pipe3.getHeight());
		batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
				pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
	}

	private void drawBirdCentered(float runTime) {
		batcher.draw(birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15,
				bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
				bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
	}

	private void drawBird(float runTime) {

		if (bird.shouldntFlap()) {
			batcher.draw(birdMid, bird.getX(), bird.getY(),
					bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
					bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

		} else {
			batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(),
					bird.getY(), bird.getWidth() / 2.0f,
					bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
					1, 1, bird.getRotation());
		}

	}

	private void drawMenuUI() {
		batcher.draw(dcLogo, 136 / 2 - 56, midPointY - 50,
				dcLogo.getRegionWidth() / 1.2f, dcLogo.getRegionHeight() / 1.2f);

		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}

	}

	
	private void drawScoreboard() {
		batcher.draw(scoreboard, 22, midPointY - 30, 97, 72);

		batcher.draw(noStar, 25, midPointY - 15, 10, 10);
		batcher.draw(noStar, 37, midPointY - 15, 10, 10);
		batcher.draw(noStar, 49, midPointY - 15, 10, 10);
		batcher.draw(noStar, 61, midPointY - 15, 10, 10);
		batcher.draw(noStar, 73, midPointY - 15, 10, 10);

		if (myWorld.getScore() > 2) {
			batcher.draw(star, 73, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 17) {
			batcher.draw(star, 61, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 50) {
			batcher.draw(star, 49, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 80) {
			batcher.draw(star, 37, midPointY - 15, 10, 10);
		}

		if (myWorld.getScore() > 120) {
			batcher.draw(star, 25, midPointY - 15, 10, 10);
		}

		int length = ("" + myWorld.getScore()).length();

		AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
				104 - (2 * length), midPointY - 20);
		ArrayList<String> str = new ArrayList<String>();
		ArrayList<Integer> num = new ArrayList<Integer>();
		ResultSet rs = null;
		ResultSet rs2 = null;
		try{
			PreparedStatement pstmt = myWorld.con.prepareStatement("Select PHScore From PlayerHighScores Where AcctName = ?");
			pstmt.setString(1, myWorld.username);
			rs = pstmt.executeQuery();
			int PlayerHighScore = 0;
			while(rs.next()){
				PlayerHighScore = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			Statement stmt = myWorld.con.createStatement();
			rs2 = stmt.executeQuery("Select * From GlobalHighScores ORDER BY GHScore DESC");
			while(rs2.next()){
				num.add(rs2.getInt(1));
				str.add(rs2.getString(2));
			}
			rs2.close();
			stmt.close();
		
		int length2 = ("" + PlayerHighScore).length();
		AssetLoader.whiteFont.draw(batcher, "" + PlayerHighScore,
				104 - (2.5f * length2), midPointY - 3);
		int FirstNameLength = (str.get(0).length() + 3);
		int FirstScoreLength = ("" + num.get(0)).length();
		AssetLoader.whiteFont.draw(batcher,"1. " + str.get(0), 
				60 - (2.5f * FirstNameLength), midPointY + 15);
		AssetLoader.whiteFont.draw(batcher, "" + num.get(0),
				104 - (2.5f * FirstScoreLength), midPointY + 15);
		int SecondNameLength = (str.get(1).length() + 3);
		int SecondScoreLength = ("" + num.get(1)).length();
		AssetLoader.whiteFont.draw(batcher, "2. " + str.get(1), 
				60 - (2.5f * SecondNameLength), midPointY + 25);
		AssetLoader.whiteFont.draw(batcher, "" + num.get(1),
				104 - (2.5f * SecondScoreLength), midPointY + 25);
		int ThirdNameLength = ("" + str.get(2)).length() + 3;
		int ThirdScoreLength = ("" + num.get(2)).length();
		AssetLoader.whiteFont.draw(batcher,"3. " + str.get(2),
				60 - (2.5f * ThirdNameLength), midPointY + 34);
		AssetLoader.whiteFont.draw(batcher, "" + num.get(2),
				104 - (2.5f * ThirdScoreLength), midPointY + 34);
		}catch(SQLException se){
			se.printStackTrace();
		}
		

	}

	private void drawRetry() {
		batcher.draw(retry, 36, midPointY + 48, 66, 14);
	}

	private void drawReady() {
		batcher.draw(ready, 36, midPointY - 50, 68, 14);
	}

	private void drawGameOver() {
		batcher.draw(gameOver, 24, midPointY - 50, 92, 14);
	}

	private void drawScore() {
		int length = ("" + myWorld.getScore()).length();
		AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
				68 - (3 * length), midPointY - 82);
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
				68 - (3 * length), midPointY - 83);
	}

	private void drawHighScore() {
		batcher.draw(highScore, 22, midPointY - 50, 96, 14);
	}

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midPointY + 66);

		// Draw Grass
		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 66, 136, 11);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 77, 136, 52);

		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();

		batcher.draw(bg, 0, midPointY + 23, 136, 43);

		drawPipes();

		batcher.enableBlending();
		drawSkulls();

		if (myWorld.isRunning()) {
			drawBird(runTime);
			drawScore();
		} else if (myWorld.isReady()) {
			drawBird(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawBirdCentered(runTime);
			drawMenuUI();
		} else if (myWorld.isGameOver()) {
			drawScoreboard();
			drawBird(runTime);
			drawGameOver();
			drawRetry();
		} else if (myWorld.isHighScore()) {
			drawScoreboard();
			drawBird(runTime);
			drawHighScore();
			drawRetry();
		}

		drawGrass();

		batcher.end();
		drawTransition(delta);

	}

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g,
					transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, 136, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

}