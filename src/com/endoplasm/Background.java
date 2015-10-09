package com.endoplasm;

import org.lwjgl.opengl.GL11;

public class Background {
	
	public final static Vertex2f sun_Pos = new Vertex2f(0,0);
	public final static float sun_Posfactor = 0.99f;
	public final static float sun_Size = 64f;
	public final static Vertex2f ora_Pos = new Vertex2f(-100,-240);
	public final static float ora_Posfactor = 0.97f;
	public final static float ora_Size = 16f;
	
	public static void update(){
		
	}
	
	public static void render(){
		GL11.glPushMatrix();
		Vertex2f sPos = VectorMath.add(sun_Pos, VectorMath.multiply(SceneGraph.gCamera.pos, sun_Posfactor));
		Vertex2f oPos = VectorMath.add(ora_Pos, VectorMath.multiply(SceneGraph.gCamera.pos, ora_Posfactor));
		//Render2d.squareRot(sun_Pos.getX(), sun_Pos.getY(), -sun_Size*2, -sun_Size*2, sun_Size*2, sun_Size*2, 0, new float[]{1,1,1,1}, Game.Assets.SUNBEAM);
		Render2d.DrawCircle(sPos, 600, 64, 0, 1, 1, 1, 0.2f);
		Render2d.squareRot(sPos.getX(), sPos.getY(), -sun_Size*2, -sun_Size*2, sun_Size*2, sun_Size*2, 0, new float[]{1,1,1,1}, Game.Assets.SUN);
		
		Render2d.squareRot(oPos.getX(), oPos.getY(), -ora_Size*2, -ora_Size*2, ora_Size*2, ora_Size*2, 0, new float[]{1,1,1,1}, Game.Assets.PLANET_BIGORANGE);
		
		GL11.glPopMatrix();
	}

}
