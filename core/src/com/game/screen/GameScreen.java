package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.game.ShinxDefends;
import com.game.helper.Assets;

/**
 * @author cdgira
 *
 */
public class GameScreen extends SizableScreen
{
    public boolean m_paused = false;
    public boolean m_endGame = false;

    private Texture m_background;

    public float xScale = 0;
    public float yScale = 0;

    SpriteBatch batcher;

    private OrthographicCamera m_uiCam;

    public GameScreenController controller;


    public GameScreen()
    {
	// Get Initial Window Setup  
	m_background = Assets.assetManager.get(Assets.GAME_SCREEN, Texture.class);
	
	preferredWidth = m_background.getWidth();
	preferredHeight = m_background.getHeight();

	xScale = preferredWidth / GameScreenController.VIEWPORT_WIDTH;
	yScale = preferredHeight / GameScreenController.VIEWPORT_HEIGHT;

	Gdx.graphics.setWindowedMode(preferredWidth, preferredHeight);

	// Setup the Cameras
	m_uiCam = new OrthographicCamera();
	m_uiCam.setToOrtho(true, preferredWidth, preferredHeight);
	m_uiCam.update();

	batcher = new SpriteBatch();

	controller = new GameScreenController();
	
	initUI();
    }

    /**
     * Initializes all the key data values for the start of a new game.
     */
    public void init()
    {
	
    }

    /**
     * Setup the UI components for the GameScreen.
     */
    private void initUI()
    {
	
    }

    /**
     * TODO: Potential refactor as near duplicate with MainScreen
     */
    @Override
    public void render(float delta)
    {
	// Update First
	
	// Then Render
	Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1);
	Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	renderBackground();
	
	controller.render(delta);

	renderGui();
    }

    /**
     * Switches back to the Main Menu Screen.
     */
    public void endGame()
    {
	//AudioManager.instance.play(Assets.assetManager.get(Assets.INTRO_MUSIC, Music.class));
	ShinxDefends.m_shinxDefends.setScreen(ShinxDefends.MAIN_SCREEN);
	
    }

    private void updateGui(float delta)
    {
    }

    private void renderBackground()
    {
	batcher.setProjectionMatrix(m_uiCam.combined);
	batcher.begin();
	
	batcher.draw(m_background, 0, 0, m_background.getWidth(), m_background.getHeight(), 0, 0, m_background.getWidth(), m_background.getHeight(), false, true);
	batcher.end();
    }
    
    /**
     * Draw the GUI layer.
     */
    private void renderGui()
    {
	batcher.setProjectionMatrix(m_uiCam.combined);
	batcher.begin();

	batcher.end();
    }

    @Override
    public void resize(int width, int height)
    {
	Gdx.graphics.setWindowedMode(width, height);

	xScale = preferredWidth / GameScreenController.VIEWPORT_WIDTH;
	yScale = preferredHeight / GameScreenController.VIEWPORT_HEIGHT;

	m_uiCam.setToOrtho(true, width, height);
	// m_stage.getViewport().update(width, height, true);
	Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show()
    {
	// InputProcessor inputProcessorOne = MainScreenInputHandler.getInstance();
	// InputProcessor inputProcessorTwo = m_stage;
	//InputMultiplexer inputMultiplexer = new InputMultiplexer();
	//  inputMultiplexer.addProcessor(inputProcessorOne);
	//inputMultiplexer.addProcessor(inputProcessorTwo);
	//inputProcessor = new GameScreenInputAdapter(this);
	//inputMultiplexer.addProcessor(inputProcessor);
	//Gdx.input.setInputProcessor(inputMultiplexer);
	Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide()
    {
	Gdx.input.setInputProcessor(null);
	Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause()
    {
	Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume()
    {
	Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose()
    {
	// Leave blank
    }
}