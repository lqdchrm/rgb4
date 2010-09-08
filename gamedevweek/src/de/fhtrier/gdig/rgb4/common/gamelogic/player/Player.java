package de.fhtrier.gdig.rgb4.common.gamelogic.player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.engine.entities.Entity;
import de.fhtrier.gdig.engine.graphics.Shader;
import de.fhtrier.gdig.engine.helpers.IFiniteStateMachineListener;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.rgb4.client.input.InputControl;
import de.fhtrier.gdig.rgb4.client.network.protocol.QueryAction;
import de.fhtrier.gdig.rgb4.common.Level;
import de.fhtrier.gdig.rgb4.common.entities.physics.CollisionManager;
import de.fhtrier.gdig.rgb4.common.entities.physics.LevelCollidableEntity;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerAssetState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerJumpingState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerRunningState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerShootJumpingState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerShootRunningState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerShootStandingState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.PlayerStandingState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActionState;
import de.fhtrier.gdig.rgb4.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.rgb4.common.network.NetworkData;
import de.fhtrier.gdig.rgb4.common.network.PlayerData;
import de.fhtrier.gdig.rgb4.identifiers.Assets;
import de.fhtrier.gdig.rgb4.identifiers.Constants;
import de.fhtrier.gdig.rgb4.identifiers.Constants.GamePlayConstants;
import de.fhtrier.gdig.rgb4.identifiers.EntityOrder;
import de.fhtrier.gdig.rgb4.identifiers.EntityType;
import de.fhtrier.gdig.rgb4.identifiers.StateColor;

public class Player extends LevelCollidableEntity implements
		IFiniteStateMachineListener<PlayerActionState> {

	// helpers
	private static Shader playerShader = null;
	private Factory factory;
	private float playerHalfWidth = 48;

	// private AnimationEntity weapon; // has to be an own class-object!

	// "useful" members
	private PlayerCondition condition;

	// some states for gfx and a statemachine for logic
	private PlayerStandingState stateStanding;
	private PlayerShootStandingState stateShootStanding;
	private PlayerRunningState stateRunning;
	private PlayerShootRunningState stateShootRunning;
	private PlayerJumpingState stateJumping;
	private PlayerShootJumpingState stateShootJumping;
	
	private PlayerActionFSM fsmAction;
	private PlayerOrientationFSM fsmOrientation;

	// carries current Asset
	private PlayerAssetState currentState;

	// initialization
	public Player(int id, Factory factory) throws SlickException {
		super(id, EntityType.PLAYER);

		this.factory = factory;

		initCondition();
		initGraphics();
		initPhysics();
		initStates();

		// startup
		setState(stateStanding);
	}

	private void initCondition() {
		condition = new PlayerCondition();
		condition.name = "Player";
		condition.health = 1;
		condition.ammo = 1;
		// condition.shootDirection = PlayerActionState.RunLeft;
		condition.color = StateColor.RED; // player gets default-color: red
		condition.weaponColor = StateColor.RED; // weapon of player get
		// default-color: red
	}

	private void initStates() throws SlickException {

		// statemachines for logic
		this.fsmAction = new PlayerActionFSM();
		this.fsmAction.add(this);

		this.fsmOrientation = new PlayerOrientationFSM();
		this.fsmOrientation.add(this);

		// some states
		stateStanding = new PlayerStandingState(this, factory);
		stateShootStanding = new PlayerShootStandingState(this, factory);
		stateRunning = new PlayerRunningState(this, factory);
		stateShootRunning = new PlayerShootRunningState(this, factory);
		stateJumping = new PlayerJumpingState(this, factory);
		stateShootJumping = new PlayerShootJumpingState(this, factory);
	}

	private void initGraphics() throws SlickException {

		// weapon
		// TODO Weapon Image loading goes here
		// assets.storeAnimation(Assets.WeaponImageId,
		// Assets.BulletAnimImagePath);
		// this.weapon = factory.createAnimationEntity(Assets.WeaponImageId,
		// Assets.WeaponImageId);
		// this.playerGroup.add(this.weapon);
		// Position correction for weapon
		// weapon.getData()[Entity.X] += 20;
		// weapon.getData()[Entity.Y] += 95;
		// weapon.setVisible(true);

		// shader
		if (playerShader == null && Constants.Debug.shadersActive) {
			playerShader = new Shader(Assets.PlayerVertexShaderPath,
					Assets.PlayerPixelShaderPath);
		}

		// make player visible
		setVisible(true);

		// order
		this.setOrder(EntityOrder.Player);
	}

	private void initPhysics() {
		// initialize position, velocity and acceleration
		// X Y OX OY SY SY ROT
		initData(new float[] { 200, 200, 65, 70, 1, 1, 0 }); // pos +
																// center of
																// rotation +
																// scale +
																// rot

		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, GamePlayConstants.gravity, 0, 0, 0, 0, 0 }); // gravity

		// make player collidable with other entities
		CollisionManager.addEntity(this);

		// set bounding box according to idle animation size
		setBounds(new Rectangle(35, 0, 58, 128)); // bounding box
	}

	// network
	@Override
	public void applyNetworkData(final NetworkData networkData) {
		super.applyNetworkData(networkData);

		// HACK we assume we're getting PlayerData
//		this.setState(((PlayerData) networkData).getState());
	}

	@Override
	protected NetworkData _createNetworkData() {
		return new PlayerData(getId());
	}

	@Override
	public NetworkData getNetworkData() {
		PlayerData result = (PlayerData) super.getNetworkData();
		result.state = this.fsmAction.getCurrentState();

		return result;
	}

	// state switching
	// private void enterState(final int state) {
	// this._currentState = state;
	// switch (state) {
	// case PlayerActionState.Idle:
	// this.idleImage.setVisible(true);
	// break;
	// case PlayerActionState.RunLeft:
	// this.getAcc()[Entity.X] = -Constants.GamePlayConstants.playerWalkVel;
	// this.playerGroup.getData()[Entity.SCALE_X] = 1;
	// this.runAnimation.setVisible(true);
	// this.playerCondition.shootDirection = state;
	// break;
	// case PlayerActionState.RunRight:
	// this.getAcc()[Entity.X] = Constants.GamePlayConstants.playerWalkVel;
	// this.playerGroup.getData()[Entity.SCALE_X] = -1;
	// this.runAnimation.setVisible(true);
	// this.playerCondition.shootDirection = state;
	// break;
	// case PlayerActionState.Jump:
	// this.getVel()[Entity.Y] = -Constants.GamePlayConstants.playerJumpVel;
	// this.jump.start();
	// this.jumpAnimation.setVisible(true);
	// break;
	// }
	// }
	//
	// private void leaveState(int state) {
	// switch (state) {
	// case PlayerActionState.Idle:
	// this.idleImage.setVisible(false);
	// break;
	// case PlayerActionState.RunLeft:
	// case PlayerActionState.RunRight:
	// this.runAnimation.setVisible(false);
	// break;
	// case PlayerActionState.Jump:
	// this.jumpAnimation.setVisible(false);
	// }
	// }
	//
	// public void setState(final int state) {
	// if (state != this._currentState) {
	// this.leaveState(this._currentState);
	// this.enterState(state);
	// }
	// }

	public PlayerAssetState getState() {
		return currentState;
	}

//	public void setState(PlayerActionState stateId) {
//		switch (stateId) {
//		case Standing:
//			setState(stateStanding);
//			break;
//		// TODO !!!!
//		}
//	}

	public void setState(PlayerAssetState state) {
		if (currentState != null) {
			currentState.leave();
		}
		currentState = state;
		if (currentState != null) {
			currentState.enter();
		}
	}

	public void applyAction(PlayerActions action) {
		this.fsmAction.apply(action);
	}
	
	// physics
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
				getAcc()[Entity.X] = 0.0f;
			}

			if (InputControl.isRefKeyDown(InputControl.REFWALKLEFT)) {
				fsmOrientation.apply(PlayerActions.Left);
			}

			if (InputControl.isRefKeyDown(InputControl.REFWALKRIGHT)) {
				fsmOrientation.apply(PlayerActions.Right);
			}

			if (InputControl.isRefKeyPressed(InputControl.REFJUMP)) {
				if (this.isOnGround()) {
					// applyAction(PlayerActions.Jump);
				}
			}

			if (InputControl.isRefKeyDown(InputControl.REFFIRE)) {
				
				// TODO tell server to create bullet
//				NetworkComponent.getInstance().sendCommand(
//						new QueryAction(PlayerActions.StartShooting));
				
				applyAction(PlayerActions.StartShooting);
			}

			if (!InputControl.isRefKeyDown(InputControl.REFFIRE)) {

				NetworkComponent.getInstance().sendCommand(
						new QueryAction(PlayerActions.StopShooting));
				applyAction(PlayerActions.StopShooting);
			}

			PlayerCondition state = this.getPlayerCondition();

			// change player color
			if (InputControl.isRefKeyPressed(InputControl.REFCHANGECOLOR)) {
				state.color = state.color << 1;
				if (state.color > StateColor.BLUE) {
					state.color = StateColor.RED;
				}
			}

			// change weapon color
			if (InputControl.isRefKeyPressed(InputControl.REFCHANGEWEAPON)) {
				state.weaponColor = state.weaponColor << 1;
				if (state.weaponColor > StateColor.BLUE) {
					state.weaponColor = StateColor.RED;
				}
			}
		}
		super.handleInput(input);
	}

	@Override
	protected void preRender(Graphics graphicContext) {
		super.preRender(graphicContext);

		if (Constants.Debug.shadersActive) {
			Shader.setActiveShader(playerShader);
			playerShader.setValue("playercolor",
					StateColor.constIntoColor(this.getPlayerCondition().color));
		}
	}

	// render
	@Override
	public void renderImpl(final Graphics g, Image frameBuffer) {
		super.renderImpl(g, frameBuffer);
		currentState.render(g, frameBuffer);
	}

	@Override
	protected void postRender(Graphics graphicContext) {

		// deactivate shader
		if (Constants.Debug.shadersActive) {
			Shader.setActiveShader(null);
		}

		// render player infos
		if (this.condition.name != null) {
			float x = playerHalfWidth
					- graphicContext.getFont().getWidth(condition.name) / 2.0f;
			float y = -graphicContext.getFont().getHeight(condition.name);
			graphicContext.setColor(StateColor.constIntoColor(condition.color)); // colors
																					// the
			// name of
			// player with
			// his color
			graphicContext.drawString(condition.name + " " + getId(), x, y);
			graphicContext.setColor(StateColor
					.constIntoColor(condition.weaponColor));
			graphicContext.drawString("Weapon", x, y + 80);
		}

		super.postRender(graphicContext);
	}

	// update
	@Override
	public void update(final int deltaInMillis) {

		if (this.isActive()) {

			// set Drag
			if (isOnGround()) {
				setDrag(0.005f);
			} else {
				setDrag(0.0000001f);
			}

			super.update(deltaInMillis); // calc physics

			// Handle Player Actions according to physics state after update
			getState().update();
			

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

			// TODO fix PlayerState
			// if (this._currentState == PlayerActionState.Standing
			// && Math.abs(this.getData()[Entity.X]
			// - this.getPrevPos()[Entity.X]) < Constants.EPSILON
			// && Math.abs(this.getData()[Entity.Y]
			// - this.getPrevPos()[Entity.Y]) < Constants.EPSILON) {
			// this.getVel()[Entity.X] = this.getVel()[Entity.Y] = 0.0f;
			// }
		}
	}

	// getters + setters
	public PlayerCondition getPlayerCondition() {
		return condition;
	}

	public void setLevel(final Level level) {
		this.setMap(level.getMap());
	}

	@Override
	public void leavingState(PlayerActionState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enteringState(PlayerActionState state) {
		switch (state) {
		case Left:
			getAcc()[Entity.X] = -Constants.GamePlayConstants.playerWalkSpeed;
			getData()[Entity.SCALE_X] = 1.0f;
			applyAction(PlayerActions.StartRunning);
			break;
		case Right:
			getAcc()[Entity.X] = Constants.GamePlayConstants.playerWalkSpeed;
			getData()[Entity.SCALE_X] = -1.0f;
			applyAction(PlayerActions.StartRunning);
			break;
		case Standing:
			setState(stateStanding);
			break;
		case Running:
			setState(stateRunning);
			break;
		case ShootStanding:
			setState(stateShootStanding);
			break;
		case ShootRunning:
			setState(stateShootRunning);
			break;
		case Jumping:
			setState(stateJumping);
			break;
		case ShootJumping:
			setState(stateShootJumping);
			break;
		default:
			if (Constants.Debug.finiteStateMachineDebug) {
				Log.error("FSM: state " + state + "unhandled");
			}
		}
	}
}
