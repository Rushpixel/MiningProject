package com.endoplasm;

import org.lwjgl.opengl.GL11;

public class HUD {

	
	public void render(){
		healthBar();
	}
	
	private void healthBar(){
		GL11.glPushMatrix();
		GL11.glTranslatef(SceneGraph.player.entity.pos.getX(), SceneGraph.player.entity.pos.getY(), 0);
		float startRot = 0;
		float endRot = 90;
		float innerRad = 35;
		float thickness = 30;
		int numSegments = 30;
		float ifac = (endRot - startRot) / numSegments * ((float) SceneGraph.player.HP) / SceneGraph.player.maxHP;
		GL11.glColor4f(.1f, .4f, 1, 0.6f);
		Endogen.SystemAssets.mask.BLANK.TEX.bind();
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		for(int i = 0; i <= numSegments; i++){
			GL11.glVertex2f(MathUtil.getXSpeed(i*ifac + startRot, innerRad), MathUtil.getYSpeed(i*ifac + startRot, innerRad));
			GL11.glVertex2f(MathUtil.getXSpeed(i*ifac + startRot, innerRad + thickness), MathUtil.getYSpeed(i*ifac + startRot, innerRad + thickness));
		}
		GL11.glEnd();
		
		Render2d.square(0, innerRad, -4, thickness, new float[]{.1f, .4f, 1, 0.6f}, Endogen.SystemAssets.mask.BLANK);
		Text.renderTextFromString(SceneGraph.player.HP+"", innerRad, -2, 8, 10, -1, 2, Endogen.SystemAssets.mask.FONT1, new float[]{.1f, .4f, 1, 0.8f} );
		GL11.glPopMatrix();
	}
	
}
