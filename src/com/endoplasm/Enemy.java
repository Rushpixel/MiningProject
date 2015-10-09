package com.endoplasm;

public abstract class Enemy {
	
	public Collidor2d body;
	public Faction faction = Faction.Alien;
	public boolean alive = true;
	public int fleetWeight;
	
	public Enemy(){
		SceneGraph.enemies.add(this);
	}
	
	public abstract void update();
	public abstract void render();
	public abstract void unload();
	
	public abstract void takeDamage(int damage);

}
