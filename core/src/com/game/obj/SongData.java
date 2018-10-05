package com.game.obj;

public class SongData
{
    public String name;
    public float[] onset;
    public int[] types;
    public int[][] runs;
    
    public SongData(String n, float[] o, int[] t, int[][] r)
    {
	
	name = new String(n);
	onset = new float[o.length];
	for (int x=0;x<o.length;x++)
	    onset[x] = o[x];
	types = new int[t.length];
	for (int x=0;x<t.length;x++)
	    types[x] = t[x];
	runs = new int[r.length][r[0].length];
	for (int x=0;x<r.length;x++)
	{
	    for (int y=0;y<r[x].length;y++)
	    {
		runs[x][y] = r[x][y];
	    }
	}
    }

}
