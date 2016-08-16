package com.Group6.Screens;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.Group6.DCGame.DCGame;
//Code Author: Stephen Wilson
public class LoginScreen implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextField LoginField;
	private TextField PassField;
	private Stage stage;
	private Skin skin;
	private Label WelcomeLabel;
	private Label LoginLabel;
	private Label PassLabel;
	private Label ErrorLabel;
	private TextButton LoginButton;
	private TextButton RegisterButton;
	private DCGame game;
	public static String username;
	public LoginScreen(DCGame game){
		this.game = game;
    }
	
	@Override
    public void show() {        
    	camera = new OrthographicCamera(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage();
        stage.getCamera().position.set(180,320,0);
        WelcomeLabel = new Label("Death Crow Login", skin);
        WelcomeLabel.setWidth(125f);
        WelcomeLabel.setHeight(20f);
        WelcomeLabel.setPosition(Gdx.graphics.getWidth()/2 - 70, Gdx.graphics.getHeight()/2 + 280);
        stage.addActor(WelcomeLabel);
        LoginLabel = new Label("Username",skin);
        LoginLabel.setWidth(200f);
        LoginLabel.setHeight(20f);
        LoginLabel.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 195);
        stage.addActor(LoginLabel);
        LoginField = new TextField("",skin); LoginField.setWidth(200f); LoginField.setHeight(20f);
        LoginField.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 175);
        stage.addActor(LoginField);
        PassLabel = new Label("Password",skin);
        PassLabel.setWidth(200f); PassLabel.setHeight(20f); 
        PassLabel.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 150);
        stage.addActor(PassLabel);
        PassField = new TextField("",skin); PassField.setWidth(200f); PassField.setHeight(20f);
        PassField.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 125);
        PassField.setPasswordMode(true);
        PassField.setPasswordCharacter('*');
        stage.addActor(PassField);
        ErrorLabel = new Label("",skin); ErrorLabel.setWidth(200f); ErrorLabel.setHeight(20f);
        ErrorLabel.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 50);
        stage.addActor(ErrorLabel);
        LoginButton = new TextButton("Login",skin,"default");
        LoginButton.setWidth(200f); LoginButton.setHeight(20f);
        LoginButton.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 100);
        LoginButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		if(LoginSuccess() == true){
        			try{
        			username = LoginField.getText();
        			game.con.close();
        			game.setScreen(new GameScreen());
        			}catch(Exception e){
        				e.printStackTrace();
        			}
        		}
        		else{
        			ErrorLabel.setText("Login/Username are incorrect");
        		}
        	}
        });
        stage.addActor(LoginButton);
        RegisterButton = new TextButton("Register",skin,"default");
        RegisterButton.setWidth(200f); RegisterButton.setHeight(20f);
        RegisterButton.setPosition(Gdx.graphics.getWidth()/2 - 90, Gdx.graphics.getHeight()/2 + 75);
        RegisterButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent even, float x, float y){
        		game.setScreen(new RegisterScreen(game));
        	}
        });
        stage.addActor(RegisterButton);
        // Wire the stage to receive input
        Gdx.input.setInputProcessor(stage);
        
    }
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    stage.draw();
	    batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	public boolean LoginSuccess()
	{
		boolean Success = false;
		ResultSet rs = null;
		try{
			PreparedStatement pstmt = game.con.prepareStatement("Select Password FROM UserAccount Where AcctName = ?");
			pstmt.setString(1,LoginField.getText().replaceAll("\\s+",""));
			rs = pstmt.executeQuery();
			while(rs.next()){
				String str2 = PassField.getText().replaceAll("\\s+", "");
				String str = rs.getString(1).replaceAll("\\s+","");
				if(str.equals(str2)){
					Success = true;
				}
			}
		
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		return Success;
	}

}
