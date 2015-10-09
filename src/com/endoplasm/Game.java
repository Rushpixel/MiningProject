package com.endoplasm;

import org.lwjgl.input.Mouse;

public class Game extends GInit {
	
	public static Assets Assets;
	
	public static GameFeild GameFeild;

	@Override
	public void init() {
		Endogen.camera.setUp(new Vertex3f(0,1,0));
		Assets = new Assets(null);
		Assets.load();
		GameFeild = new GameFeild(GUI.field, new Vertex2f(0,0), new Vertex2f(Endogen.WIDTH, Endogen.HEIGHT));
		GUI.field.children.add(GameFeild);
		SceneGraph.NewGame();
	}

	@Override
	public void cleanup() {
		Assets.unload();
		Assets = null;
	}

	private long lastUpdate = 0;
	private long lastRender = 0;

	@Override
	public void update() {
		long startTime = System.currentTimeMillis();
		Debug = "Running at " + Endogen.lastUPS + " UPS and " + Endogen.lastFPS + " FPS";
		{
			SceneGraph.update();
		}
		long endTime = System.currentTimeMillis();
		lastUpdate = endTime - startTime;
		addDebugLine("Last Update took " + lastUpdate + " ms");
		addDebugLine("Last Render took " + lastRender + " ms");
		addDebugLine("Mouse at " + Mouse.getX() + " " + Mouse.getY());
	}

	@Override
	public void render3D() {
		long startTime = System.currentTimeMillis();
		SceneGraph.render();
		lastRender = System.currentTimeMillis() - startTime;
	}

	public static String Debug = "";

	@Override
	public void render2D() {
		Text.renderTextFromString(Debug, 5, Endogen.HEIGHT - 5, 8, 10, -1, 2, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });

	}

	public static void addDebugLine(String line) {
		Debug += " \n" + line;
	}
	
	@Override
	public String getVertexShaderDir(){
		return "/Resources/Shaders/Vertex.shader";
	}
	
	@Override
	public String getFragmentShaderDir(){
		return "/Resources/Shaders/Fragment.shader";
	}

}
