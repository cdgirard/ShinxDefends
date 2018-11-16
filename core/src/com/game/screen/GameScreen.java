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
import com.game.obj.Beat;
import com.game.obj.SongData;
import com.game.screen.input.GameScreenInputAdapter;

/**
 * @author cdgira
 *
 */
public class GameScreen extends SizableScreen
{
    RayHandler rayHandler;
    Light approachLight;
    Light beatLights[] = new Light[10];
    Light runLights[] = new Light[10];
    int run, placeInRun, lastBeat = 0;
    World world;
    
    Array<Beat> beats = new Array<Beat>();
    
    //float[] woodBeats = {0.82f, 2.42f, 4.02f, 5.62f, 7.2f, 8.82f, 10.42f, 12.02f, 30f};
    //float[] pianoBeats = {0.29f, 2.42f, 3.23f, 6.43f, 9.67f, 12.8f, 30f};
	
    int placeOnset = 0;
    
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
	
	approachLight = new PointLight(rayHandler, 100, Color.WHITE, 0f, 300f, 460f);
	
	beatLights[0] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 100f, 200f);
	beatLights[1] = new PointLight(rayHandler, 100, Color.PURPLE, 0f, 440f, 460f);
	beatLights[2] = new PointLight(rayHandler, 100, Color.RED, 0f, 300f, 200f);
	beatLights[3] = new PointLight(rayHandler, 100, Color.BLUE, 0f, 400f, 200f);
	beatLights[4] = new PointLight(rayHandler, 100, Color.WHITE, 0f, 500f, 200f);
	beatLights[5] = new PointLight(rayHandler, 100, Color.GREEN, 0f, 100f, 400f);
	beatLights[6] = new PointLight(rayHandler, 100, Color.BROWN, 0f, 200f, 400f);
	beatLights[7] = new PointLight(rayHandler, 100, Color.FIREBRICK, 0f, 300f, 400f);
	beatLights[8] = new PointLight(rayHandler, 100, Color.GOLDENROD, 0f, 400f, 400f);
	beatLights[9] = new PointLight(rayHandler, 100, Color.MAROON, 0f, 500f, 400f);
	
	runLights[0] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 100f, 500f);
	runLights[1] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 150f, 500f);
	runLights[2] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 200f, 500f);
	runLights[3] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 250f, 500f);
	runLights[4] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 300f, 500f);
	runLights[5] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 350f, 500f);
	runLights[6] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 400f, 500f);
	runLights[7] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 450f, 500f);
	runLights[8] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 500f, 500f);
	runLights[9] = new PointLight(rayHandler, 100, Color.YELLOW, 0f, 550f, 500f);
	run = 0;
	placeInRun = 0;
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

    private int nextBeatOfType(int type)
    {
	int loc = placeOnset;
	SongData data = Assets.songData;
	
	while (loc < data.types.length)
	{
	    if (data.types[loc]%10 == type)
		return loc;
	    loc++;
	}
	return -1;
    }
    
    /**
     * Updates the state of the game, presently working off the music beats.
     * @param delta
     */
    public void update(float delta)
    {
	float placeInMusic = AudioManager.instance.getPlaceInMusic();
	SongData data = Assets.songData;
	
	if (data.onset[placeOnset] < placeInMusic)
	        placeOnset++;
	
	// TODO: Need to look for loc of other beat types.
	int MAX_TYPES = 10;
	for (int type=1;type<MAX_TYPES;type++)
	{
	    int loc = nextBeatOfType(type);
	
	    if (loc != -1)
	    {
	    // This makes sure there is only one beat of each "type"
	    // Going, by making sure that loc already has a beat going.
	        if (!data.beat[loc])
	        {
		    Beat b = new Beat(rayHandler, placeInMusic, data.onset[loc],type);
		
		    if (b != null)
		    {
		        beats.add(b);
		        data.beat[loc] = true;
		    }
	        }
	    }
	}
	
	Array<Beat> removeList = new Array<Beat>();
	for (Beat b : beats)
	{
	    b.update(delta);
	    if (b.finished)
	    {
		removeList.add(b);
	    }
	}
	
	for (Beat b : removeList)
	{
	    int index = beats.indexOf(b, true);
	    if (index != -1)
	    {
		b.dispose();
		beats.removeIndex(index);
	    }
	}
	if (removeList.size > 0)
	    removeList.removeRange(0, removeList.size-1);
	
	
	
//	if (placeInMusic > data.onset[placeOnset]) 
//	{
//	    beatLights[data.types[placeOnset]%10].setDistance(100f);
//	    placeOnset++;
//	}
//	else 
//	{
//	    for (int light=0;light<beatLights.length;light++)
//	    {
//	        if (beatLights[light].getDistance() > 1)
//	            beatLights[light].setDistance(beatLights[light].getDistance()*0.95f);
//	    }
//	}
	
	if (placeInMusic > data.runs[run][placeInRun])
	{
	    runLights[placeInRun].setDistance(100f);
	    placeInRun++;
	    if ((placeInRun == 10) || (placeInRun == data.runs[run].length))
	    {
		placeInRun = 0;
		run++;
	    }
	}
	else
	{
	    for (int light=0;light<runLights.length;light++)
	    {
	        if (runLights[light].getDistance() > 1)
	            runLights[light].setDistance(runLights[light].getDistance()*0.95f);
	    }
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
