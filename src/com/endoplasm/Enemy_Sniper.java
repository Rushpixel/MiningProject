package com.endoplasm;

public class Enemy_Sniper extends Enemy_Preset {

	public Turret turret = new Turret(new Vertex2f(0, 0), new Vertex2f(47, 0), 0, 1f, new Bullet.AmmoSniper(true, 0));
	public final Vertex2f[] polygon = new Vertex2f[] { new Vertex2f(50, 0), new Vertex2f(15, 30), new Vertex2f(-50, 0), new Vertex2f(15, -30), };
	public int numSent = 0;

	public Enemy_Sniper(Vertex2f pos, Fleet fleet) {
		super();
		Entity e = new Entity(pos, new Vertex2f(0, 0), 20000, true, .99f, 0, 0, 1);
		body = Collidor2d.makePoly(this, pos, 0, polygon);
		super.Set(1000, 300, Faction.Alien, fleet, 1, e, body);
		frame = 12;
	}

	@Override
	public void update() {
		Vertex2f v = new Vertex2f(0, 0);
		
		v.add(super.keepDist(SceneGraph.player.entity.pos, 800, 900, 1));
		if(fleet != null) if(fleet.members.get(0) != this) {
			v.add(super.flockSeperation(300, 1f));
		}
		
		VectorMath.normalise(v, thrust);
		VectorMath.fixNaN(v);
		entity.impulse(v);
		super.lookAhead();
		super.physics();
		super.rotateTurretToFace(turret, SceneGraph.player.entity.pos);
		super.shoot(turret);
		turret.update();
		
		if(HP < 750 && numSent == 0){
			numSent = 1;
			for(int i = 0; i < 5; i++){
				new Enemy_Dumb(new Vertex2f(entity.pos.getX(), entity.pos.getY()), fleet);
			}
		}
		if(HP < 500 && numSent == 1){
			numSent = 2;
			for(int i = 0; i < 8; i++){
				new Enemy_Dumb(new Vertex2f(entity.pos.getX(), entity.pos.getY()), null);
			}
		}
		if(HP < 250 && numSent == 2){
			numSent = 3;
			for(int i = 10; i < 5; i++){
				new Enemy_Dumb(new Vertex2f(entity.pos.getX(), entity.pos.getY()), null);
			}
		}
		super.testHP();
	}

	@Override
	public void render() {
		super.renderSprite(Game.Assets.ENEMY_SNIPER, 1, 64);
		Render2d.squareRot(entity.pos.getX(), entity.pos.getY(), -64, -64, 64, 64,turret.BarrelRot, new float[]{1,1,1,1}, Game.Assets.ENEMY_BIGGUN);
		CollidorUtil.renderCollidor(body, true, true, true, true);
	}
	
	@Override
	public void unload(){
		turret.unload();
		turret = null;
	}

}
