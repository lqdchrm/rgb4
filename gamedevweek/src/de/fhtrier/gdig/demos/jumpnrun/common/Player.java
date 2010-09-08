package de.fhtrier.gdig.demos.jumpnrun.common;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import de.fhtrier.gdig.demos.jumpnrun.client.input.InputControl;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryAction;
import de.fhtrier.gdig.demos.jumpnrun.common.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.demos.jumpnrun.common.entities.physics.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.PlayerData;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.StateColor;
import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.entities.gfx.AnimationEntity;
import de.fhtrier.gdig.engine.graphics.Shader;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;

public class Player extends LevelCollidableEntity {

	// HACK make it private
	public int currentState = -1;
	private final AnimationEntity idleImage;
	private final Entity playerGroup;
	private final AnimationEntity runAnimation;
	private final AnimationEntity jumpAnimation;
	private final Animation jump;

	private final float playerHalfWidth = 48;

	private PlayerState state;
	private static Shader playerShader = null;
	private static Image playerGlow = null;
	private static Image weaponGlow = null;
	private AnimationEntity weapon; // has to be an own class-object!
	
	public Player(int id, Factory factory) throws SlickException {
		super(id, EntityType.PLAYER);

		state = new PlayerState();
		state.name = "Player";
		state.health = 1;
		state.ammo = 1;
		state.damage = 0.2f;
		state.shootDirection = PlayerActionState.RunLeft;
		state.color = StateColor.RED; // player gets default-color: red
		state.weaponColor = StateColor.RED; // weapon of player get
											// default-color: red

		AssetMgr assets = factory.getAssetMgr();

		// gfx
		assets.storeAnimation(Assets.PlayerIdleAnimId, Assets.PlayerIdleAnimImagePath);
		assets.storeAnimation(Assets.PlayerRunAnimId, Assets.PlayerRunAnimImagePath);
		assets.storeAnimation(Assets.WeaponImageId, Assets.WeaponAnimImagePath); //TODO: change weapon dummy
		
		this.jump = assets.storeAnimation(Assets.PlayerJumpAnimId, Assets.PlayerIdleAnimImagePath);
		this.jump.setLooping(false);

		this.idleImage = factory.createAnimationEntity(Assets.PlayerIdleAnimId, Assets.PlayerIdleAnimId);
		this.runAnimation = factory.createAnimationEntity(Assets.PlayerRunAnimId,
				Assets.PlayerRunAnimId);
		this.jumpAnimation = factory.createAnimationEntity(
				Assets.PlayerJumpAnimId, Assets.PlayerJumpAnimId);
		this.weapon = factory.createAnimationEntity(Assets.WeaponImageId, Assets.WeaponImageId);

		int groupId = factory.createEntity(EntityOrder.Player,
				EntityType.HELPER);

		this.playerGroup = factory.getEntity(groupId);

		this.playerGroup.getData()[Entity.CENTER_X] = assets.getAnimation(
				Assets.PlayerIdleAnimId).getWidth() / 2;
		this.playerGroup.getData()[Entity.CENTER_Y] = assets.getAnimation(
				Assets.PlayerIdleAnimId).getHeight() / 2;

		this.playerGroup.add(this.idleImage);
		this.playerGroup.add(this.runAnimation);
		this.playerGroup.add(this.jumpAnimation);
		
		this.playerGroup.add(this.weapon);

		this.add(this.playerGroup);

		// physics
		// X Y OX OY SY SY ROT

		initData(new float[] { 200, 200, 65, 70, 1, 1, 0 }); // pos +
																// center of
																// rotation +
																// scale +
																// rot
		
		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, GamePlayConstants.gravity, 0, 0, 0, 0, 0 }); // gravity

		CollisionManager.addEntity(this);

		// set bounding box according to idle animation size
		int x = 35;
		int width = assets.getAnimation(Assets.PlayerIdleAnimId).getWidth() - 70;
		int height = assets.getAnimation(Assets.PlayerIdleAnimId).getHeight();
		setBounds(new Rectangle(x, 0, width, height)); // bounding box

		if (playerShader == null && Constants.Debug.shadersActive)
		{
			playerShader = new Shader("content/jumpnrun/shader/simple.vert",
			"content/jumpnrun/shader/playercolor.frag");
			playerGlow = new Image("content/jumpnrun/shader/playerGlow.png");
			weaponGlow = new Image("content/jumpnrun/shader/weaponGlow.png");
		}
			
		setVisible(true);
		weapon.setVisible(true);
		// order
		this.setOrder(EntityOrder.Player);

		// startup
		setState(PlayerActionState.Idle);
	}

	@Override
	public void applyNetworkData(final NetworkData networkData) {
		super.applyNetworkData(networkData);

		if ((this.currentState == PlayerActionState.Idle)
				&& (Math.abs(getData()[X] - getPrevPos()[X]) < Constants.EPSILON)
				&& (Math.abs(getData()[Y] - getPrevPos()[Y]) < Constants.EPSILON)) {
			getVel()[X] = getVel()[Y] = 0.0f;
		}
		this.setState(((PlayerData) networkData).getState());
	}

	private void enterState(final int state) {
		this.currentState = state;
		switch (state) {
		case PlayerActionState.Idle:
			this.getAcc()[Entity.X] = 0.0f;
			this.idleImage.setVisible(true);
			break;
		case PlayerActionState.RunLeft:
			this.getAcc()[Entity.X] = -Constants.GamePlayConstants.playerWalkVel;
			this.playerGroup.getData()[Entity.SCALE_X] = 1;
			this.runAnimation.setVisible(true);
			this.state.shootDirection = state;
			break;
		case PlayerActionState.RunRight:
			this.getAcc()[Entity.X] = Constants.GamePlayConstants.playerWalkVel;
			this.playerGroup.getData()[Entity.SCALE_X] = -1;
			this.runAnimation.setVisible(true);
			this.state.shootDirection = state;
			break;
		case PlayerActionState.Jump:
			this.getVel()[Entity.Y] = -Constants.GamePlayConstants.playerJumpVel;
			this.jump.start();
			this.jumpAnimation.setVisible(true);
			break;
		}
	}

	@Override
	public boolean handleCollisions() {
		if (!isActive()) {
			return false;
		}

		boolean result = super.handleCollisions();

		// HACK for debug only
		if (Constants.Debug.showCollisions) {
			if (CollisionManager.collidingEntities(this).size() != 0) {
				this.map.setTileId(0, 0, 0, 0);
			} else {
				this.map.setTileId(0, 0, 0, 13);
			}
		}

		return result;
	}

	// input
	@Override
	public void handleInput(final Input input) {
		if (this.isActive()) {
			if (!InputControl.isRefKeyDown(InputControl.REFWALKLEFT)
					&& !InputControl.isRefKeyDown(InputControl.REFWALKRIGHT)
					&& !InputControl.isRefKeyDown(InputControl.REFJUMP)) {
				this.setState(PlayerActionState.Idle);
			}

			if (InputControl.isRefKeyDown(InputControl.REFWALKLEFT)) {
				this.setState(PlayerActionState.RunLeft);
			}

			if (InputControl.isRefKeyDown(InputControl.REFWALKRIGHT)) {
				this.setState(PlayerActionState.RunRight);
			}

			if (InputControl.isRefKeyPressed(InputControl.REFJUMP)) {
				if (this.isOnGround()) {
					this.setState(PlayerActionState.Jump);
				}
			}

			if (InputControl.isRefKeyPressed(InputControl.REFFIRE)) {
				NetworkComponent.getInstance().sendCommand(
						new QueryAction(PlayerAction.SHOOT));
			}

			// change player color
			if (InputControl.isRefKeyPressed(InputControl.REFCHANGECOLOR)) {
				nextColor();
				
				NetworkComponent.getInstance().sendCommand(
						new QueryAction(PlayerAction.PLAYERCOLOR));
			}

			// change weapon color
			if (InputControl.isRefKeyPressed(InputControl.REFCHANGEWEAPON)) {
				nextWeaponColor ();
				
				NetworkComponent.getInstance().sendCommand(
						new QueryAction(PlayerAction.WEAPONCOLOR));
			}
		}
		super.handleInput(input);
	}
	
	public void nextColor () {
		PlayerState state = this.getState();
		
		state.color = state.color << 1;
		if (state.color > StateColor.BLUE) {
			state.color = StateColor.RED;
		}
	}
	
	public void nextWeaponColor () {
		PlayerState state = this.getState();
		
		state.weaponColor = state.weaponColor << 1;
		if (state.weaponColor > StateColor.BLUE) {
			state.weaponColor = StateColor.RED;
		}
	}

	// network
	@Override
	protected NetworkData _createNetworkData() {
		return new PlayerData(getId());
	}

	@Override
	public NetworkData getNetworkData() {
		PlayerData result = (PlayerData) super.getNetworkData();
		result.state = this.currentState;

		return result;
	}

	private void leaveState(int state) {
		switch (state) {
		case PlayerActionState.Idle:
			this.idleImage.setVisible(false);
			break;
		case PlayerActionState.RunLeft:
		case PlayerActionState.RunRight:
			this.runAnimation.setVisible(false);
			break;
		case PlayerActionState.Jump:
			this.jumpAnimation.setVisible(false);
		}
	}

	// render
	@Override
	public void renderImpl(final Graphics g, Image frameBuffer) {

		if (this.getId() == -1) {
			throw new RuntimeException("Wrong Initialization: no Client ID set");
		}
		
		if (Constants.Debug.shadersActive)
		{
			Shader.setActiveShader(playerShader);
			Shader.activateAdditiveBlending();
			float weaponX = this.getData(CENTER_X);
			float weaponY = this.getData(CENTER_Y)-weaponGlow.getHeight()/2+40;
			int lookDirection = 1;
			if (this.getState().shootDirection == PlayerActionState.RunLeft) lookDirection = -1;
			
			playerShader.setValue("playercolor", StateColor.constIntoColor(this.getState().weaponColor));
			g.drawImage(weaponGlow, weaponX, weaponY, weaponX + weaponGlow.getWidth()*lookDirection,
					weaponY+weaponGlow.getHeight(), 0, 0, weaponGlow.getWidth(), weaponGlow.getHeight());
			
			playerShader.setValue("playercolor", StateColor.constIntoColor(this.getState().color));
			g.drawImage(playerGlow, this.getData(CENTER_X)-playerGlow.getWidth()/2,
					this.getData(CENTER_Y)-playerGlow.getHeight()/2);
			
			Shader.activateDefaultBlending();
		}
		
		super.renderImpl(g, frameBuffer);
		
		if (Constants.Debug.shadersActive)
		{
			Shader.setActiveShader(null);
		}
		
		if (this.state.name != null) {
			float x = playerHalfWidth - g.getFont().getWidth(state.name) / 2.0f;
			float y = -g.getFont().getHeight(state.name);
			g.setColor(StateColor.constIntoColor(state.color)); // colors the
																// name of
																// player with
																// his color
			g.drawString(state.name + " " + getId(), x, y);
			g.setColor(StateColor.constIntoColor(state.weaponColor));
			g.drawString("Weapon", x, y + 80);

			g.setColor(Color.white);
		}

	}

	public void setLevel(final Level level) {
		this.setMap(level.getMap());
	}

	// game logic
	public void setState(final int state) {
		if (state != this.currentState) {
			this.leaveState(this.currentState);
			this.enterState(state);
		}
	}

	// update
	@Override
	public void update(final int deltaInMillis) {

		if (this.isActive()) {

			if (isOnGround() && currentState == PlayerActionState.Idle)
				setDrag(0.005f);
			else
				setDrag(0.0000001f);

			super.update(deltaInMillis); // calc physics

			if (this.getVel()[Entity.X] > Constants.GamePlayConstants.playerMaxSpeed) {
				this.getVel()[Entity.X] = Constants.GamePlayConstants.playerMaxSpeed;
			}

			if (this.getVel()[Entity.Y] > Constants.GamePlayConstants.playerMaxSpeed) {
				this.getVel()[Entity.Y] = Constants.GamePlayConstants.playerMaxSpeed;
			}

			if (this.getVel()[Entity.X] < -Constants.GamePlayConstants.playerMaxSpeed) {
				this.getVel()[Entity.X] = -Constants.GamePlayConstants.playerMaxSpeed;
			}

			if (this.getVel()[Entity.Y] < -Constants.GamePlayConstants.playerMaxSpeed) {
				this.getVel()[Entity.Y] = -Constants.GamePlayConstants.playerMaxSpeed;
			}

			if (this.currentState == PlayerActionState.Idle
					&& Math.abs(this.getData()[Entity.X]
							- this.getPrevPos()[Entity.X]) < Constants.EPSILON
					&& Math.abs(this.getData()[Entity.Y]
							- this.getPrevPos()[Entity.Y]) < Constants.EPSILON) {
				this.getVel()[Entity.X] = this.getVel()[Entity.Y] = 0.0f;
			}
		}
	}

	public PlayerState getState() {
		return state;
	}
	
	public void die () {
		this.respawn();
	}
	
	public void respawn () {
		state.health = 1.0f;
		state.ammo = 1.0f;
		state.damage = 0.2f;
		state.shootDirection = PlayerActionState.RunLeft;
		state.color = StateColor.RED; // player gets default-color: red
		state.weaponColor = StateColor.RED; // weapon of player get
											// default-color: red
		
		initData(new float[] { 200, 200, 65, 70, 1, 1, 0 });
		
		setState(PlayerActionState.RunRight);
		setState(PlayerActionState.Idle);
	}
}
