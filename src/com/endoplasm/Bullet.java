package com.endoplasm;

public abstract class Bullet{
	
	public Entity entity;
	
	public Collidor2d col;
	public boolean alive = true;
	public int overcome = 1;
	
	public Faction faction;

	public abstract void update();

	public abstract void render();
	

	
	
	
	

	public static abstract class OmniAmmo {
		
		public String name = "Untitled Ammo";

		public abstract void Fire(Faction faction,Vertex2f fireFrom, Vertex2f parentVel, float aim, float dist);

		public void update() {

		}

		public String getDetail(){
			return "";
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
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	
////	
//	
	
	
	public static class AmmoRattle extends OmniAmmo{
		public int cooldown = 0;
		public int cooldownTime = 10;
		public int v = 15;
		public boolean infinite;
		public int ammo;
		
		public AmmoRattle(boolean infinite, int ammo){
			this.infinite = infinite;
			this.ammo = ammo;
		}
		
		@Override
		public void Fire(Faction faction, Vertex2f fireFrom, Vertex2f parentVel, float aim, float dist) {
			if(cooldown <= 0 && (infinite || ammo > 0)) {
				SceneGraph.bullets.add(new BulletStraight(faction, 0, 16, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim, v),MathUtil.getYSpeed(aim, v)), parentVel), 25, 50, 10));
				SceneGraph.bullets.add(new BulletStraight(faction, 0, 16, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim-5, v),MathUtil.getYSpeed(aim-5, v)), parentVel), 25, 50, 10));
				SceneGraph.bullets.add(new BulletStraight(faction, 0, 16, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim+5, v),MathUtil.getYSpeed(aim+5, v)), parentVel), 25, 50, 10));
				if(!infinite) ammo--;
				cooldown = cooldownTime;
			}
		}
		
		@Override
		public void update(){
			if(cooldown > 0) cooldown--;
		}
		
		@Override
		public String getDetail(){
			return infinite ? "" : "" + ammo;
		}
	}
	
	
	
	
	public static class BulletStraight extends Bullet{
		
		public int life;
		public int damage;
		public int Image;
		public BulletStraight(Faction faction, int image, float radius, Vertex2f pos, Vertex2f vel, int life, int damage, int overcome){
			this.faction = faction;
			this.Image = image;
			this.life = life;
			this.damage = damage;
			this.overcome = overcome;
			entity = new Entity(pos, new Vertex2f(0,0), 1, false, 1, 0, 0, 1);
			entity.addVel(vel);
			entity.rotation = MathUtil.direction(0, 0, entity.mom.getX(), entity.mom.getY());
			col = Collidor2d.makeCirc(this, pos, radius);
		}

		@Override
		public void update() {
			for(CollidorResponse cr: col.Responses){
				if(cr.other instanceof Enemy) {
					Enemy o = (Enemy) cr.other;
					if(o.faction != faction){
						o.takeDamage(damage);
						SceneGraph.foreParticle.add(new Particle.Blast1(entity.pos, new Vertex2f(0,0), damage));
						alive = false;
					}
				}
				if(cr.other instanceof Bullet) {
					Bullet o = (Bullet) cr.other;
					if(o.faction != faction && alive && o.alive){
						if(o.overcome > overcome) {
							alive = false;
							overcome -= o.overcome;
							if(overcome <= 0) alive = false;
						}else if(o.overcome < overcome) {
							o.alive = false;
							o.overcome -= overcome;
							if(o.overcome <= 0) o.alive = false;
						} else {
							o.alive = false;
							alive = false;
						}
					}
				}
				if(cr.other instanceof PlayerShip && faction != Faction.Ally) {
					((PlayerShip) cr.other).takeDamage(damage);
					SceneGraph.foreParticle.add(new Particle.Blast1(entity.pos, ((PlayerShip) cr.other).entity.mom, damage*2));
					alive = false;
				}
				if(cr.other instanceof Moon){
					alive = false;
				}
			}
			entity.step();
			col.updateModifier(entity.pos, 0);
			SceneGraph.QuadTree.register(col);
			life--;
			if(life <= 0) alive = false;
		}

		@Override
		public void render() {
		Render2d.squareRot(entity.pos.getX(), entity.pos.getY(), -16, -16, 16, 16, (Image % 8) / 8f + (1 / 8f), (Image / 8) / 8f + (1 / 8f), (Image % 8) / 8f, (Image / 8) / 8f, entity.rotation, new float[] { 1, 1, 1, 1 }, Game.Assets.BULLETS);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class AmmoPlasma extends AmmoRattle{
		
		public AmmoPlasma(boolean infinite, int ammo){
			super(infinite, ammo);
			cooldownTime = 40;
			v = 3;
		}
		
		@Override
		public void Fire(Faction faction, Vertex2f fireFrom, Vertex2f parentVel, float aim, float dist) {
			if(cooldown <= 0 && (infinite || ammo > 0)) {
				SceneGraph.bullets.add(new BulletStraight(faction, 8, 16, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim, v),MathUtil.getYSpeed(aim, v)), parentVel), 200, 10, 10));
				if(!infinite) ammo--;
				cooldown = cooldownTime;
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
public static class AmmoSniper extends AmmoRattle{
		
		public AmmoSniper(boolean infinite, int ammo){
			super(infinite, ammo);
			cooldownTime = 60;
			v = 10;
		}
		
		@Override
		public void Fire(Faction faction, Vertex2f fireFrom, Vertex2f parentVel, float aim, float dist) {
			if(cooldown <= 0 && (infinite || ammo > 0)) {
				SceneGraph.bullets.add(new BulletStraight(faction, 9, 16, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim, v),MathUtil.getYSpeed(aim, v)), parentVel), 300, 50, 50));
				if(!infinite) ammo--;
				cooldown = cooldownTime;
			}
		}
	}


public static class AmmoSpam extends AmmoRattle{
	
	public AmmoSpam(boolean infinite, int ammo){
		super(infinite, ammo);
		cooldownTime = 30;
		v = 3;
	}
	
	@Override
	public void Fire(Faction faction, Vertex2f fireFrom, Vertex2f parentVel, float aim, float dist) {
		if(cooldown <= 0 && (infinite || ammo > 0)) {
			SceneGraph.bullets.add(new BulletStraight(faction, 10, 10, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim-45, v),MathUtil.getYSpeed(aim-45, v)), parentVel), 300, 5, 2));
			SceneGraph.bullets.add(new BulletStraight(faction, 10, 10, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim-30, v),MathUtil.getYSpeed(aim-30, v)), parentVel), 300, 5, 2));
			SceneGraph.bullets.add(new BulletStraight(faction, 10, 10, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim-15, v),MathUtil.getYSpeed(aim-15, v)), parentVel), 300, 5, 2));
			SceneGraph.bullets.add(new BulletStraight(faction, 10, 10, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim+15, v),MathUtil.getYSpeed(aim+15, v)), parentVel), 300, 5, 2));
			SceneGraph.bullets.add(new BulletStraight(faction, 10, 10, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim+30, v),MathUtil.getYSpeed(aim+30, v)), parentVel), 300, 5, 2));
			SceneGraph.bullets.add(new BulletStraight(faction, 10, 10, fireFrom, VectorMath.add(new Vertex2f(MathUtil.getXSpeed(aim+45, v),MathUtil.getYSpeed(aim+45, v)), parentVel), 300, 5, 2));
			if(!infinite) ammo--;
			cooldown = cooldownTime;
		}
	}
}
	
	
	
}
