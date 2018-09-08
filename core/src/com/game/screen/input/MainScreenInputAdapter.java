package com.game.screen.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.game.ShinxDefends;
import com.game.helper.Assets;
import com.game.helper.AudioManager;
import com.game.screen.MainScreen;

public class MainScreenInputAdapter extends InputAdapter implements Disposable
{
    MainScreen m_screen;
    private boolean dragged = false;

    public MainScreenInputAdapter(MainScreen gs)
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
	Rectangle newGameButton = new Rectangle(129,597,462,698);
	if (newGameButton.contains(screenX, screenY))
	{
	    AudioManager.instance.play(Assets.assetManager.get(Assets.INTRO_MUSIC, Music.class));
	    ShinxDefends.GAME_SCREEN.init();
            ShinxDefends.m_shinxDefends.setScreen(ShinxDefends.GAME_SCREEN);
	}
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
