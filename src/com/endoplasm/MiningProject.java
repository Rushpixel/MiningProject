package com.endoplasm;

public class MiningProject {

	public static void main(String[] args) {
		Game game = new Game();
		Endogen e = new Endogen(game);
		e.Begin();
	}

}
