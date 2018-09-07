package com.game.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.game.obj.AbstractGameObject;


/**
 * Job of this class is to manage all the non-ui objects
 * in the game screen.
 * @author cdgira
 *
 */
public class MainScreenController
{
    public static final float VIEWPORT_WIDTH = 9.7f;
    public static final float VIEWPORT_HEIGHT = 7.6f;
    
    public World world;
 
    private OrthographicCamera m_gameCam;
    
    private SpriteBatch batcher;
    
    private float gameTime = 0;
    
    // For Box2D Debugging
    private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
    private Box2DDebugRenderer b2DebugRenderer;
    
    public MainScreenController()
    {
	// Setup the Cameras
	m_gameCam = new OrthographicCamera();
	m_gameCam.setToOrtho(true, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	
	batcher = new SpriteBatch();
	
	if (world != null)
	    world.dispose();
	world = new World(new Vector2(0, 9.81f), true);
        
        //RayHandler.setGammaCorrection(true);
        //RayHandler.useDiffuseLight(true);
        
        //rayHandler = new RayHandler(world);
        //rayHandler.setAmbientLight(0.3f, 0.3f, 0.3f, 0.5f);
        //rayHandler.setBlurNum(3);
        
        // May need to move this due to new camera.
        //rightLight = new ConeLight(rayHandler, 1000, Color.WHITE, 500f, -1f, 3.5f,0f,30f);
        //leftLight = new ConeLight(rayHandler, 1000, Color.WHITE, 500f, 10.7f, 3.5f,180f,30f);
        
	b2DebugRenderer = new Box2DDebugRenderer();
    }
    
    public void init()
    {

    }
    
    /**
     * TODO: Not implemented.
     * @param obj
     */
    public void flagForRemoval(AbstractGameObject obj)
    {

    }
    
    public void render(float delta)
    {
	if (gameTime > 1)
	{
	    //Gdx.app.log("MainScreen", "Arrays:"+m_attackers.size+" , "+m_droppingDragons.size+" : "+m_objectsToRemove.size);
	    gameTime = 0;
	}
	gameTime += delta;
	m_gameCam.update();
	
	batcher.setProjectionMatrix(m_gameCam.combined);
	batcher.begin();

	batcher.end();
	
        //rayHandler.setCombinedMatrix(m_gameCam);
        //rayHandler.update();
        //rayHandler.render();
	
	if (DEBUG_DRAW_BOX2D_WORLD)
	{
	    b2DebugRenderer.render(world, m_gameCam.combined);
	}
    }
    
    /**
     * Update the present state of all objects in the game before 
     * rendering everything.
     * @param delta
     */
    public void update(float delta)
    {
	
    }

}
