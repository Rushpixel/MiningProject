package com.endoplasm;

public class Entity {
	
	public Vertex2f pos; // metres
	public Vertex2f mom; // mass * acceleration
	public Vertex2f netGrav;
	public boolean hasGravity;
	public float mass; // kilograms
	public float friction; // % force maintained every step
	public float rotation; // degrees
	public float rotvel; // degrees per second
	public float rotfrict; // % rotvel maintained every step;
	
	public Entity(Vertex2f pos, Vertex2f mom, float mass, boolean hasGravity, float friction, float rotation, float rotvel, float rotfrict){
		this.pos = new Vertex2f(pos.getX(), pos.getY());
		this.mom = mom;
		this.netGrav = netGravity();
		this.hasGravity = hasGravity;
		this.mass = mass;
		this.friction = friction;
		this.rotation = rotation;
		this.rotvel = rotvel;
		this.rotfrict = rotfrict;
	}
	
	public Entity(){
		this.pos = new Vertex2f(0,0);
		this.mom = new Vertex2f(0,0);
		this.mass = 1;
		this.hasGravity = false;
		this.friction = 1;
		this.rotation = 0;
		this.rotvel = 0;
		this.rotfrict = 1;
	}
	
	@Override
	public Entity clone(){
		return new Entity(pos.clone(), mom.clone(), mass, hasGravity, friction, rotation, rotvel, rotfrict);
	}
	
	public void step(int numSteps){
		while(numSteps > 0){
			numSteps--;
			pos = VectorMath.add(pos, VectorMath.divide(mom, mass));
			netGrav = netGravity();
			if(hasGravity) mom = VectorMath.add(mom, netGrav);
			mom = VectorMath.multiply(mom, friction);
			rotation += rotvel;
			rotvel /= rotfrict;
		}
		if(Float.isNaN(mom.getX()) || Float.isNaN(mom.getY()) || Float.isNaN(pos.getX()) || Float.isNaN(pos.getY())){
			System.err.println("Entity " + this + " is corrupt, one or more of it's values equal NaN");
		}
	}
	
	public void step(){
		step(1);
	}
	
	public Vertex2f getVel(){
		return VectorMath.divide(mom, mass);
	}
	
	public void addVel(Vertex2f acc){
		mom = VectorMath.add(mom, VectorMath.multiply(acc, mass));
	}
	
	public void impulse(Vertex2f force){
		this.mom = VectorMath.add(this.mom, force);
	}
	
	public void impulse(float Rot, float force){
		mom = VectorMath.add(mom, new Vertex2f(MathUtil.getXSpeed(Rot, force), MathUtil.getYSpeed(Rot, force)));
	}
	
	public void impulseRelative(float Rot, float force){
		impulse(Rot + rotation, force);
	}
	
	public Vertex2f netGravity(){
		Vertex2f grav = new Vertex2f(0,0);
		for(Entity e: SceneGraph.gravity){
			float d = MathUtil.direction(e.pos.getX(), e.pos.getY(), pos.getX(), pos.getY());
			float g = (e.mass + mass) / MathUtil.distance(e.pos.getX(), e.pos.getY(), pos.getX(), pos.getY());
			g /= 100000;
			if(Float.isNaN(g)) continue;
			grav = VectorMath.add(grav, new Vertex2f(MathUtil.getXSpeed(d, g), MathUtil.getYSpeed(d, g)));
		}
		return grav;
	}
	
	/**
	 * Adds or subtracts mass from the entity without changing it's velocity
	 * @param dMass
	 */
	public void SmartMassChange(float newMass){
		mom = VectorMath.multiply(VectorMath.divide(mom, mass), newMass);
		mass = newMass;
	}

}
