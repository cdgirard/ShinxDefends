package com.game.screen;

import box2dLight.ConeLight;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.game.helper.Assets;

public class MainScreen extends SizableScreen
{
    RayHandler rayHandler;
    Array<Light> fireFlies = new Array<Light>();
    Array<Light> stars = new Array<Light>();
    World world;
    static final int MAX_LIGHT_DIST = 200;

    private Skin buttonSkin = new Skin();

    private OrthographicCamera m_cam;

    private Stage m_stage = null;

    private MainScreenController m_controller;

    private ImageButton m_startNewGameButton, m_loadGameButton;

    private Texture m_background;

    // TODO: Needs a better name.
    private Skin skinLibgdx = new Skin(Gdx.files.internal("ui/clean-crispy-ui.json"));

    SpriteBatch batcher;

    public MainScreen()
    {
	m_background = Assets.assetManager.get(Assets.MAIN_SCREEN, Texture.class);
	preferredWidth = m_background.getWidth();
	preferredHeight = m_background.getHeight();

	Gdx.graphics.setWindowedMode(preferredWidth, preferredHeight);

	//MainScreenInputHandler.initializeInstance();

	rebuildUI();

	m_cam = new OrthographicCamera(preferredWidth, preferredHeight);
	m_cam.update();

	m_controller = new MainScreenController();

	batcher = new SpriteBatch();

	//AudioManager.instance.play(Assets.assetManager.get(Assets.INTRO_MUSIC,Music.class));

	world = new World(new Vector2(0, 0), true);

	RayHandler.setGammaCorrection(true);
	RayHandler.useDiffuseLight(true);

	rayHandler = new RayHandler(world);
	rayHandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.5f);
	rayHandler.setBlurNum(3);

	// Fire Flies
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 50f, 975f, 533f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 100f, 1055f, 530f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 150f, 1234f, 531f));

	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 200f, 61f, 597f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 50f, 167f, 610f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 100f, 264f, 621f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 150f, 958f, 619f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 200f, 1065f, 682f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 50f, 1200f, 652f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 100f, 1309f, 645f));

	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 150f, 56f, 748f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 200f, 117f, 729f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 50f, 235f, 757f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 100f, 375f, 713f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 150f, 587f, 727f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 200f, 641f, 761f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 50f, 1103f, 774f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 100f, 1249f, 747f));

	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 150f, 291f, 846f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 200f, 444f, 794f));
	fireFlies.add(new PointLight(rayHandler, 100, Color.YELLOW, 50f, 1234f, 809f));
	
	// Stars
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 638f, 20f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 277f, 42f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 824f, 54f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 1193f, 75f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 370f, 79f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 147f, 87f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 175f, 114f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 894f, 117f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 1044f, 119f));
	
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 202f, 154f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 318f, 159f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 36f, 190f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 427f, 191f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 1069f, 226f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 1182f, 210f));
	
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 850f, 261f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 415f, 268f));
	
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 861f, 307f));
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 75f, 329f));
	
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 1016f, 403f));
	
	stars.add(new PointLight(rayHandler, 100, Color.WHITE, 50f, 541f, 577f));	
	
	//light = new ConeLight(rayHandler, 1000, Color.WHITE, 1000f, 640f, 75f,90f,30f);
	// conelight.setStaticLight(true);
    }

    /**
     * Need this between games to make sure the HighScore Table is
     * updated.
     */
    public void rebuildUI()
    {
	Gdx.app.log("MainScreen", "Rebuild UI: " + preferredWidth + " " + preferredHeight);
	if (m_stage != null)
	    m_stage.clear();

	m_stage = new Stage();
	m_stage.getViewport().update(preferredWidth, preferredHeight, true);

    }

    /**
     * TODO: Can this be replaced with the method in UIHelper. Do I want the buttonSkin mixed with buttons from MainScreen and GameScreen.
     * @param name
     * @return
     */
    private ImageButton constructButton(String name)
    {
	ImageButtonStyle imgButtonStyle = new ImageButtonStyle();
	imgButtonStyle.up = buttonSkin.newDrawable(name);
	imgButtonStyle.down = buttonSkin.newDrawable(name);
	imgButtonStyle.checked = buttonSkin.newDrawable(name);
	imgButtonStyle.over = buttonSkin.newDrawable(name);
	ImageButton button = new ImageButton(imgButtonStyle);
	// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
	// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
	// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
	// revert the checked state.
	//button.addListener(MainScreenInputHandler.getInstance());
	button.setName(name);
	return button;
    }

    public void update(float delta)
    {
	for (Light l : fireFlies)
	{
	    l.setDistance(l.getDistance() * 0.99f);
	    if (l.getDistance() < 5)
	    {
		int y = MathUtils.random(0, MAX_LIGHT_DIST);
		l.setDistance(y);
	    }
	}

    }

    @Override
    public void render(float delta)
    {

	update(delta);

	Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1);
	Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	m_cam.update();

	// Attach batcher to camera
	batcher.setProjectionMatrix(m_cam.combined);
	batcher.disableBlending();
	batcher.begin();
	batcher.draw(m_background, 0, 0, m_background.getWidth(), m_background.getHeight(), 0, 0, m_background.getWidth(), m_background.getHeight(), false, true);
	batcher.enableBlending();
	batcher.end();

	m_controller.render(delta);

	rayHandler.setCombinedMatrix(m_cam);
	rayHandler.update();
	rayHandler.render();

	m_stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	m_stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
	Gdx.graphics.setWindowedMode(width, height);

	m_cam.setToOrtho(true, width, height);
	m_stage.getViewport().update(width, height, true);

	Gdx.app.log("MainScreen", "resizing:" + width + " , " + height);
    }

    @Override
    public void show()
    {
	// InputProcessor inputProcessorOne = MainScreenInputHandler.getInstance();
	InputProcessor inputProcessorTwo = m_stage;
	InputMultiplexer inputMultiplexer = new InputMultiplexer();
	//  inputMultiplexer.addProcessor(inputProcessorOne);
	inputMultiplexer.addProcessor(inputProcessorTwo);
	Gdx.input.setInputProcessor(inputMultiplexer);
	Gdx.app.log("MainScreen", "show called");
    }

    @Override
    public void hide()
    {
	Gdx.input.setInputProcessor(null);
	Gdx.app.log("MainScreen", "hide called");
    }

    @Override
    public void pause()
    {
	Gdx.app.log("MainScreen", "pause called");
    }

    @Override
    public void resume()
    {
	Gdx.app.log("MainScreen", "resume called");
    }

    @Override
    public void dispose()
    {
	// Leave blank
    }

}
