package com.endoplasm;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class SceneGraph {

	public static PlayerShip player;
	public static GCamera gCamera;
	public static QuadTree QuadTree;

	// outside forces
	public static PSystem system;
	public static ArrayList<Particle> foreParticle;
	public static ArrayList<Particle> backParticle;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Fleet> fleets;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Entity> gravity;

	public static void NewGame() {
		QuadTree = new QuadTree(3, false);
		QuadTree.Assign(new Vertex2f(0, 0), new Vertex2f(0, 0));
		gravity = new ArrayList<Entity>();
		system = MoonGenerator.makeSystem();
		player = new PlayerShip(new Vertex2f(0, 0));
		gCamera = new GCamera(new Vertex2f(0, 0), 100);
		foreParticle = new ArrayList<Particle>();
		backParticle = new ArrayList<Particle>();
		enemies = new ArrayList<Enemy>();
		fleets = new ArrayList<Fleet>();
		bullets = new ArrayList<Bullet>();
		//gen();
	}
	
	public static void Restart(){
		if(player != null){
			player.unload();
			player = null;
		}
		if(QuadTree != null){
			QuadTree.unload();
			QuadTree = null;
		}
		if(foreParticle != null){
			for(Particle p: foreParticle){
				p.unload();
			}
			foreParticle.clear();
		}
		if(backParticle != null){
			for(Particle p: backParticle){
				p.unload();
			}
			backParticle.clear();
		}
		if(enemies != null){
			for(Enemy e: enemies) e.unload();
			enemies.clear();
			enemies = null;
		}
		if(fleets != null){
			for(Fleet f: fleets) f.unload();
			fleets.clear();
			fleets = null;
		}
		if(bullets != null){
			bullets.clear();
			bullets = null;
		}
		if(bullets != null){
			bullets.clear();
			bullets = null;
		}
		if(system != null){
			system.unload();
			system = null;
		}
	}
	
	public static void gen(){
		Fleet f = new Fleet(Faction.Alien);
		
//		new Enemy_Sniper(new Vertex2f(500, 50), f);
//		new Enemy_Sniper(new Vertex2f(500, 50), f);
		
//		new Enemy_Spam(new Vertex2f(500, 50), f);
//		new Enemy_Spam(new Vertex2f(500, 50), f);
//		new Enemy_Spam(new Vertex2f(500, 50), f);
//		
		new Enemy_Slug(new Vertex2f(500, 50), f);
		new Enemy_Slug(new Vertex2f(500, 50), f);
		new Enemy_Slug(new Vertex2f(500, 50), f);
		new Enemy_Slug(new Vertex2f(500, 50), f);
		new Enemy_Slug(new Vertex2f(500, 50), f);
//
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
//		new Enemy_Dumb(new Vertex2f(500, 50), f);
		
	}
	
	public static boolean gened = false;
	public static int freezeFrames = 0;

	public static void update() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) return;
		if(freezeFrames > 0) {
			freezeFrames--;
			return;
		}
		
		if (QuadTree != null) {
			QuadTree.resolve();
			Game.addDebugLine(QuadTree.numTests +  " collision tests");
			if(Keyboard.isKeyDown(Keyboard.KEY_C)) QuadTree.useNonaTop = true;
			if(Keyboard.isKeyDown(Keyboard.KEY_V)) QuadTree.useNonaTop = false;
		}
		if (gCamera != null) gCamera.set();
		if (player != null) player.update();
		if (enemies != null) for (int i = 0; i < enemies.size();) {
			enemies.get(i).update();
			if(!enemies.get(i).alive){
				enemies.remove(i);
			} else i++;
		}
		if (fleets != null) for (int i = 0; i < fleets.size();) {
			fleets.get(i).update();
			if(!fleets.get(i).alive){
				fleets.remove(i);
			} else i++;
		}
		if(system != null){
			system.update();
		}
		for(int i = 0; i < foreParticle.size();){
			foreParticle.get(i).update();
			if(!foreParticle.get(i).alive) {
				foreParticle.remove(i);
			} else i++;
		}
		for(int i = 0; i < backParticle.size(); ){
			backParticle.get(i).update();
			if(!backParticle.get(i).alive) {
				backParticle.remove(i);
			} else i++;
		}
		if (bullets != null) for (int i = 0; i < bullets.size();) {
			bullets.get(i).update();
			if(!bullets.get(i).alive){
				bullets.remove(i);
			} else i++;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			if(!gened){
				gen();
			}
			gened = true;
		} else {
			gened = false;
		}
	}

	public static void render() {
		GL11.glPushMatrix();
		GL11.glDisable(GL_DEPTH_TEST);
		if (gCamera != null) {
			Background.render();
			QuadTree.Root.render();
			for(int i = 0; i < backParticle.size(); i++){
				backParticle.get(i).render();
			}
			if(system != null){
				system.renderPlanets();
			}
			if (player != null) {
				player.render();
			}
			if (enemies != null) for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).render();
			}
			if (bullets != null) for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).render();
			}
			for(int i = 0; i < foreParticle.size(); i++){
				foreParticle.get(i).render();
			}
		} else {
			System.out.println("Attempted to render when gCam == null, didn't render scenegraph");
		}
		GL11.glPopMatrix();
	}

}
