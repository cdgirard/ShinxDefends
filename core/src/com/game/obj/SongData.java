package com.game.obj;

public class SongData
{
    public String name;
    public float[] onset;
    public int[] types;
    public float[][] runs;
    public int[] runTypes;
    
    public SongData(String n, float[] o, int[] t, float[][] r)
    {
	name = new String(n);
	
	onset = new float[o.length];
	for (int x=0;x<o.length;x++)
	    onset[x] = o[x];
	
	types = new int[t.length];
	for (int x=0;x<t.length;x++)
	    types[x] = t[x];
	
	runs = new float[r.length][];
	runTypes = new int[r.length];
	for (int x=0;x<r.length;x++)
	{
	    runs[x] = new float[r[x].length-1];
	    for (int y=0;y<r[x].length-1;y++)
	    {
		runs[x][y] = r[x][y];
	    }
	    runTypes[x] = (int)r[x][r[x].length-1];
	}
    }

}
