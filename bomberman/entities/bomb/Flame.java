package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.entities.LayeredEntity;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments;

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param radius độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		_flameSegments = new FlameSegment[ calculatePermitedDistance() ];

		createFlameSegments();
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		//_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * biến last dùng để đánh dấu cho segment cuối cùng
		 */
		boolean last = false;

		// TODO: tạo các segment dưới đây
		int x = (int)_x;
		int y = (int)_y;
		for (int i = 0; i < _flameSegments.length; i++) {
			last = i == _flameSegments.length -1 ? true : false;

			switch (_direction) {
				case 0: y--; break;
				case 1: x++; break;
				case 2: y++; break;
				case 3: x--; break;
			}
			_flameSegments[i] = new FlameSegment(x, y, _direction, last, _board);
		}
	}

	/**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
	private int calculatePermitedDistance() {
		// TODO: thực hiện tính toán độ dài của Flame
		//return 1;
		int radius = 0;
		int x = (int)_x;
		int y = (int)_y;
		while(radius < _radius) {
			if(_direction == 0) y--;
			if(_direction == 1) x++;
			if(_direction == 2) y++;
			if(_direction == 3) x--;

			Entity a = _board.getEntity(x, y, null);

			if(a instanceof Character) ++radius;

			if(a.collide(this) == false)
				break;

			++radius;
		}
		return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
		System.out.println(e);
		if(e instanceof Character || e instanceof Enemy) {
			((Character) e).kill();
		}
		return true;
	}
}
