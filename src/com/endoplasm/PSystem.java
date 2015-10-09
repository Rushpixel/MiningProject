package com.endoplasm;

import java.util.ArrayList;

public class PSystem {
	
	public ArrayList<Moon> moons = new ArrayList<Moon>();
	
	public void update(){
		for(Moon m: moons){
			m.update();
		}
	}
	
	public void renderPlanets(){
		for(Moon m: moons){
			m.render();
		}
	}
	
	public void unload(){
		for(Moon m: moons){
			m.unload();
		}
		moons.clear();
		moons = null;
	}

}
