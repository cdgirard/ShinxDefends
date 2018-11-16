package com.game.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.game.helper.AudioManager;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * This should track the location of the lights for one of
 * the next beats (aka Aliens) that is approaching.
 * @author cdgira
 *
 */
public class Beat
{
    Color[] colors = {Color.PURPLE, Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN, 
	    Color.BROWN, Color.FIREBRICK, Color.GOLDENROD, Color.MAROON, Color.FOREST};
    // TODO: Not sure if we need this given all movement plotted relative to endPoint
    Vector2[] startPoints = {new Vector2(240,460), new Vector2(1010, 460), new Vector2(625,175), new Vector2(300,460),
	    new Vector2(300,460), new Vector2(300,460), new Vector2(300,460), new Vector2(300,460),
	    new Vector2(300,460), new Vector2(300,460)
    };
    
    Vector2[] endPoints = {new Vector2(440,460), new Vector2(810,460), new Vector2(625,275), new Vector2(440,460), 
	    new Vector2(440,460), new Vector2(440,460), new Vector2(440,460), new Vector2(440,460),
	    new Vector2(440,460), new Vector2(440,460) 
    };
    
    private Light approachLight;
    private Light beatLight;
    
    /**
     * Beat type, not sure if keeping this here yet.
     */
    private int type;
    /**
     * When we started tracking the beat.
     */
    private float start;
    /**
     * When the beat should go off.
     */
    private float end;
    
    /**
     * Has the beat light been shown.
     */
    public boolean fired = false;
    
    public boolean finished = false;
    
    /**
     * 
     * @param rayHandler
     * @param s
     * @param e
     */
    public Beat(RayHandler rayHandler, float s, float e, int t)
    {
	start = s;
	end = e;
        type = t;
        // TODO: Needs a set start point for each type and end point.
        Vector2 startPoint = startPoints[type];
        Vector2 endPoint = endPoints[type];
	approachLight = new PointLight(rayHandler, 100, Color.WHITE, 0f, startPoint.x, startPoint.y);
	beatLight = new PointLight(rayHandler, 100, colors[type], 0f, endPoint.x, endPoint.y);
    }
    
    public void update(float deltaTime)
    {
	float scale = 0f;
	
	if (finished)
	    return;
	
	if (!fired)
	{
	    float placeInMusic = AudioManager.instance.getPlaceInMusic();
	
	    float distance = (end - placeInMusic);
	    scale = distance/(end - start);
	    //System.out.println("Approaching: "+placeInMusic+" : "+start+" : "+end+" : "+scale);
	}
	
	if (scale > 0.001)
	{

	    approachLight.setDistance(50f);
	    if (endPoints[type].x == 625)
	    {
		approachLight.setPosition(startPoints[type].x, endPoints[type].y-scale*100);
	    }
	    else
	    {
	        if (endPoints[type].x > 700)
		    approachLight.setPosition(endPoints[type].x+scale*200, approachLight.getY());
	        else
	            approachLight.setPosition(endPoints[type].x-scale*200, approachLight.getY());
	    }
	}
	else if (((int)beatLight.getDistance() == 0) && (!fired))
	{    
	    approachLight.setDistance(0f);
	    beatLight.setDistance(100f);
	    fired = true;
	}
	else if (beatLight.getDistance() > 1)
	{
	     beatLight.setDistance(beatLight.getDistance()*0.95f);
	}
	else
	{
	    finished = true;
	}
    }
    
    public void dispose()
    {
	System.out.println("Disposed");
	beatLight.remove();
	approachLight.remove();
    }

}
