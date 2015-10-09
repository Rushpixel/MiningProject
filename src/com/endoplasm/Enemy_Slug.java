package com.endoplasm;

public class Enemy_Slug extends Enemy_Preset{
	
	
	public Turret turret = new Turret(new Vertex2f(18, 0), new Vertex2f(0,0), 0, 0, new Bullet.AmmoPlasma(true, 0));
	public final Vertex2f[] polygon = new Vertex2f[] { 
			new Vertex2f(32, 0),
			new Vertex2f(-15, 32),
			new Vertex2f(-15, 0),
			new Vertex2f(-15, -32),
		};
	
	public Enemy_Slug(Vertex2f pos, Fleet fleet){
		super();
		Entity e = new Entity(pos, new Vertex2f(0,0), 500, true, .95f, 0, 0, 1);
		body = Collidor2d.makePoly(this, pos, 0, polygon);
		super.Set(100, 150, Faction.Alien, fleet, 1, e, body);
		frame = 12;
	}

	@Override
	public void update() {
		if(super.playerCollisions(50)) return;
		super.lookAhead();
		Vertex2f v = new Vertex2f(0, 0);
		
		v.add(super.moveCorrected(SceneGraph.player.entity.pos, 1));
		v.add(super.flockSeperation(100, 0.5f));
		
		VectorMath.normalise(v, thrust);
		VectorMath.fixNaN(v);
		entity.impulse(v);
		super.physics();
		turret.BarrelRot = entity.rotation;
		super.shoot(turret);
		turret.update();
		super.testHP();
	}

	@Override
	public void render() {
		super.renderSprite(Game.Assets.ENEMY_SLUG, 4, 32);
		CollidorUtil.renderCollidor(body, true, true, true, true);
	}
	
	@Override
	public void unload(){
		turret.unload();
		turret = null;
	}

}
