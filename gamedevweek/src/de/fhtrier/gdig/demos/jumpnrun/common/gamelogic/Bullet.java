package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.Player;
import de.fhtrier.gdig.demos.jumpnrun.common.network.BulletData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.shader.Shader;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.physics.CollisionManager;

public class Bullet extends Projectile {

	public AnimationEntity bullet;
	public AssetMgr assets;
	private static Image bulletGlow;
	private int flightTime;
	private final static float AMPLITUDE = 40;
	private final static float FREQUENCY = 0.8f;
	private float offset;
	private double startValue;

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public Bullet(int id, Factory factory) throws SlickException {
		super(id, EntityType.BULLET);

		assets = new AssetMgr();

		// gfx
		assets.storeAnimation(Assets.Bullet.AnimId, Assets.Bullet.AnimPath);
		bullet = factory.createAnimationEntity(EntityOrder.Bullet,
				Assets.Bullet.AnimId, assets);

		bullet.setVisible(true);
		add(bullet);

		// physics
		// X Y OX OY SX SY ROT
		initData(new float[] { 200, 200, 14, 32, 1, 1, 0 }); // pos +
																// center +
																// scale +
																// rot

		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // gravity

		setBounds(new Rectangle(10, 28, 8, 8)); // bounding box

		CollisionManager.addEntity(this);

		if (bulletGlow == null) {
			bulletGlow = new Image(
					assets.getPathRelativeToAssetPath(Assets.Bullet.GlowImagePath));
		}

		// setup
		setVisible(true);
	}

	@Override
	public void applyNetworkData(NetworkData networkData) {
		super.applyNetworkData(networkData);

		if (networkData instanceof BulletData) {
			this.color = ((BulletData) networkData).getColor();
		} else {
			throw new RuntimeException("Wrong package received");
		}
	}

	@Override
	protected NetworkData _createNetworkData() {
		return new BulletData(getId());
	}

	@Override
	public NetworkData getNetworkData() {
		BulletData result = (BulletData) super.getNetworkData();
		result.bulletColor = this.color;

		return result;
	}
	
	@Override
	public void update(int deltaInMillis) {
		getData()[Entity.Y] = getData()[Entity.Y] + offset * AMPLITUDE;
		super.update(deltaInMillis);
		offset = ((float)-Math.cos((Math.PI*2*(startValue + flightTime/1000.0f))*FREQUENCY)) + 1.0f;
		flightTime += deltaInMillis;
		getData()[Entity.Y] = getData()[Entity.Y] - offset * AMPLITUDE;
	}

	@Override
	protected void preRender(Graphics graphicContext) {
		super.preRender(graphicContext);

		Color bulletCol = StateColor.constIntoColor(this.color);

		if (Constants.Debug.shadersActive) {
			Shader.pushShader(Player.getColorGlowShader());
			Player.getColorGlowShader().setValue("playercolor", bulletCol);
		}

		graphicContext.setColor(Color.white);
		Shader.activateAdditiveBlending();

		graphicContext.drawImage(bulletGlow, this.getData(CENTER_X)
				- bulletGlow.getWidth() / 2.0f, this.getData(CENTER_Y)
				- bulletGlow.getHeight() / 2.0f);

		Shader.activateDefaultBlending();

		if (Constants.Debug.shadersActive) {
			Shader.popShader();
		}
	}

	@Override
	protected void postRender(Graphics graphicContext) {
		super.postRender(graphicContext);
	}
}
