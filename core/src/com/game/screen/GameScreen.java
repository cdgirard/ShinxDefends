package com.game.screen;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

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
import com.badlogic.gdx.physics.box2d.World;
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
import com.game.helper.AudioManager;
import com.game.screen.input.GameScreenInputAdapter;

/**
 * @author cdgira
 *
 */
public class GameScreen extends SizableScreen
{
    RayHandler rayHandler;
    Light woodBeatLight, pianoBeatLight, randomBeatLight;
    World world;
    
    float[] woodBeats = {0.82f, 2.42f, 4.02f, 5.62f, 7.2f, 8.82f, 10.42f, 12.02f, 30f};
    float[] pianoBeats = {0.29f, 2.42f, 3.23f, 6.43f, 9.67f, 12.8f, 30f};
    double[] randomBeats = { 15.530023,
	    15.620476,
	    15.823061,
	    16.003719,
	    16.219795,
	    16.329842,
	    16.419954,
	    16.621881,
	    16.797415,
	    16.930363,
	    17.020046,
	    17.221270,
	    17.424740,
	    17.530001,
	    17.616690,
	    17.826962,
	    18.019773,
	    18.130772,
	    18.225578,
	    18.398050,
	    18.730816,
	    18.820408,
	    19.027029,
	    19.200726,
	    19.620226,
	    19.820431,
	    19.998888,
	    20.420385,
	    20.624649,
	    20.731586,
	    20.810453,
	    21.220226,
	    21.330929,
	    21.423855,
	    21.596758,
	    21.929615,
	    22.017845,
	    22.227280,
	    22.404581,
	    24.026598,
	    24.225555,
	    24.326780,
	    24.429138,
	    24.627573,
	    24.695465,
	    24.761837,
	    24.830681,
	    24.929501,
	    25.030636,
	    25.231405,
	    25.663969,
	    26.225216,
	    26.420296,
	    26.458754,
	    26.824127,
	    27.123741,
	    27.220976,
	    27.425850,
	    27.624376,
	    27.726349,
	    28.020613,
	    28.056849,
	    28.325352,
	    28.425056,
	    28.626326,
	    28.821020,
	    28.923151,
	    29.125669,
	    29.225828,
	    29.424286,
	    29.620430,
	    29.657824,
	    30.025715,
	    30.326599,
	    30.420771,
	    30.521950,
	    30.626348,
	    30.825602,
	    30.926508,
	    31.220749,
	    31.259546,
	    31.525873,
	    31.826530,
	    32.021202,
	    32.121861,
	    32.326099,
	    32.424919,
	    32.625465,
	    32.820454,
	    32.856689,
	    33.225849,
	    33.525738,
	    33.620861,
	    33.723175,
	    33.824104,
	    34.024261,
	    34.125420,
	    34.420227,
	    34.458595,
	    34.724377,
	    35.026756,
	    35.221066,
	    35.322742,
	    35.524967,
	    35.625874,
	    35.826328,
	    36.020432,
	    36.059162,
	    36.424740,
	    36.726124,
	    36.820908,
	    36.922153,
	    37.025215,
	    37.224083,
	    37.324715,
	    37.620430,
	    37.657120};
    int placeWood = 0;
    int placePiano = 0;
    int placeRandom = 0;
    
    public boolean m_paused = false;
    public boolean m_endGame = false;

    private Texture m_background;

    public float xScale = 0;
    public float yScale = 0;

    SpriteBatch batcher;

    private OrthographicCamera m_uiCam;

    public GameScreenController controller;
    
    private GameScreenInputAdapter inputProcessor;

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
	
	world = new World(new Vector2(0, 0), true);

	RayHandler.setGammaCorrection(true);
	RayHandler.useDiffuseLight(true);

	rayHandler = new RayHandler(world);
	rayHandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.5f);
	rayHandler.setBlurNum(3);
	
	woodBeatLight = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 400f, 400f);
	pianoBeatLight = new PointLight(rayHandler, 100, Color.PURPLE, 0f, 600f, 400f);
	randomBeatLight = new PointLight(rayHandler, 100, Color.RED, 0f, 200f, 400f);
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

    public void update(float delta)
    {
	float placeInMusic = AudioManager.instance.getPlaceInMusic();
	if (placeInMusic > woodBeats[placeWood]) 
	{
	    woodBeatLight.setDistance(100f);
	    placeWood++;
	}
	else 
	{
	    woodBeatLight.setDistance(woodBeatLight.getDistance()*0.97f);
	}
	
	if (placeInMusic > pianoBeats[placePiano])
	{
	    pianoBeatLight.setDistance(100f);
	    placePiano++;
	    //System.out.println("P: "+placeInMusic);
	}
	else 
	{
	    pianoBeatLight.setDistance(pianoBeatLight.getDistance()*0.95f);
	}
	
	if (placeInMusic > randomBeats[placeRandom])
	{
	    randomBeatLight.setDistance(100f);
	    placeRandom++;
	    //System.out.println("P: "+placeInMusic);
	}
	else 
	{
	    randomBeatLight.setDistance(randomBeatLight.getDistance()*0.5f);
	}

    }
    
    /**
     * TODO: Potential refactor as near duplicate with MainScreen
     */
    @Override
    public void render(float delta)
    {
	// Update First
	update(delta);
	
	// Then Render
	Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1);
	Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	renderBackground();
	
	controller.render(delta);

	renderGui();
	
	rayHandler.setCombinedMatrix(m_uiCam);
	rayHandler.update();
	rayHandler.render();
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
	InputMultiplexer inputMultiplexer = new InputMultiplexer();
	//  inputMultiplexer.addProcessor(inputProcessorOne);
	//inputMultiplexer.addProcessor(inputProcessorTwo);
	inputProcessor = new GameScreenInputAdapter(this);
	inputMultiplexer.addProcessor(inputProcessor);
	Gdx.input.setInputProcessor(inputMultiplexer);
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
