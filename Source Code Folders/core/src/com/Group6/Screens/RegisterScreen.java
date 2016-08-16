package com.Group6.Screens;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.Group6.DCGame.DCGame;
//Code Author: Stephen Wilson
public class RegisterScreen implements Screen {
	private DCGame game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	private Label AcctLabel;
	private TextField AcctNameField;
	private Label PasswordLabel;
	private TextField PassField;
	private Label RePassLabel;
	private TextField RePassField;
	private Label EmailLabel;
	private TextField EmailField;
	private Label ErrorLabel;
	private Button RegisterButton, CancelButton;
	
	public RegisterScreen(DCGame game){
		this.game = game;
	}
	@Override
	public void show(){
	camera = new OrthographicCamera(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
	batch = new SpriteBatch();
	skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	stage = new Stage();
	//stage.setViewport(360, 640, false);
    stage.getCamera().position.set(180,320,0);
	AcctLabel = new Label("Account Name", skin);
	AcctLabel.setWidth(125f); AcctLabel.setHeight(20f);
	AcctLabel.setPosition(Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/2 + 280);
	stage.addActor(AcctLabel);
	AcctNameField = new TextField("",skin); AcctNameField.setWidth(125f); AcctNameField.setHeight(20f);
	AcctNameField.setPosition(Gdx.graphics.getWidth()/2 - 5, Gdx.graphics.getHeight()/2 + 280);
	AcctNameField.setMaxLength(13);
	stage.addActor(AcctNameField);
	PasswordLabel = new Label("Password",skin); PasswordLabel.setWidth(125f); PasswordLabel.setHeight(20f);
	PasswordLabel.setPosition(Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/2 + 255);
	stage.addActor(PasswordLabel);
	PassField = new TextField("",skin);
	PassField.setWidth(125f); PassField.setHeight(20f);
	PassField.setPosition(Gdx.graphics.getWidth()/2 - 5, Gdx.graphics.getHeight()/2 + 255);
	PassField.setPasswordMode(true); PassField.setPasswordCharacter('*');
	stage.addActor(PassField);
	RePassLabel = new Label("Re-enter Password",skin); RePassLabel.setWidth(125f); RePassLabel.setHeight(20f);
	RePassLabel.setPosition(Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/2 + 230);
	stage.addActor(RePassLabel);
	RePassField = new TextField("",skin); RePassField.setWidth(125f); RePassField.setHeight(20f);
	RePassField.setPosition(Gdx.graphics.getWidth()/2 - 5, Gdx.graphics.getHeight()/2 + 230);
	RePassField.setPasswordMode(true); RePassField.setPasswordCharacter('*');
	stage.addActor(RePassField);
	EmailLabel = new Label("Email",skin); EmailLabel.setWidth(125f); EmailLabel.setHeight(20f);
	EmailLabel.setPosition(Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/2 + 205);
	stage.addActor(EmailLabel);
	EmailField = new TextField("",skin);
	EmailField.setWidth(125f); EmailField.setHeight(20f);
	EmailField.setPosition(Gdx.graphics.getWidth()/2 - 5, Gdx.graphics.getHeight()/2 + 205);
	stage.addActor(EmailField);
	ErrorLabel = new Label("",skin); ErrorLabel.setWidth(125f); ErrorLabel.setHeight(20f);
	ErrorLabel.setPosition(Gdx.graphics.getWidth()/2 - 70, Gdx.graphics.getHeight()/2 + 155);
	stage.addActor(ErrorLabel);
	RegisterButton = new TextButton("Register",skin,"default");
	RegisterButton.setWidth(125f); RegisterButton.setHeight(20f);
	RegisterButton.setPosition(Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/2 + 180);
	RegisterButton.addListener(new ClickListener(){
		@Override
		public void clicked(InputEvent event, float x, float y){
			if(RegisterSuccess() == true)
			game.setScreen(new LoginScreen(game));
			else
			{
				//Print to error label scheme
			}
		}
	});	
	stage.addActor(RegisterButton);
	CancelButton = new TextButton("Cancel",skin,"default");
	CancelButton.setWidth(125f); CancelButton.setHeight(20f);
	CancelButton.setPosition(Gdx.graphics.getWidth()/2 - 5, Gdx.graphics.getHeight()/2 + 180);
	CancelButton.addListener(new ClickListener(){
		@Override
		public void clicked(InputEvent event, float x, float y){
			game.setScreen(new LoginScreen(game));
		}
	});
	stage.addActor(CancelButton);
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
public void hide(){
	
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
public boolean RegisterSuccess(){
	boolean success = true; 
	if(!EmailField.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") || EmailField.getText().length() == 0){
		ErrorLabel.setText("Not a valid email address");
		success = false;
	}
	if(!PassField.getText().equals(RePassField.getText()) || PassField.getText().length() == 0 || RePassField.getText().length() == 0){
		ErrorLabel.setText("Passwords input are not equal");
		success = false;
	}
	boolean taken = false;
	ResultSet rs = null;
	try{
		Statement stmt = game.con.createStatement();
		rs = stmt.executeQuery("Select AcctName FROM UserAccount");
		PreparedStatement pstmt = game.con.prepareStatement("Insert Into UserAccount (AcctName, Password, Email) Values (?,?,?,)");
		while(rs.next()){
			if(AcctNameField.getText().equals(rs.getString(1))){
				taken = true;
			}
		}
		rs.close();
		stmt.close();
		if(!taken){
			pstmt.setString(1,AcctNameField.getText());
			pstmt.setString(2, PassField.getText());
			pstmt.setString(3, EmailField.getText());
			pstmt.executeUpdate();
		}
		pstmt.close();
	}catch(SQLException se){
		se.printStackTrace();
	}
	if(taken || AcctNameField.getText().length() == 0 ){
		ErrorLabel.setText("Username is taken");
		success = false;
	}
	return success;
}
}
