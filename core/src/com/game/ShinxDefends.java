package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.game.helper.Assets;
import com.game.screen.MainScreen;
import com.game.screen.SizableScreen;

public class ShinxDefends extends ApplicationAdapter
{
    public static MainScreen MAIN_SCREEN;
    
    private SizableScreen screen;

    @Override
    public void create()
    {
	Assets.load();
	MAIN_SCREEN = new MainScreen();
	setScreen(MAIN_SCREEN);
    }

    @Override
    public void dispose()
    {
	if (screen != null)
	    screen.hide();
	// AssetLoader.dispose();
    }

    @Override
    public void pause()
    {
	if (screen != null)
	    screen.pause();
    }

    @Override
    public void resume()
    {
	if (screen != null)
	    screen.resume();
    }

    @Override
    public void render()
    {
	if (screen != null)
	    screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height)
    {
	if (screen != null)
	    screen.resize(width, height);
    }

    /**
     * Sets the current screen. {@link Screen#hide()} is called on any old
     * screen, and {@link Screen#show()} is called on the new screen, if any.
     * 
     * @param screen
     *            may be {@code null}
     */
    public void setScreen(SizableScreen screen)
    {
	if (this.screen != null)
	    this.screen.hide();
	this.screen = screen;
	if (this.screen != null)
	{
	    this.screen.show();
	    this.screen.resize();

	}
    }

    /** @return the currently active {@link Screen}. */
    public SizableScreen getScreen()
    {
	return screen;
    }
}
