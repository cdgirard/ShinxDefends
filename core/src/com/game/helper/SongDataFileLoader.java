package com.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.game.obj.SongData;

public class SongDataFileLoader
{


    /**
     * Only used for saving and loading an instance of a LocalMap from a Json
     * file.
     * @author cdgira
     *
     */
    public static class JsonWorld
    {
	/**
	 * Name of the song this data is attached too.
	 */
	public String song;
        /**
         * This array keeps tract of the time point in
         * the song where a key "beat" occurs.
         */
        public float[] onset;
        
        /**
         * For the index where a key "beat" occurs this
         * tells what group that beat belongs to.  If it 
         * works correctly all similar sounding beats should
         * belong to the same group.
         */
        public int[] types;
        
        public int[][] runs;
    }
    
    /**
     * We are not going to be able to stick to internal files if we want to be able for 
     * the world to be created dynamically.
     * @param path
     * @param file
     * @param world
     */
    public static SongData loadWorld(String file)
    {
        SongData myData = null;
        String load = readFile(file);
        if (!load.isEmpty())
        {
            Json json = new Json();
            JsonWorld jWorld = json.fromJson(JsonWorld.class, load);

            myData = new SongData(jWorld.song, jWorld.onset, jWorld.types, jWorld.runs);                
        }
        return myData;

    }
    
    /**
     * Loads the text file and returns the information in the file as a String.
     * @param fileName
     * @return
     */
    private static String readFile(String fileName)
    {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists())
        {
            String s = file.readString();
            if (!s.isEmpty())
            {
                return s;
            }
        }
        return "";
    }




}
