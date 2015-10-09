package com.endoplasm;

public class Enemy_Dumb extends Enemy_Preset{
	
	public final Vertex2f[] polygon = new Vertex2f[] { 
			new Vertex2f(18, 0),
			new Vertex2f(-10, 10),
			new Vertex2f(-13, 0),
			new Vertex2f(-10, -10),
		};
	
	public Enemy_Dumb(Vertex2f pos, Fleet fleet){
		super();
		Entity e = new Entity(pos, new Vertex2f(0,0), 500, true, .95f, 0, 0, 1);
		body = Collidor2d.makePoly(this, pos, 0, polygon);
		super.Set(100, 150, Faction.Alien, fleet, 0, e, body);
	}

	@Override
	public void update() {
		if(super.playerCollisions(50)) return;
		Vertex2f v = new Vertex2f(0,0);
		v.add(super.moveStraight(SceneGraph.player.entity.pos, 1));
		v.add(super.flockSeperation(100, 0.5f));
		VectorMath.normalise(v, thrust);
		entity.impulse(v);
		super.lookAhead();
		super.physics();
		super.testHP();
	}

	@Override
	public void render() {
		super.renderSprite(Game.Assets.ENEMY_DUMB, 2, 16);
	}

}
