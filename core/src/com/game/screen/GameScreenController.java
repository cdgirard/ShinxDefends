package com.game.screen;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

/**
 * Job of this class is to manage all the non-ui objects
 * in the game screen.
 * @author cdgira
 *
 */
public class GameScreenController
{
    public static final float VIEWPORT_WIDTH = 5.0f;
    public static final float VIEWPORT_HEIGHT = 9.0f;
    
    public World world;
    
    private OrthographicCamera m_gameCam;
    
    private SpriteBatch batcher;
    
    // For Box2D Debugging
    private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
    private Box2DDebugRenderer b2DebugRenderer;
    
    public GameScreenController()
    {
	// Setup the Cameras
	m_gameCam = new OrthographicCamera();
	m_gameCam.setToOrtho(true, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	
	batcher = new SpriteBatch();
	
	if (world != null)
	    world.dispose();
	world = new World(new Vector2(0, 9.81f), true);
	
	b2DebugRenderer = new Box2DDebugRenderer();
    }
    
    public void init()
    {
	
    }
    
    public void render(float delta)
    {
	m_gameCam.update();
	
	batcher.setProjectionMatrix(m_gameCam.combined);
	batcher.begin();

	batcher.end();
	
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
	world.step(delta, 8, 3);
    }
    



}
