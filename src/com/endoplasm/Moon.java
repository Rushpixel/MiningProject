package com.endoplasm;

import java.util.Random;

public class Moon {
	
	public Random r;
	public Collidor2d col;
	public float mass;
	public float density;
	public float radius;
	public int numSlices;
	public Vertex2f pos;
	public Vertex2f shadowPos;
	public float shadowRadius;
	public Vertex2f bottomLeft;
	public Vertex2f topRight;
	
	// Changing factors
	public float SeismicActivity;
	public float atmosphereDensity;
	
	public Moon(Vertex2f pos, int numSlices, long seed, float radius){
		this.pos = pos;
		r = new Random(seed);
		this.numSlices = numSlices;
		this.radius = radius;
		col = Collidor2d.makeCirc(this, pos, radius);
		
		this.shadowRadius = radius * 0.9f;
		float shadowDir = MathUtil.direction(0, 0, pos.getX(), pos.getY());
		shadowPos = VectorMath.add(pos, new Vertex2f(MathUtil.getXSpeed(shadowDir, -radius*.1f), MathUtil.getYSpeed(shadowDir, -radius*.1f)));
		
		density = r.nextFloat() * 1000 + 500;
		mass = (float)Math.PI * radius*radius;
		mass *= density;
		SceneGraph.gravity.add(new Entity(pos, new Vertex2f(0,0), mass, false, 0, 0, 0, 0));
	}
	
	public void update(){
		SceneGraph.QuadTree.register(col);
	}
	
	public void render(){
		Render2d.DrawCircle(pos, radius, numSlices, 0, .4f, .2f, .2f, 1);
		Render2d.DrawCircle(shadowPos, shadowRadius, numSlices, 0, 0f, 0f, 0f, .3f);
	}
	
	public void unload(){
		r = null;
	}

}
