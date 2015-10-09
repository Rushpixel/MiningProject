package com.endoplasm;

public abstract class Particle{
	public Entity e;
	public boolean alive = true;
	
	public Particle(Vertex2f pos, Vertex2f mom,  float friction, float rotation, float rotvel, float rotfrict){
		e = new Entity(pos, mom, 1, false, friction, rotation, rotvel, rotfrict);
	}

	public abstract void render();

	public void update() {
		e.step();
	}
	
	public void unload(){
		e = null;
	}

	
	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
	
	
	public static class Smoke1 extends Particle {
		public Smoke1(Vertex2f pos, Vertex2f mom, int frameTime) {
		super(pos, mom, 0.9f, 0, 0, 0);
		this.frameTime = frameTime;
	}
		public int frame = 0;
		public int frameTime;
		public int i = 0;

		@Override public void update(){
			i++;
			if(i >= frameTime) {
				i = 0;
				frame++;
			}
			if(frame > 15) alive = false;
		}
		
		@Override
		public void render() {
			float size = 8;
			Render2d.squareRot(e.pos.getX(), e.pos.getY(), -size, -size, size, size, (frame % 4) / 4f, (frame / 4) / 4f + (1 / 4f), (frame % 4) / 4f + (1 / 4f), (frame / 4) / 4f, e.rotation, new float[] { 1, 1, 1, 1 }, Game.Assets.P_SMOKE1);
		}
	}
	
	
//	
//	
//	
//	
//	
//	
//	
//	
//	
	
	public static class Blast1 extends Particle {
		public float size;
		public Blast1(Vertex2f pos, Vertex2f mom, float size) {
		super(pos, mom, 1, 0, 0, 0);
		this.size = size;
	}

		@Override 
		public void update(){
			size += -size * 0.5f;
			if(size < 1) alive = false;
		}
		
		@Override
		public void render() {
			Render2d.squareRot(e.pos.getX(), e.pos.getY(), -size, -size, size, size, e.rotation, new float[] { 1, 1, 1, 1 }, Game.Assets.P_BLAST);
		}
	}

}
