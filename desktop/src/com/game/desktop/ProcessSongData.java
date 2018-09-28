package com.game.desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessSongData
{
    public static final String ONSET_FILE = "onset.txt";
    public static final String PITCH_FILE = "pitch.txt";
    public static final String EXPORT_FILE = "songData.dat";
    public static final String SONG_FILE = "chime_phototropic.wav";
    public static final String PATH = "assets-raw/music";
    public static final String EXPORT_PATH = "game_data";
    public static final int THRESHOLD = 5;
    
    public static double[] loadOnsetData() throws FileNotFoundException
    {
	File file = new File(PATH+"/"+ONSET_FILE);
	ArrayList<String> data = new ArrayList<String>();
	Scanner scan = new Scanner(file);
	while (scan.hasNext())
	{
	    data.add(scan.nextLine());
	}
	double[] onsetData = new double[data.size()];
	for (int x=0;x<onsetData.length;x++)
	{
	    onsetData[x] = Double.parseDouble(data.get(x));
	}
	return onsetData;
    }
    
    public static double[][] loadPitchData() throws FileNotFoundException
    {
	File file = new File(PATH+"/"+PITCH_FILE);
	ArrayList<String> data = new ArrayList<String>();
	Scanner scan = new Scanner(file);
	while (scan.hasNext())
	{
	    data.add(scan.nextLine());
	}
	double[][] pitchData = new double[2][data.size()];
	for (int x=0;x<pitchData[0].length;x++)
	{
	    String[] values = data.get(x).split(" ");
	    pitchData[0][x] = Double.parseDouble(values[0]);
	    pitchData[1][x] = Double.parseDouble(values[1]);
	}
	return pitchData;
    }
    
    public static int[] computePitchAtOnset(double[] onset, double[][]pitch)
    {
	int[] result = new int[onset.length];
	
	int pitchLoc = 0;
	for (int x=0;x<onset.length;x++)
	{
	    while (pitch[0][pitchLoc] < onset[x])
	    {
		pitchLoc++;
	    }
	    result[x] = (int)((pitch[1][pitchLoc-1]+pitch[1][pitchLoc])/2);
	}
	return result;
    }
    
    /**
     * Very simple clustering algorithm.
     * @param rawTypes
     * @return
     */
    public static int[] setTypes(int[] rawTypes)
    {
	int[] types = new int[rawTypes.length];
	int nextType = 1;
	HashMap<Integer,Integer> baseTypes = new HashMap<Integer,Integer>();
	types[0] = 1;
	baseTypes.put(1, rawTypes[0]);
	for (int x=1;x<types.length;x++)
	{
	    types[x] = -1;
	    for(Integer key : baseTypes.keySet())
	    {
		int value = baseTypes.get(key);
		if ((rawTypes[x] > value - 5) && (rawTypes[x] < value + 5))
		{
		    types[x] = key;
		    break;
		}
	    }
	    if (types[x] == -1)
	    {
		types[x] = nextType;
		baseTypes.put(nextType, rawTypes[x]);
		nextType++;
		
	    }
	}
	return types;
    }
    
    public static void exportData(double[] onset, int[] types) throws FileNotFoundException
    {
	File file = new File(EXPORT_PATH+"/"+EXPORT_FILE);
	PrintWriter pw = new PrintWriter(file);
	pw.print("{\n");
	pw.print("song: "+SONG_FILE);
	pw.print("onset: [\n");
	for (int x=0;x<onset.length-1;x++)
	{
	    pw.print(onset[x]+", \n");
	}
	pw.print(onset[onset.length-1]+" ],\n");
	
	pw.print("types: [\n");
	for (int x=0;x<types.length-1;x++)
	{
	    pw.print(types[x]+", \n");
	}
	pw.print(types[types.length-1]+" ]\n");
	pw.print("}");
	pw.flush();
	pw.close();
    }
    
    public static void main(String[] args)
    {
	try
	{
	    double[] onsetData = loadOnsetData();
	    
	
	    double[][] pitchData = loadPitchData();
	    
	    int[] pitchAtOnsetData = computePitchAtOnset(onsetData,pitchData);
	    
	    int[] typeData = setTypes(pitchAtOnsetData);
	    
	    exportData(onsetData,typeData);
	    
	    System.out.println("Here");
	}
	catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	
    }
}
