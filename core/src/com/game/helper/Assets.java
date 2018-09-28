package com.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.game.obj.SongData;

public class Assets
{
    // Backgrounds
    public static final String MAIN_SCREEN = "images/backgrounds/menu_screen.jpg";
    public static final String GAME_SCREEN = "images/backgrounds/game_screen.png";

    // Attackers

    // Dragons
    
    // Landscape
    
    // UI

    public static TextureAtlas buttonsAtlas;
    
    // Sounds                                         
    
    // Music
    public static final String CHIME_SONG = "music/chime_phototropic.wav";
    public static final String CHIME_DATA = "game_data/songData.dat";
    
    public static AssetManager assetManager = new AssetManager();
    public static SongData songData;
    

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
        assetManager.load(GAME_SCREEN, Texture.class);
        assetManager.finishLoading();
        
    }
    
    private static void loadSounds()
    {
	// Load Sounds
	assetManager.finishLoading();
    }
    
    private static void loadMusic()
    {
	assetManager.load(CHIME_SONG, Music.class);
	assetManager.finishLoading();
	
	songData = SongDataFileLoader.loadWorld(CHIME_DATA);
    }
    
    
    public static void dispose()
    {
        buttonsAtlas.dispose();
        assetManager.dispose();
    }

}
