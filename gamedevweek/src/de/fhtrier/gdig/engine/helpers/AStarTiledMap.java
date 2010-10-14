package de.fhtrier.gdig.engine.helpers;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;

/**
 * 
 * Tiledmap that can be used with slick astar-pathfinding 
 * 
 * @author ttrocha
 *
 */
public class AStarTiledMap extends TiledMap implements TileBasedMap {

	private Mover mover;
	private static AStarPathFinder finder;
	private boolean[][] visited;

	public AStarTiledMap(String ref) throws SlickException {
		super(ref);
	}

	public int getHeightInTiles() {
		return height;
	}

	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return width;
	}

	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;

	}

	/**
	 * Clear the array marking which tiles have been visted by the path finder.
	 */
	public void clearVisited() {
		if (visited == null)
			visited = new boolean[getWidthInTiles()][getHeightInTiles()];

		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}

	public void initAStarPathFinder() {
		finder = new AStarPathFinder(this, 1500, true);
		mover = new Mover() {
		};
	}

	public Path calculatePath(int fromX, int fromY, int toX, int toY) {
		if (finder == null) {
			initAStarPathFinder();
		}
		clearVisited();
		return finder.findPath(mover, fromX, fromY, toX, toY);
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 1;
	}

	@Override
	public int getTileId(int x, int y, int layerIndex) {
		if (x < 0 || x > getWidthInTiles())
			return 0;
		if (y < 0 || y > getHeightInTiles())
			return 0;
		return super.getTileId(x, y, layerIndex);
	}

	@Override
	public boolean blocked(PathFindingContext arg0, int x, int y) {
		int id = this.getTileId(x, y, Constants.Level.collisionLayer);
		return id > 0;
	}

}
