package com.game.obj;

import com.badlogic.gdx.graphics.Color;
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
    private Light approachLight;
    private Light beatLight;
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
    public Beat(RayHandler rayHandler, float s, float e)
    {
	start = s;
	end = e;

	approachLight = new PointLight(rayHandler, 100, Color.WHITE, 0f, 300f, 460f);
	beatLight = new PointLight(rayHandler, 100, Color.PURPLE, 0f, 440f, 460f);
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
	    approachLight.setPosition(440-scale*200, approachLight.getY());
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
