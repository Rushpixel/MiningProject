package com.endoplasm;

public class Enemy_Spam extends Enemy_Preset {

	public Turret turret = new Turret(new Vertex2f(18, 0), new Vertex2f(0, 0), 180, 0, new Bullet.AmmoSpam(true, 0));
	public final Vertex2f[] polygon = new Vertex2f[] { new Vertex2f(26, 0), new Vertex2f(10, 30), new Vertex2f(-12, 0), new Vertex2f(10, -30), };

	public Enemy_Spam(Vertex2f pos, Fleet fleet) {
		super();
		Entity e = new Entity(pos, new Vertex2f(0, 0), 500, true, .95f, 0, 0, 1);
		body = Collidor2d.makePoly(this, pos, 0, polygon);
		super.Set(250, 200, Faction.Alien, fleet, 3, e, body);
		frame = 12;
	}

	@Override
	public void update() {
		if (super.playerCollisions(50)) return;
		super.lookAt(SceneGraph.player.entity.pos);
		Vertex2f v = new Vertex2f(0, 0);

		if (fleet != null) if (fleet.members.get(0) != this) {
			v.add(super.flockSeperation(100, 0.5f));
			v.add(super.keepDist(fleet.members.get(0).entity.pos, 150, 250, 1.2f));
		}
		v.add(super.keepDist(SceneGraph.player.entity.pos, 350, 450, 0.5f));

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
		super.renderSprite(Game.Assets.ENEMY_SPAM, 4, 32);
		CollidorUtil.renderCollidor(body, true, true, true, true);
	}
	
	@Override
	public void unload(){
		turret.unload();
		turret = null;
	}

}
