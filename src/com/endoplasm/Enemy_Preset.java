package com.endoplasm;

import com.endoplasm.Bullet.OmniAmmo;

public abstract class Enemy_Preset extends Enemy {

	public Fleet fleet;
	public int HP;
	public Entity entity;
	public float thrust;
	public TextureAsset tex;
	public int frame = 0;

	public void Set(int HP, float thrust, Faction faction, Fleet fleet, int fleetWeight, Entity entity, Collidor2d body) {
		this.HP = HP;
		this.thrust = thrust;
		this.faction = faction;
		this.fleetWeight = fleetWeight;
		this.fleet = fleet;
		if(fleet != null)fleet.add(this);
		this.entity = entity;
		this.body = body;
	}
	
	public void unload(){
		fleet = null;
		entity = null;
		tex = null;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Movement Options
	 */
	
	public Vertex2f flockSeperation(float neighborRange, float weight){
		if(fleet != null){
			Vertex2f total = new Vertex2f(0,0);
			int numneighbor = 0;
			for(Enemy_Preset e: fleet.members){
				if(e != this && MathUtil.distance(entity.pos.getX(), entity.pos.getY(), e.entity.pos.getX(), e.entity.pos.getY()) < neighborRange){
					total.addX(e.entity.pos.getX());
					total.addY(e.entity.pos.getY());
					numneighbor++;
				}
			}
			if(numneighbor == 0) return new Vertex2f(0,0);
			Vertex2f centre = VectorMath.divide(total, new Vertex2f(numneighbor, numneighbor));
			Vertex2f dif = new Vertex2f(centre.getX() - entity.pos.getX(), centre.getY() - entity.pos.getY());
			if(dif.getX() == 0 && dif.getY() == 0) return new Vertex2f(0,0);
			VectorMath.normalise(dif, -weight);
			return dif;
		} else
		return new Vertex2f(0,0);
	}

	public Vertex2f moveStraight(Vertex2f target, float weight) {
		Vertex2f dif = new Vertex2f(target.getX() - entity.pos.getX(), target.getY() - entity.pos.getY());
		if(dif.getX() == 0 && dif.getY() == 0) return new Vertex2f(0,0);
		VectorMath.normalise(dif, weight);
		return dif;
	}

	public Vertex2f moveCorrected(Vertex2f target, float weight) {
		Vertex2f t = new Vertex2f(target.getX() - entity.pos.getX(), target.getY() - entity.pos.getY());
		Vertex2f v = new Vertex2f(entity.mom.getX(), entity.mom.getY());
		VectorMath.normalise(v, 1);
		VectorMath.normalise(v, 1);
		Vertex2f dif = VectorMath.subtract(t, v);
		if(Float.isNaN(dif.getX()) || Float.isNaN(dif.getY())) return new Vertex2f(0.1f,0);
		VectorMath.normalise(dif, weight);
		return dif;
	}

	public Vertex2f keepDist(Vertex2f target, float minDist, float maxDist, float weight) {
		float pDir = MathUtil.direction(entity.pos.getX(), entity.pos.getY(), target.getX(), target.getY()) + 180;
		float dis = MathUtil.distance(entity.pos.getX(), entity.pos.getY(), target.getX(), target.getY());
		if(dis < minDist){
			return new Vertex2f(MathUtil.getXSpeed(pDir, -weight), MathUtil.getYSpeed(pDir, -weight));
		} else if(dis > maxDist){
			return new Vertex2f(MathUtil.getXSpeed(pDir, weight), MathUtil.getYSpeed(pDir, weight));
		}
		return(new Vertex2f(0,0));
	}
	
	public void lookAhead(){
		entity.rotation = MathUtil.direction(0, 0, entity.mom.getX(), entity.mom.getY()) + 180;
	}
	
	public void lookAt(Vertex2f target){
		entity.rotation = MathUtil.direction(entity.pos.getX(), entity.pos.getY(), target.getX(), target.getY()) + 180;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * shooting
	 */

	public void shoot(Turret turret) {
		turret.ammo.Fire(faction, turret.getPos(entity), entity.getVel(), turret.BarrelRot, 100);
	}

	public void rotateTurretToFace(Turret turret, Vertex2f target) {
		Vertex2f p = VectorMath.add(VectorMath.rotateMatrix2D(turret.PosOnShip, entity.rotation), entity.pos);
		float dr = MathUtil.getAngleBetween(turret.BarrelRot, MathUtil.direction(p.getX(), p.getY(), target.getX(), target.getY()));
		if (dr < -turret.maxRotSpeed) dr = -turret.maxRotSpeed;
		if (dr > turret.maxRotSpeed) dr = turret.maxRotSpeed;
		turret.BarrelRot += dr;
	}

	public static class Turret {
		public Vertex2f PosOnShip;
		public Vertex2f BarrelTip;
		public float BarrelRot;
		public float maxRotSpeed;
		public OmniAmmo ammo;

		public Turret(Vertex2f PosOnShip, Vertex2f BarrelTip, float BarrelRot, float maxRotSpeed, OmniAmmo ammo) {
			this.PosOnShip = PosOnShip;
			this.BarrelTip = BarrelTip;
			this.BarrelRot = BarrelRot;
			this.maxRotSpeed = maxRotSpeed;
			this.ammo = ammo;
		}

		public void update() {
			ammo.update();
		}
		
		public void unload(){
			PosOnShip = null;
			BarrelTip = null;
			ammo = null;
		}

		public Vertex2f getPos(Entity e) {
			return VectorMath.add(VectorMath.add(VectorMath.rotateMatrix2D(BarrelTip, BarrelRot), VectorMath.rotateMatrix2D(PosOnShip, e.rotation)), e.pos);
		}
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * basics
	 */

	public boolean playerCollisions(int damage) {
		for (CollidorResponse cr : body.Responses) {
			if (cr.other instanceof PlayerShip && HP > 0 && faction != Faction.Ally) {
				((PlayerShip) cr.other).takeDamage(damage);
				SceneGraph.foreParticle.add(new Particle.Blast1(entity.pos, ((PlayerShip) cr.other).entity.mom, damage*2));	
				alive = false;
				return true;
			}
		}
		return false;
	}
	
	public void physics(){
		entity.step();
		body.updateModifier(entity.pos, entity.rotation);
		SceneGraph.QuadTree.register(body);
	}

	public void testHP() {
		if (HP <= 0) alive = false;
	}

	@Override
	public void takeDamage(int damage) {
		HP -= damage;
		SceneGraph.freezeFrames += damage / 50;
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * render
	 */
	
	public void renderSprite(TextureAsset tex, int numS, float size){
		float numF = (float) numS;
		Render2d.squareRot(entity.pos.getX(), entity.pos.getY(), -size, -size, size, size, (frame % numS) / numF, (frame / numS) / numF + (1 / numF), (frame % numS) / numF + (1 / numF), (frame / numS) / numF, entity.rotation, new float[] { 1, 1, 1, 1 }, tex);
	}

}
