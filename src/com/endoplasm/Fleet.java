package com.endoplasm;

import java.util.ArrayList;

public class Fleet {
	
	public Faction faction;
	public Vertex2f centre;
	public ArrayList<Enemy_Preset> members = new ArrayList<Enemy_Preset>();
	public boolean alive = true;
	
	public Fleet(Faction faction){
		SceneGraph.fleets.add(this);
	}
	
	public void add(Enemy_Preset newMember){
			members.add(newMember);
	
	}
	
	public void update(){
		alive = (members.size() == 0);
	}
	
	public void unload(){
		faction = null;
		centre = null;
		members.clear();
		members = null;
	}
}
