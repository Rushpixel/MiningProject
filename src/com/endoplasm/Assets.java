package com.endoplasm;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.CursorLoader;

	public class Assets extends AssetNodeGroup{
		
		public TextureAsset PERFUCTSHUP = new TextureAsset(this, "/Resources/Textures/perfectShip");
		public TextureAsset PLAYERSHIP = new TextureAsset(this, "/Resources/Textures/ship_player");
		public TextureAsset RETICLE2 = new TextureAsset(this, "/Resources/Textures/Reticle2");
		public TextureAsset RETICLE3 = new TextureAsset(this, "/Resources/Textures/Reticle3");
		public TextureAsset SUN = new TextureAsset(this, "/Resources/Textures/sun");
		public TextureAsset PLANET_BIGORANGE = new TextureAsset(this, "/Resources/Textures/planet_bigOrange");
		public TextureAsset ENEMY_DUMB = new TextureAsset(this, "/Resources/Textures/enemy_dumb");
		public TextureAsset ENEMY_SLUG = new TextureAsset(this, "/Resources/Textures/enemy_slug");
		public TextureAsset ENEMY_SPAM = new TextureAsset(this, "/Resources/Textures/enemy_spam");
		public TextureAsset ENEMY_SNIPER = new TextureAsset(this, "/Resources/Textures/enemy_sniper");
		public TextureAsset ENEMY_BIGGUN = new TextureAsset(this, "/Resources/Textures/enemy_biggun");
		public TextureAsset BULLETS = new TextureAsset(this, "/Resources/Textures/bullets");
		public TextureAsset P_SMOKE1 = new TextureAsset(this, "/Resources/Textures/smoke1");
		public TextureAsset P_BLAST = new TextureAsset(this, "/Resources/Textures/damageblast");
		
		public Cursor Reticle;
		public FontAsset ALPHA3 = new FontAsset(this, "/Resources/Textures/alpha3", 16, 22, 16, Endogen.SystemAssets.mask.FONT1SPACING);

		public Assets(AssetNode PARENT) {
			super(PARENT);
			INDEX = new AssetNode[]{
					ALPHA3,
					PERFUCTSHUP,
					PLAYERSHIP,
					RETICLE2,
					RETICLE3,
					SUN,
					PLANET_BIGORANGE,
					ENEMY_DUMB,
					ENEMY_SLUG,
					ENEMY_SPAM,
					ENEMY_SNIPER,
					ENEMY_BIGGUN,
					BULLETS,
					P_SMOKE1,
					P_BLAST,
			};
			
			try {
				Reticle = CursorLoader.get().getCursor("Resources/Textures/Reticle.png", 8, 8);
			} catch (LWJGLException | IOException e) {
				e.printStackTrace();
			}
		}

}
