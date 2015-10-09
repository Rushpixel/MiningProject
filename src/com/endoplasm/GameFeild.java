package com.endoplasm;

import org.lwjgl.input.Cursor;

public class GameFeild extends GUIRect{

	public GameFeild(GUIElement parent, Vertex2f pos, Vertex2f dimensions) {
		super(parent, pos, dimensions);
	}

	@Override
	public Cursor getCursor() {
		return Game.Assets.Reticle;
	}
	
	@Override
	public boolean doesContain(Vertex2f parentOffset){
		return true;
	}

	@Override
	public void render() {
		
	}

	@Override
	public void update() {
		
	}

}
