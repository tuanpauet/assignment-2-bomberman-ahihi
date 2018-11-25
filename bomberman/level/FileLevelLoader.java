package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;



import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader( Board board, String path) throws LoadLevelException {
		super(board, path);
	}
	
	@Override
	public void loadLevel(String path) throws LoadLevelException {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
        try {
            URL absPath = FileLevelLoader.class.getResource("/" + path);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(absPath.openStream()));

            String data = in.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            _level = Integer.parseInt(tokens.nextToken());
            _height = Integer.parseInt(tokens.nextToken());
            _width = Integer.parseInt(tokens.nextToken());

            _lineTiles = new String[_height];

            for(int i = 0; i < _height; ++i) {
                _lineTiles[i] = in.readLine().substring(0, _width);
            }

            in.close();
        } catch (IOException e) {
            //throw new LoadLevelException("Error loading level /levels/Level" + String.valueOf(level) + ".txt", e);
        }
	}

	@Override
    public void createEntities() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                addLevelEntity( _lineTiles[y].charAt(x), x, y );
            }
        }
    }

	public void addLevelEntity(char c,int x,int y) {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall
		/*for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				int pos = x + y * _width;
				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
				_board.addEntity(pos, new Grass(x, y, sprite));
			}
		}



		// thêm Bomber
		int xBomber = 1, yBomber = 1;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

		// thêm Enemy
		int xE = 2, yE = 1;
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

		// thêm Brick
		int xB = 3, yB = 1;
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
					new Grass(xB, yB, Sprite.grass),
					new Brick(xB, yB, Sprite.brick)
				)
		);

		// thêm Item kèm Brick che phủ ở trên
		int xI = 1, yI = 2;
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
					new Grass(xI ,yI, Sprite.grass),
					//new SpeedItem(xI, yI, Sprite.powerup_flames),
					new Brick(xI, yI, Sprite.brick)
				)
		);
	}
    */
        int pos = x + y * getWidth();
                System.out.printf ("x = %d and y = %d and pos = %d\n", x, y, pos);
        switch(c) {
            case '#':
                _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                break;
            case 'b':
                LayeredEntity layer = new LayeredEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Brick(x ,y, Sprite.brick));

                if(_board.isPowerupUsed(x, y, _level) == false) {
                    layer.addBeforeTop(new BombItem(x, y, _level, Sprite.powerup_bombs));
                }

                _board.addEntity(pos, layer);
                break;
            case 's':
                layer = new LayeredEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Brick(x ,y, Sprite.brick));

                if(_board.isPowerupUsed(x, y, _level) == false) {
                    layer.addBeforeTop(new SpeedItem(x, y, _level, Sprite.powerup_speed));
                }

                _board.addEntity(pos, layer);
                break;
            case 'f':
                layer = new LayeredEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Brick(x ,y, Sprite.brick));

                if(_board.isPowerupUsed(x, y, _level) == false) {
                    layer.addBeforeTop(new FlameItem(x, y, _level, Sprite.powerup_flames));
                }

                _board.addEntity(pos, layer);
                break;
            case '*':
                _board.addEntity(pos, new LayeredEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Brick(x ,y, Sprite.brick)) );
                break;
            case 'x':
                _board.addEntity(pos, new LayeredEntity(x, y,
                        new Grass(x ,y, Sprite.grass),
                        new Portal(x ,y, _board, Sprite.portal),
                        new Brick(x ,y, Sprite.brick)) );
                break;
            case ' ':
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            case 'p':
                _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                Screen.setOffset(0, 0);

                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            //Enemies
            case '1':
                _board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '2':
                _board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '3':
                _board.addCharacter( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '4':
                _board.addCharacter( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            case '5':
                _board.addCharacter( new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
            default:
                _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                break;
        }
    }

}
