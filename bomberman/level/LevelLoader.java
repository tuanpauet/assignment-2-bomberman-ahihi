package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width = 20, _height = 20; // default values just for testing
	protected int _level;
	protected Board _board;
	protected String[] _lineTiles;

	public LevelLoader(Board board, String path) throws LoadLevelException {
		_board = board;
		loadLevel(path);
	}
	protected static String[] codes = { //TODO: change this code system to actualy load the code from each level.txt
			"dnibpb5uqy",
			"cuq0vaxstb",
			"38y418wriq",
			"34h8k0chcs",
			"9qztxh6l4s",
	};

	public int validCode(String str) {
		for (int i = 0; i < codes.length; i++) {
			if (codes[i].equals(str)) {
				return i;
			}
		}
		return -1;
	}

	public abstract void loadLevel(String path) throws LoadLevelException;

	public String getActualCode() {
		return codes[_level -1];
	}

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}
