package com.game.helper;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets
{
    // Backgrounds
    public static final String MAIN_SCREEN = "images/backgrounds/menu_screen.jpg";

    
    // Attackers

    
    // Dragons
    
    // Landscape
    
    // UI

    public static TextureAtlas buttonsAtlas;
    
    // Sounds                                         
    
    // Music
    
    public static AssetManager assetManager = new AssetManager();
    

    public static void load()
    {
        loadBackgrounds();
        loadGUIImages();
        loadDragons();
        loadAttackers();
        loadLandscape();
        loadSounds();
        loadMusic();
    }
    
    private static void loadGUIImages()
    {       
        assetManager.finishLoading();
    }
    
    private static void loadLandscape()
    {
	assetManager.finishLoading();
    }
    
    private static void loadAttackers()
    {
        assetManager.finishLoading();
    }
    
    private static void loadDragons()
    {

        assetManager.finishLoading();
    }
    
    private static void loadBackgrounds()
    {               
        assetManager.load(MAIN_SCREEN, Texture.class);     
        assetManager.finishLoading();
        
    }
    
    private static void loadSounds()
    {
	// Load Sounds
	assetManager.finishLoading();
    }
    
    private static void loadMusic()
    {
	assetManager.finishLoading();
    }
    
    
    public static void dispose()
    {
        buttonsAtlas.dispose();
        assetManager.dispose();
    }

}
