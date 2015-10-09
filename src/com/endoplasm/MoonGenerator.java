package com.endoplasm;

public class MoonGenerator {
	
	
	public static PSystem makeSystem(){
		PSystem system = new PSystem();
		system.moons.add(new Moon(new Vertex2f(1200,0), 60, 0, 300));
		return system;
	}
	
//	public static Moon NoisePass(Moon moon, float noiseFactor){
//		for(int i = 0; i < moon.heightMap.length; i++){
//			moon.heightMap[i] += moon.r.nextFloat() * noiseFactor - (noiseFactor / 2);
//		}
//		return moon;
//	}
//	
//	public static Moon ErosionPass(Moon moon, float erosionFactor){
//		float[] newHeight = moon.heightMap.clone();
//		for(int i = 0; i < moon.heightMap.length; i++){
//			 float a = (moon.heightMap[i-1<0?moon.heightMap.length-1:i-1] + moon.heightMap[i+1%moon.heightMap.length])/2;
//			 float dif = (a - moon.heightMap[i]);
//			 newHeight[i] += dif * erosionFactor;
//		}
//		moon.heightMap = newHeight;
//		return moon;
//	}
//	
//	public static Moon AddMountain(Moon moon, float MaxHeight){
//		int p = moon.r.nextInt(moon.heightMap.length);
//		moon.heightMap[p] += moon.r.nextFloat() * MaxHeight;
//		return moon;
//	}

}
