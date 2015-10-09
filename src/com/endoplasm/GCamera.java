package com.endoplasm;

public class GCamera {
	
	public Vertex2f pos;
	public float hWidth;
	
	public GCamera(Vertex2f pos, float hWidth){
		this.pos = pos;
		this.hWidth = hWidth;
	}
	
	public void set(){
		pos = VectorMath.add(SceneGraph.player.entity.pos, VectorMath.multiply(VectorMath.subtract(SceneGraph.player.Reticle, SceneGraph.player.entity.pos), new Vertex2f( 0.03f, 0.03f)));
		//pos = VectorMath.add(pos, VectorMath.multiply(VectorMath.subtract(newPos, pos), new Vertex2f(0.1f, 0.1f)));
		Endogen.camera.setPos1(new Vertex3f(pos.getX(), pos.getY(), 3000 ));
		Endogen.camera.setPos2(new Vertex3f(pos.getX(), pos.getY(), 0 ));
		float size = hWidth*20;
		SceneGraph.QuadTree.Assign(new Vertex2f(pos.getX() - size, pos.getY() - size), new Vertex2f(pos.getX() + size, pos.getY() + size));
	}

}
