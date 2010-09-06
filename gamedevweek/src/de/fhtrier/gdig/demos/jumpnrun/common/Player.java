package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryAction;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.PlayerData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.PlayerState;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.gfx.AnimationEntity;
import de.fhtrier.gdig.engine.entities.gfx.ImageEntity;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class Player extends LevelCollidableEntity {

	// HACK make it private
	public int currentState = -1;
	private ImageEntity idleImage;
	private Entity playerGroup;
	private AnimationEntity runAnimation;
	private AnimationEntity jumpAnimation;
	private Animation jump;

	private float maxPlayerSpeed = 1000.0f;
	private float playerHalfWidth = 48;
	
    public PlayerState state;

	public Player(int id, Factory factory) throws SlickException {
		super(id);
		
		state = new PlayerState();
		state.name = "Player";
		state.color = Constants.StateColor.RED; // player gets default-color: red
		state.weaponColor = Constants.StateColor.RED; // weapon of player get default-color: red
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
		
		playerGroup = factory.createEntity(EntityOrder.Player);
		
		playerGroup.getData()[CENTER_X] = 48;
		playerGroup.getData()[CENTER_Y] = 48;
		
		playerGroup.add(this.idleImage);
		playerGroup.add(this.runAnimation);
		playerGroup.add(this.jumpAnimation);

		add(playerGroup);
		
		// physics
		// X Y OX OY SY SY ROT
		initData(new float[] { 200, 200, 0, 0, 1, 1, 0 }); // pos +
																	// center of rotation +
																	// scale +
																	// rot
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, GamePlayConstants.gravity, 0, 0, 0, 0, 0 }); // gravity
		setBounds(new Rectangle(30, 0, 36, 96)); // bounding box

		setVisible(true);
		
		// order
		setOrder(EntityOrder.Player);

		// startup
		setActive(true);
		setVisible(true);
		setState(PlayerState.Idle);
	}
	
	// update
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

			if ((this.currentState == PlayerState.Idle)
					&& (Math.abs(getData()[X] - getPrevPos()[X]) < Constants.EPSILON)
					&& (Math.abs(getData()[Y] - getPrevPos()[Y]) < Constants.EPSILON)) {
				getVel()[X] = getVel()[Y] = 0.0f;
			}
		}
	}

	
	// render
	@Override
	public void renderImpl(Graphics g) {

		if (getId() == -1) {
			throw new RuntimeException("Wrong Initialization: no Client ID set");
		}
		
		
		super.renderImpl(g);

		if (state.name != null)
		{
			float x = playerHalfWidth - g.getFont().getWidth(state.name)/2.0f;
			float y = -g.getFont().getHeight(state.name);
			g.setColor(Constants.StateColor.constIntoColor( state.color )); // colors the name of player with his color
			
			g.drawString(state.name + " " + state.weaponColor, x,y);
			g.setColor(Color.white); // default-color when changed
		}
		
	}

	// input
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
			
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				NetworkComponent.getInstance().sendCommand(new QueryAction(PlayerAction.DROPGEM));
			}
		}
		super.handleInput(input);
	}

	@Override
	public boolean handleCollisions() {
		markCollisionTiles(12);
		return super.handleCollisions();
	}
	
	// network
	@Override
	protected NetworkData _createNetworkData() {
		return new PlayerData(getId());
	}
	
	@Override
	public NetworkData getNetworkData() {
		PlayerData result = (PlayerData)super.getNetworkData();
		result.state = this.currentState;
		
		return result;
	}

	@Override
	public void applyNetworkData(NetworkData networkData) {
		super.applyNetworkData(networkData);
		
		this.setState(((PlayerData)networkData).getState());
	}

	
	
	// game logic
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
			playerGroup.getData()[SCALE_X] = 1;
			this.runAnimation.setActive(true);
			this.runAnimation.setVisible(true);
			break;
		case PlayerState.RunRight:
			getAcc()[X] = 2000.0f;
			playerGroup.getData()[SCALE_X] = -1;
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
}
