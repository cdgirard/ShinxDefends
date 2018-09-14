package com.game.screen.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.game.ShinxDefends;
import com.game.helper.Assets;
import com.game.helper.AudioManager;
import com.game.screen.GameScreen;

public class GameScreenInputAdapter extends InputAdapter implements Disposable
{
    GameScreen m_screen;

    public GameScreenInputAdapter(GameScreen gs)
    {
	m_screen = gs;
    }

    /**
     * 
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {

	return true;
    }

    /**
     * Mouse button pressed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
	
	float place = AudioManager.instance.getPlaceInMusic();
	System.out.println(place);
	return false;
    }

    /**
     * Mouse button released;
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {

	return false;
    }

    @Override
    public void dispose()
    {
	// TODO Auto-generated method stub

    }

}
