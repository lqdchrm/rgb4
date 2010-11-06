package de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ConfigurableEmitter.ColorRecord;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryAction;
import de.fhtrier.gdig.demos.jumpnrun.common.events.Event;
import de.fhtrier.gdig.demos.jumpnrun.common.events.EventManager;
import de.fhtrier.gdig.demos.jumpnrun.common.events.PlayerDiedEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.events.WonGameEvent;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.LogicPoint;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.StateColor;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.Team;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.AbstractAssetState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.DyingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.FallingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.JumpingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.LandingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.RevivingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.RunningState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.ShootFallingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.ShootJumpingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.ShootRunningState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.ShootStandingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.StandingState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActionState;
import de.fhtrier.gdig.demos.jumpnrun.common.gamelogic.player.states.identifiers.PlayerActions;
import de.fhtrier.gdig.demos.jumpnrun.common.input.GameInputController;
import de.fhtrier.gdig.demos.jumpnrun.common.network.NetworkData;
import de.fhtrier.gdig.demos.jumpnrun.common.network.PlayerData;
import de.fhtrier.gdig.demos.jumpnrun.common.physics.entities.LevelCollidableEntity;
import de.fhtrier.gdig.demos.jumpnrun.common.states.PlayingState;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.GamePlayConstants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityOrder;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.EntityType;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameInputCommands;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.DoPlaySound;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendKill;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.SendWon;
import de.fhtrier.gdig.engine.gamelogic.Entity;
import de.fhtrier.gdig.engine.graphics.entities.ParticleEntity;
import de.fhtrier.gdig.engine.graphics.shader.Shader;
import de.fhtrier.gdig.engine.helpers.IFiniteStateMachineListener;
import de.fhtrier.gdig.engine.input.InputController;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.management.Factory;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.physics.CollisionManager;
import de.fhtrier.gdig.engine.sound.SoundManager;

public class Player extends LevelCollidableEntity implements
		IFiniteStateMachineListener<PlayerActionState> {

	// Shader stuff
	private static Image playerGlow = null;
	private static Image weaponGlow = null;
	private static Shader colorGlowShader;

	// helpers
	private Factory factory;
	private float playerHalfWidth = 48;

	// "useful" members
	private PlayerCondition condition;

	// some states for gfx and a statemachine for logic
	private StandingState stateStanding;
	private ShootStandingState stateShootStanding;
	private RunningState stateRunning;
	private ShootRunningState stateShootRunning;
	private JumpingState stateJumping;
	private ShootJumpingState stateShootJumping;
	private LandingState stateLanding;
	private FallingState stateFalling;
	private ShootFallingState stateShootFalling;
	private DyingState stateDying;
	private RevivingState stateReviving;

	private HashMap<Integer, AbstractAssetState> id2PlayerState;

	private PlayerActionFSM fsmAction;
	private PlayerOrientationFSM fsmOrientation;

	// carries current Asset
	private AbstractAssetState currentPlayerAsset;

	// particles
	private ParticleEntity weaponParticles;

	// we have our own asset manager -> no sharing of animations
	private AssetMgr assets;

	// color
	int playerColor;
	int weaponColor;
	
	// delays
	float fireDelay = 0f;
	float colorChangeDelayPlayer = 0f;
	float colorChangeDelayWeapon = 0f;
	
	// initialization
	public Player(int id, Factory factory) throws SlickException {
		super(id, EntityType.PLAYER);

		this.factory = factory;
		assets = new AssetMgr();

		initGraphics();
		initPhysics();
		initCondition();
		initStates();

		// startup
		setState(stateStanding);
	}

	private void initCondition() {
		condition = new PlayerCondition(this.getId(), "XXX", 1,
				Constants.GamePlayConstants.initialPlayerHealth, 1.0f, Constants.GamePlayConstants.defaultShotDamage);
		setPlayerColor(StateColor.RED); // player gets default-color: red
		setWeaponColor(StateColor.RED); // weapon of player get default-color:
										// red
	}

	private void initStates() throws SlickException {

		// lookup
		this.id2PlayerState = new HashMap<Integer, AbstractAssetState>();

		// statemachines for logic
		this.fsmAction = new PlayerActionFSM();
		this.fsmAction.add(this);

		this.fsmOrientation = new PlayerOrientationFSM();
		this.fsmOrientation.add(this);

		// some states
		stateStanding = new StandingState(PlayerActionState.Standing.ordinal(),
				this, factory);
		stateShootStanding = new ShootStandingState(
				PlayerActionState.ShootStanding.ordinal(), this, factory);
		stateRunning = new RunningState(PlayerActionState.Running.ordinal(),
				this, factory);
		stateShootRunning = new ShootRunningState(
				PlayerActionState.ShootRunning.ordinal(), this, factory);
		stateJumping = new JumpingState(PlayerActionState.Jumping.ordinal(),
				this, factory);
		stateShootJumping = new ShootJumpingState(
				PlayerActionState.ShootJumping.ordinal(), this, factory);
		stateLanding = new LandingState(PlayerActionState.Landing.ordinal(),
				this, factory);
		stateFalling = new FallingState(PlayerActionState.Falling.ordinal(),
				this, factory);
		stateShootFalling = new ShootFallingState(
				PlayerActionState.ShootFalling.ordinal(), this, factory);
		stateDying = new DyingState(PlayerActionState.Dying.ordinal(), this,
				factory);
		stateReviving = new RevivingState(PlayerActionState.Reviving.ordinal(),
				this, factory);

		id2PlayerState.put(stateStanding.getStateId(), stateStanding);
		id2PlayerState.put(stateShootStanding.getStateId(), stateShootStanding);
		id2PlayerState.put(stateRunning.getStateId(), stateRunning);
		id2PlayerState.put(stateShootRunning.getStateId(), stateShootRunning);
		id2PlayerState.put(stateJumping.getStateId(), stateJumping);
		id2PlayerState.put(stateShootJumping.getStateId(), stateShootJumping);
		id2PlayerState.put(stateLanding.getStateId(), stateLanding);
		id2PlayerState.put(stateFalling.getStateId(), stateFalling);
		id2PlayerState.put(stateShootFalling.getStateId(), stateShootFalling);
		id2PlayerState.put(stateDying.getStateId(), stateDying);
		id2PlayerState.put(stateReviving.getStateId(), stateReviving);

	}

	private void initGraphics() throws SlickException {

		// shader
		if (playerGlow == null) {
			if (Constants.Debug.shadersActive) {
				colorGlowShader = new Shader(
						assets.getPathRelativeToAssetPath(Assets.Player.VertexShaderPath),
						assets.getPathRelativeToAssetPath(Assets.Player.PixelShaderPath));
			}

			playerGlow = new Image(
					assets.getPathRelativeToAssetPath(Assets.Player.GlowImagePath));
			weaponGlow = new Image(
					assets.getPathRelativeToAssetPath(Assets.Weapon.GlowImagePath));
		}

		// weaponparticles
		assets.storeParticleSystem(this.getId(),
				Assets.Weapon.ParticleEffectImgPath,
				Assets.Weapon.ParticleEffectCfgPath);
		weaponParticles = factory.createParticleEntity(0, this.getId(), assets);

		// playerGroup.add(weaponParticles);

		// Position correction for particleEffects
		// TODO: take weapon cords
		weaponParticles.getData()[Entity.X] = 122;
		weaponParticles.getData()[Entity.Y] = 165;

		weaponParticles.setVisible(true);

		// make entities visible
		setVisible(true);

		// order
		this.setOrder(EntityOrder.Player);
	}

	private void initPhysics() {
		// initialize position, velocity and acceleration
		// X Y OX OY SY SY ROT

		// SpawnPoint randomSpawnPoint = level.getRandomSpawnPoint(1);

		initData(new float[] { 200, 200, 65, 70, 1, 1, 0 }); // pos +
																// center of
																// rotation +
																// scale +
																// rot

		setVel(new float[] { 0, 0, 0, 0, 0, 0, 0 }); // no speed
		setAcc(new float[] { 0, GamePlayConstants.gravity, 0, 0, 0, 0, 0 }); // gravity

		// make player collidable with other entities
		CollisionManager.addEntity(this);

		// TODO set bounding box according to idle animation size
		setBounds(new Rectangle(35, 16, 58, 108)); // bounding box
	}

	// network
	@Override
	public void applyNetworkData(final NetworkData networkData) {
		super.applyNetworkData(networkData);

		if (networkData instanceof PlayerData) {

			PlayerData pd = (PlayerData) networkData;

			this.currentPlayerAsset = id2PlayerState.get(pd
					.getAnimationEntityId());
			setPlayerColor(pd.getColor());
			setWeaponColor(pd.getWeaponColor());

		} else {
			throw new IllegalArgumentException(
					"wrong network data type received");
		}

	}

	@Override
	protected NetworkData _createNetworkData() {
		return new PlayerData(getId());
	}

	@Override
	public NetworkData getNetworkData() {
		PlayerData result = (PlayerData) super.getNetworkData();
		result.setAnimationEntityId(this.currentPlayerAsset.getStateId());
		result.setColor(getPlayerColor());
		result.setWeaponColor(getWeaponColor());

		return result;
	}

	public AbstractAssetState getCurrentPlayerAsset() {
		return currentPlayerAsset;
	}

	public void setState(AbstractAssetState state) {
		if (currentPlayerAsset != null) {
			currentPlayerAsset.leave();
		}
		currentPlayerAsset = state;
		if (currentPlayerAsset != null) {
			currentPlayerAsset.enter();
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
	public void handleInput(final InputController<?> _input) {
		super.handleInput(_input);
		
		// TODO react on player state, not on health
		// TODO stop player motion in else case
		if (this.isActive() && this.condition.getHealth() > Constants.EPSILON) {
			
			GameInputController input = (GameInputController)_input;
			
			if (!input.isKeyDown(GameInputCommands.WALKLEFT)
					&& !input.isKeyDown(GameInputCommands.WALKRIGHT)
					&& !input.isKeyDown(GameInputCommands.JUMP)) {
				getAcc()[Entity.X] = 0.0f;
			}

			if (input.isKeyDown(GameInputCommands.WALKLEFT)) {
				fsmOrientation.apply(PlayerActions.Left);
			}

			if (input.isKeyDown(GameInputCommands.WALKRIGHT)) {
				fsmOrientation.apply(PlayerActions.Right);
			}

			if (input.isKeyPressed(GameInputCommands.JUMP)) {
				if (this.isOnGround()) {
					getVel()[Entity.Y] = -Constants.GamePlayConstants.playerJumpSpeed;
					applyAction(PlayerActions.Jump);
					SoundManager.playSound(Assets.Sounds.PlayerJumpSoundId, 1f,
							0.5f);
				}
			}

			if (input.isKeyPressed(GameInputCommands.SHOOT)) {
				if(fireDelay == Constants.GamePlayConstants.shotCooldown) {
					NetworkComponent.getInstance().sendCommand(
							new QueryAction(PlayerNetworkAction.SHOOT));
					applyAction(PlayerActions.StartShooting);
					fireDelay = 0;
				}
			}

			if (input.isKeyPressed(GameInputCommands.ROCKET)) {

				if (fireDelay == Constants.GamePlayConstants.shotCooldown) {
					NetworkComponent.getInstance().sendCommand(
						new QueryAction(PlayerNetworkAction.SHOOT_ROCKET));
					applyAction(PlayerActions.StartShooting);
					fireDelay = 0;
				}
			}

			// change player color
			if (input.isKeyPressed(GameInputCommands.CHANGECOLOR)) {
				nextColor();
				SoundManager.playSound(Assets.Sounds.PlayerChangeColorSoundID,
						1f, 0.2f);
				colorChangeDelayPlayer = 0;
			}

			// change weapon color
			if (input.isKeyPressed(GameInputCommands.CHANGEWEAPONCOLOR)) {
				nextWeaponColor();
				SoundManager.playSound(Assets.Sounds.WeaponChangeColorSoundID,
						1f, 0.2f);
				colorChangeDelayPlayer = 0;
			}

			// Player Phrases
			if (input.isKeyPressed(GameInputCommands.PHRASE1)) {
				NetworkComponent.getInstance().sendCommand(
						new DoPlaySound(Assets.Sounds.PlayerPhrase1SoundID));
				SoundManager.playSound(Assets.Sounds.PlayerPhrase1SoundID, 1f,
						1f);
			}

			if (input.isKeyPressed(GameInputCommands.PHRASE2)) {
				NetworkComponent.getInstance().sendCommand(
						new DoPlaySound(Assets.Sounds.PlayerPhrase2SoundID));
				SoundManager.playSound(Assets.Sounds.PlayerPhrase2SoundID, 1f,
						1f);
			}

			if (input.isKeyPressed(GameInputCommands.PHRASE3)) {
				NetworkComponent.getInstance().sendCommand(
						new DoPlaySound(Assets.Sounds.PlayerPhrase4SoundID));
				SoundManager.playSound(Assets.Sounds.PlayerPhrase3SoundID, 1f,
						1f);
			}

			if (input.isKeyPressed(GameInputCommands.PHRASE4)) {
				NetworkComponent.getInstance().sendCommand(
						new DoPlaySound(Assets.Sounds.PlayerPhrase4SoundID));
				SoundManager.playSound(Assets.Sounds.PlayerPhrase4SoundID, 1f,
						0.6f);
			}

		} else {
			getAcc()[Entity.X] = 0.0f;
		}
	}

	public void nextColor() {

		int tmp = getPlayerColor() << 1;
		if (tmp > StateColor.BLUE) {
			tmp = StateColor.RED;
		}

		setPlayerColor(tmp);
	}

	public void nextWeaponColor() {

		int tmp = getWeaponColor() << 1;
		if (tmp > StateColor.BLUE) {
			tmp = StateColor.RED;
		}

		setWeaponColor(tmp);
	}

	private void setParticleColor(int color) {
		ParticleSystem particleSystem = weaponParticles.getAssetMgr()
				.getParticleSystem(this.getId());
		ConfigurableEmitter emitter = (ConfigurableEmitter) particleSystem
				.getEmitter(0);
		ColorRecord cr = (ColorRecord) emitter.colors.get(2);

		cr.col = StateColor.constIntoColor(getWeaponColor());
	}

	public void die() {
		this.applyAction(PlayerActions.Die);
	}

	public void respawn() {
		LogicPoint randomSpawnPoint = level.getRandomSpawnPoint(getPlayerCondition().getTeamId()+1);
		getData()[Entity.X] = randomSpawnPoint.x;
		getData()[Entity.Y] = randomSpawnPoint.y;

		NetworkComponent.getInstance().sendCommand(
				new QueryRespawn(this.getId()));
	}

	@Override
	protected void preRender(Graphics graphicContext) {
		super.preRender(graphicContext);

		Color playerCol = StateColor.constIntoColor(getPlayerColor());
		Color weaponCol = StateColor.constIntoColor(getWeaponColor());

		if (Constants.Debug.shadersActive) {
			Shader.pushShader(colorGlowShader);
		}

		graphicContext.setColor(Color.white);
		Shader.activateAdditiveBlending();
		float weaponGlowSize = 0.6f + this.getPlayerCondition().getAmmo() * 0.4f;
		float glowSize = 0.1f + this.getPlayerCondition().getHealth() * 0.9f;
		if(this.getPlayerCondition().getHealth() <= Constants.EPSILON) {
			weaponGlowSize = 0.0f;
			glowSize = 0.0f;
		}

		// TODO find active Animation-Asset and setTintColor(playerCol)

		float weaponX = this.getData(CENTER_X);
		float weaponY = this.getData(CENTER_Y) - weaponGlow.getHeight()
				* weaponGlowSize / 2 + 40;

		float weaponBrightness = StateColor
				.constIntoBrightness(getWeaponColor());

		float weaponLoad = fireDelay / Constants.GamePlayConstants.shotCooldown;
		
		if (Constants.Debug.shadersActive) {
			weaponCol.a = Constants.GamePlayConstants.weaponGlowFalloff
					* weaponBrightness * weaponLoad;
			colorGlowShader.setValue("playercolor", weaponCol);
		}

		graphicContext.drawImage(weaponGlow, weaponX, weaponY, weaponX
				- weaponGlow.getWidth(), weaponY + weaponGlow.getHeight()
				* weaponGlowSize, 0, 0, weaponGlow.getWidth(),
				weaponGlow.getHeight(), weaponCol);

		float brightness = StateColor.constIntoBrightness(getPlayerColor());

		if (Constants.Debug.shadersActive) {
			playerCol.a = Constants.GamePlayConstants.playerGlowFalloff
					* brightness;
			colorGlowShader.setValue("playercolor", playerCol);
		}

		graphicContext.drawImage(playerGlow, this.getData(CENTER_X)
				- playerGlow.getWidth() * glowSize / 2, this.getData(CENTER_Y)
				- playerGlow.getHeight() * glowSize / 2, this.getData(CENTER_X)
				+ playerGlow.getWidth() * glowSize / 2, this.getData(CENTER_Y)
				+ playerGlow.getHeight() * glowSize / 2, 0, 0,
				playerGlow.getWidth(), playerGlow.getHeight(), playerCol);

		if (Constants.Debug.shadersActive) {

			playerCol = new Color(playerCol.r + brightness, playerCol.g
					+ brightness, playerCol.b + brightness);
			playerCol.a = 1f;
			colorGlowShader.setValue("playercolor", playerCol);
		}

		Shader.activateDefaultBlending();
	}

	// render
	@Override
	public void renderImpl(final Graphics g, Image frameBuffer) {
		currentPlayerAsset.render(g, frameBuffer);

		super.renderImpl(g, frameBuffer);
	}

	@Override
	protected void postRender(Graphics graphicContext) {

		// deactivate shader
		if (Constants.Debug.shadersActive) {
			Shader.popShader();
		}

		super.postRender(graphicContext);

		// render player name
		if (this.condition.getName() != null) {
			float x = getData()[Entity.X] + playerHalfWidth
					- graphicContext.getFont().getWidth(condition.getName())
					/ 2.0f;

			float y = getData()[Entity.Y]
					- (graphicContext.getFont().getHeight(condition.getName()) * 3);

			// colors the name of player with his color

			if (!Constants.Debug.shadersActive) {
				graphicContext.setColor(StateColor
						.constIntoColor(getPlayerColor()));
			} else {
				graphicContext
						.setColor(Constants.GamePlayConstants.defaultPlayerTextColor);
			}
			graphicContext.drawString(
					condition.getName() + "\n" + " kills: "
							+ condition.getKills() + "\n" + "deaths: "
							+ condition.getDeaths(), x, y);
		}

	}

	// update
	@Override
	public void update(final int deltaInMillis) {

		if (this.isActive()) {
			fireDelay += deltaInMillis;
			if(fireDelay > Constants.GamePlayConstants.shotCooldown)
				fireDelay = Constants.GamePlayConstants.shotCooldown;
			colorChangeDelayPlayer += deltaInMillis;
			if(colorChangeDelayPlayer > Constants.GamePlayConstants.colorChangeCooldownPlayer)
				colorChangeDelayPlayer = Constants.GamePlayConstants.colorChangeCooldownPlayer;
			colorChangeDelayWeapon += deltaInMillis;
			if(colorChangeDelayWeapon > Constants.GamePlayConstants.colorChangeCooldownWeapon)
				colorChangeDelayWeapon = Constants.GamePlayConstants.colorChangeCooldownWeapon;
			
			// set Drag
			if (isOnGround()) {
				getDrag()[Entity.X] = Constants.GamePlayConstants.playerGroundDrag;
				getDrag()[Entity.Y] = 0.0f;
			} else {
				getDrag()[Entity.X] = Constants.GamePlayConstants.playerAirDrag;
				getDrag()[Entity.Y] = Constants.GamePlayConstants.playerAirDrag;
			}

			super.update(deltaInMillis); // calc physics

			// Handle Player Actions according to physics state after update
			getCurrentPlayerAsset().update();

			if (this.getVel()[Entity.X] > Constants.GamePlayConstants.playerMaxSpeed) {
				this.getVel()[Entity.X] = Constants.GamePlayConstants.playerMaxSpeed;
			}

			if (this.getVel()[Entity.Y] > Constants.GamePlayConstants.playerMaxJumpSpeed) {
				this.getVel()[Entity.Y] = Constants.GamePlayConstants.playerMaxJumpSpeed;
			}

			if (this.getVel()[Entity.X] < -Constants.GamePlayConstants.playerMaxSpeed) {
				this.getVel()[Entity.X] = -Constants.GamePlayConstants.playerMaxSpeed;
			}

			if (this.getVel()[Entity.Y] < -Constants.GamePlayConstants.playerMaxJumpSpeed) {
				this.getVel()[Entity.Y] = -Constants.GamePlayConstants.playerMaxJumpSpeed;
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

	/**
	 * Does Damage to the Player. If Color equels the Color of the Player he
	 * gain Life instad of reducing it.
	 * 
	 * @param damageColor
	 *            the Color of the Damage
	 * @param damage
	 *            the Damage
	 * @return true if Player Died, else false.
	 */
	public boolean doDamage(int color, float damage, Player killer) {
		
		boolean died = false;
		
		// don't handle dead players
		if (getPlayerCondition().getHealth() <= 0)
			return false;
		
		// don't hurt yourself
		if (this == killer)
			return false;
		
		// check for friendly fire/different teams
		if (Constants.GamePlayConstants.friendlyFire == true ||
			killer == null ||
			killer.getPlayerCondition().getTeamId() != this.getPlayerCondition().getTeamId()) {
			
			// check color --> different damages / same heals
			if (this.getPlayerColor() != color) {
				
				// do damage
				this.getPlayerCondition().setHealth(
						getPlayerCondition().getHealth() - damage);

				// check if died
				if (this.getPlayerCondition().getHealth() <= Constants.EPSILON) {
					
					// tell everyone
					NetworkComponent.getInstance().sendCommand(
							new SendKill(this.getId(), killer != null ? killer
									.getId() : -1));

					// enqueue event, that calculates statistics
					Event dieEvent = new PlayerDiedEvent(this, killer);
					dieEvent.update();
					died = true;
				}

				// handle game modes
				switch(PlayingState.gameType) {
				
					// Deatch Match
					case Constants.GameTypes.deathMatch:
						
						if (killer != null &&
							killer.getPlayerCondition().getKills() >= Constants.GamePlayConstants.winningKills_Deathmatch) {
						
							NetworkComponent.getInstance().sendCommand(new SendWon(killer.getId(),
										SendWon.winnerType_Player));

							Event wonEvent = new WonGameEvent(killer);
							EventManager.addEvent(wonEvent);
						}
					break;
				
					// Team Death Match
					case Constants.GameTypes.teamDeathMatch:
						
						if (Team.team1.getKills() >= Constants.GamePlayConstants.winningKills_TeamDeathmatch) {
						
							NetworkComponent.getInstance().sendCommand(new SendWon(Team.team1.id,
										SendWon.winnerType_Team));

							Event wonEvent = new WonGameEvent(Team.team1);
							EventManager.addEvent(wonEvent);
						} else if (Team.team2.getKills() >= Constants.GamePlayConstants.winningKills_TeamDeathmatch) {
						
							NetworkComponent.getInstance().sendCommand(
								new SendWon(Team.team2.id, SendWon.winnerType_Team));

							Event wonEvent = new WonGameEvent(Team.team1);
							EventManager.addEvent(wonEvent);
						}
					break;
				}
			} else { // same color

				// heal player
				this.getPlayerCondition().setHealth(
						getPlayerCondition().getHealth() +
						damage * Constants.GamePlayConstants.healingFactor);
				
				// cap health at maxHealth
				if (this.getPlayerCondition().getHealth() > Constants.GamePlayConstants.maxPlayerHealth)
					this.getPlayerCondition().setHealth(
							Constants.GamePlayConstants.maxPlayerHealth);
			}

		}
		
		return died;
	}

	public void setPlayerCondition(PlayerCondition playerCondition) {
		this.condition = playerCondition;

	}

	public int getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}

	public int getWeaponColor() {
		return weaponColor;
	}

	public void setWeaponColor(int weaponColor) {
		this.weaponColor = weaponColor;
		setParticleColor(this.weaponColor);
	}

	public static Shader getColorGlowShader() {
		return colorGlowShader;
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
		case Landing:
			setState(stateLanding);
			break;
		case Falling:
			setState(stateFalling);
			break;
		case ShootFalling:
			setState(stateShootFalling);
			break;
		case Dying:
			setState(stateDying);
			break;
		case Reviving:
			setState(stateReviving);
			break;
		default:
			if (Constants.Debug.finiteStateMachineDebug) {
				Log.error("FSM: state " + state + "unhandled");
			}
		}
	}

	public AssetMgr getAssetMgr() {
		return assets;
	}

	public ParticleEntity getWeaponParticleEntity() {
		return this.weaponParticles;
	}
	
	@Override
	public String toString() {
		return "Player Entity: " + this.getId() + " / " + this.getPlayerCondition().getName() + " / ";
	}
	
}
