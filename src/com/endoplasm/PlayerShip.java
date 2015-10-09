package com.endoplasm;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PlayerShip {

	public int maxHP = 500;
	public int HP = 500;
	public HUD hud = new HUD();
	

	public float Width = 20;
	public float heights[] = new float[]{
		18/2,
		28/2,
		44/2,
		57/2,
		63/2,
		59/2,
		44/2,
		27/2,
		17/2,
	};

	public Entity entity;
	public Collidor2d col;
	public float thrust = 150;
	public Vertex2f Reticle;
	public float yRot = 90;
	public float DyRot = 0;
	public int frame = 0;
	public int lastFrame = 0;
	public int timeTillNextP = 0;

	public Bullet.OmniAmmo ammo = new Bullet.AmmoRattle(false, 6000);

	public PlayerShip(Vertex2f pos) {
		entity = new Entity(pos, new Vertex2f(0, 0), 1000, true, 1f, 0, 0, 0.9f);
		col = Collidor2d.makePoly(this, pos, 0, getPolygon());
		Reticle = new Vertex2f(pos.getX(), pos.getY());
	}

	public void update() {
		collisions();
		input();
		entity.step();
		animations();
		col.updateModifier(entity.pos, entity.rotation);
		SceneGraph.QuadTree.register(col);
		ammo.update();
		
		if(HP <= 0){
			SceneGraph.Restart();
			SceneGraph.NewGame();
		}
	}

	public void collisions() {
		for (CollidorResponse cr : col.Responses) {
			if(cr.other instanceof Moon) {
				HP = 0;
			}
		}
		Game.addDebugLine("HP " + HP);
	}

	public void input() {
		float cursorx = Mouse.getX() - Endogen.WIDTH / 2 + SceneGraph.gCamera.pos.getX();
		float cursory = Mouse.getY() - Endogen.HEIGHT / 2 + SceneGraph.gCamera.pos.getY();
		entity.rotation = MathUtil.direction(cursorx, cursory, entity.pos.getX(), entity.pos.getY());
		Reticle = new Vertex2f(cursorx, cursory);

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			entity.impulseRelative(0, thrust);
			if (timeTillNextP <= 0) {
				timeTillNextP = 10;
				SceneGraph.backParticle.add(new Particle.Smoke1(entity.pos, new Vertex2f(0, 0), 2));
			}
			timeTillNextP--;
		} else {
			timeTillNextP = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			entity.impulseRelative(90, 10);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			entity.impulseRelative(270, 10);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			entity.impulseRelative(180, 40);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			float r = MathUtil.direction(entity.pos.getX(), entity.pos.getY(), Reticle.getX(), Reticle.getY());
			float d = MathUtil.distance(entity.pos.getX(), entity.pos.getY(), Reticle.getX(), Reticle.getY());
			ammo.Fire(Faction.Ally, entity.pos, entity.getVel(), r + 180, d);
		}
	}
	
	public void animations(){
		Vertex2f trav = new Vertex2f(entity.mom.getX(), entity.mom.getY());
		VectorMath.normalise(trav);
		float d = VectorMath.dotProduct(trav, VectorMath.rotateMatrix2D(new Vertex2f(0, 1), entity.rotation));
		if(Math.abs(MathUtil.distance(0, 0, entity.mom.getX(), entity.mom.getY()) / entity.mass) < 1){ 
			yRot += MathUtil.getAngleBetween(90, yRot) * 0.1f;
		} else if (!Float.isNaN(d)) {
			yRot += MathUtil.getAngleBetween(-d * 90 + 90, yRot) * 0.1f;
		}
		yRot += DyRot;
		frame = Math.round(yRot / 360 * 16);
		if(frame != lastFrame){
			col.updatePolygon(getPolygon());
		}
		lastFrame = frame;
	}

	public void render() {
		hud.render();
		GL11.glPushMatrix();
		// Render2d.DrawCircle(entity.pos, 30, 30);
		float x = entity.pos.getX();
		float y = entity.pos.getY();
		float size = 32;

		float retDis = MathUtil.distance(Reticle.getX(), Reticle.getY(), entity.pos.getX(), entity.pos.getY());
		float retDir = MathUtil.direction(entity.pos.getX(), entity.pos.getY(), Reticle.getX(), Reticle.getY()) + 180;
		float jumpSize = 32;
		Render2d.squareRot(MathUtil.getXSpeed(retDir, retDis) + x, MathUtil.getYSpeed(retDir, retDis) + y, -4, -4, 4, 4, 0, new float[] { 1, 1, 1, 1 }, Game.Assets.RETICLE2);
		while (retDis > jumpSize) {
			retDis -= jumpSize;
			Render2d.squareRot(MathUtil.getXSpeed(retDir, retDis) + x, MathUtil.getYSpeed(retDir, retDis) + y, -4, -4.5f, 4, 3.5f, 0, new float[] { 1, 1, 1, 1 }, Game.Assets.RETICLE3);
		}

		// GL11.glPushMatrix();
		// GL11.glEnable(GL11.GL_DEPTH_TEST);
		// GL11.glTranslatef(entity.pos.getX(), entity.pos.getY(), 0);
		// GL11.glRotatef(entity.rotation, 0, 0, 1);
		// GL11.glRotatef(0, 1, 0, 0);
		// GL11.glScalef(10, 10, 10);
		// Render3d.Model(new Vertex3f(0,0,0), Game.Assets.SHIP_PLAYER_DOME, Endogen.SystemAssets.mask.BLANK, new float[]{.2f,.2f,.5f,1});
		// Render3d.Model(new Vertex3f(0,0,0), Game.Assets.SHIP_PLAYER_HULL, Endogen.SystemAssets.mask.BLANK, new float[]{.7f,.3f,.2f,1});
		// Render3d.Model(new Vertex3f(0,0,0), Game.Assets.SHIP_PLAYER_FUEL1, Endogen.SystemAssets.mask.BLANK, new float[]{.1f,.1f,.9f,1});
		// Render3d.Model(new Vertex3f(0,0,0), Game.Assets.SHIP_PLAYER_ENGINE1, Endogen.SystemAssets.mask.BLANK, new float[]{.1f,.1f,.1f,1});
		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glPopMatrix();
		Render2d.squareRot(x, y, -size, -size, size, size, (frame % 4) / 4f, (frame / 4) / 4f + (1 / 4f), (frame % 4) / 4f + (1 / 4f), (frame / 4) / 4f, entity.rotation, new float[] { 1, 1, 1, 1 }, Game.Assets.PLAYERSHIP);

		// Render2d.square(x-size, y-size, size*2, size*2, new float[]{1,1,1,1}, Game.Assets.PERFUCTSHUP);
		GL11.glPopMatrix();
		//CollidorUtil.renderCollidor(col, true, true, true, true);
	}

	public void takeDamage(int damage) {
		HP -= damage;
		SceneGraph.freezeFrames += damage / 10;
	}
	
	public Vertex2f[] getPolygon(){
		int i = frame > 7 ? 16 - frame : frame;
		return new Vertex2f[] {
				new Vertex2f(Width, 0),
				new Vertex2f(0, -heights[i]),
				new Vertex2f(-Width, 0),
				new Vertex2f(0, heights[i])
		};
	}
	
	public void unload(){
		entity = null;
		col.unload();
		hud = null;
		Reticle = null;
		ammo = null;
	}

}
