package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.common.network.EntityData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.PlayerState;
import de.fhtrier.gdig.engine.entities.gfx.AnimationEntity;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;

public class Player extends LevelCollidableEntity {

	private static final Color[] PlayerColors = { Color.blue, Color.green,
			Color.red, Color.yellow, Color.black };

	private static float EPSILON = 0.0001f;

	private int currentState = -1;
	private ImageEntity idleImage;
	private AnimationEntity runAnimation;
	private AnimationEntity jumpAnimation;
	private Animation jump;

	private float maxPlayerSpeed = 1000.0f;

	private EntityData playerData;

	public Player(int id, Factory factory) throws SlickException {
		super(id);

		AssetMgr assets = factory.getAssetMgr();

		// gfx
		assets.storeImage(Assets.PlayerIdleImage, "sprites/player/Idle.png");
		assets.storeAnimation(Assets.PlayerRunAnim, "sprites/player/Run.png",
				96, 96, 75);
		this.jump = assets.storeAnimation(Assets.PlayerJumpAnim,
				"sprites/player/Jump.png", 96, 96, 70);
		this.jump.setLooping(false);

		this.idleImage = factory.createImageEntity(Assets.PlayerIdleImage,
				Assets.PlayerIdleImage);
		this.runAnimation = factory.createAnimationEntity(Assets.PlayerRunAnim,
				Assets.PlayerRunAnim);
		this.jumpAnimation = factory.createAnimationEntity(
				Assets.PlayerJumpAnim, Assets.PlayerJumpAnim);

		add(this.idleImage);
		add(this.runAnimation);
		add(this.jumpAnimation);

		// physics
		// X Y OX OY FX FY SY SY ROT
		initData(new float[] { 200, 200, 48, 96, 0, 0, 1, 1, 0 }); // pos +
																	// origin +
																	// focus +
																	// scale +
																	// rot
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, 981, 0, 0, 0, 0, 0, 0, 0 }); // gravity
		setBounds(new Rectangle(-18, -96, 36, 96)); // bounding box

		setVisible(true);
		setActive(true);

		// order
		setOrder(EntityOrder.Player);

		// startup
		setState(PlayerState.Idle);
	}

	@Override
	public void update(int deltaInMillis) {

		if (isActive()) {

			super.update(deltaInMillis); // calc physics

			if (getVel()[X] > this.maxPlayerSpeed) {
				getVel()[X] = this.maxPlayerSpeed;
			}

			if (getVel()[Y] > this.maxPlayerSpeed) {
				getVel()[Y] = this.maxPlayerSpeed;
			}

			if (getVel()[X] < -this.maxPlayerSpeed) {
				getVel()[X] = -this.maxPlayerSpeed;
			}

			if (getVel()[Y] < -this.maxPlayerSpeed) {
				getVel()[Y] = -this.maxPlayerSpeed;
			}

			markCollisionTiles(12);
			handleCollisions();

			if ((this.currentState == PlayerState.Idle)
					&& (Math.abs(getData()[X] - getPrevPos()[X]) < EPSILON)
					&& (Math.abs(getData()[Y] - getPrevPos()[Y]) < EPSILON)) {
				getVel()[X] = getVel()[Y] = 0.0f;
			}
		}
	}

	@Override
	public void renderImpl(Graphics graphicContext) {

		if (getId() == -1) {
			throw new RuntimeException("Wrong Initialization: no Client ID set");
		}

		graphicContext.setColor(PlayerColors[getId() % PlayerColors.length]);

		super.renderImpl(graphicContext);
	}

	@Override
	public void handleInput(Input input) {
		if (isActive()) {
			if (!input.isKeyDown(Input.KEY_LEFT)
					&& !input.isKeyDown(Input.KEY_RIGHT)
					&& !input.isKeyDown(Input.KEY_SPACE)) {
				setState(PlayerState.Idle);
			}

			if (input.isKeyDown(Input.KEY_LEFT)) {
				setState(PlayerState.RunLeft);
			}

			if (input.isKeyDown(Input.KEY_RIGHT)) {
				setState(PlayerState.RunRight);
			}

			if (input.isKeyDown(Input.KEY_UP)) {
				if (isOnGround()) {
					setState(PlayerState.Jump);
				}
			}
		}
		super.handleInput(input);
	}

	public void setState(int state) {
		if (state != this.currentState) {
			leaveState(this.currentState);
			enterState(state);
		}
	}

	private void leaveState(int state) {
		switch (state) {
		case PlayerState.Idle:
			this.idleImage.setActive(false);
			this.idleImage.setVisible(false);
			break;
		case PlayerState.RunLeft:
		case PlayerState.RunRight:
			this.runAnimation.setActive(false);
			this.runAnimation.setVisible(false);
			break;
		case PlayerState.Jump:
			this.jumpAnimation.setActive(false);
			this.jumpAnimation.setVisible(false);
		}
	}

	private void enterState(int state) {
		this.currentState = state;
		switch (state) {
		case PlayerState.Idle:
			getAcc()[X] = 0.0f;
			this.idleImage.setActive(true);
			this.idleImage.setVisible(true);
			break;
		case PlayerState.RunLeft:
			getAcc()[X] = -2000.0f;
			getData()[SX] = 1;
			this.runAnimation.setActive(true);
			this.runAnimation.setVisible(true);
			break;
		case PlayerState.RunRight:
			getAcc()[X] = 2000.0f;
			getData()[SX] = -1;
			this.runAnimation.setActive(true);
			this.runAnimation.setVisible(true);
			break;
		case PlayerState.Jump:
			getVel()[Y] = -800;
			this.jump.start();
			this.jumpAnimation.setActive(true);
			this.jumpAnimation.setVisible(true);
			break;
		}
	}

	public EntityData getPlayerData() {
		if (this.playerData == null) {
			this.playerData = new EntityData();
			this.playerData.id = getId();
		}

		this.playerData.state = this.currentState;
		this.playerData.data = getData();

		return this.playerData;
	}

	public void setLevel(Level level) {
		this.setMap(level.getMap());
	}
}
