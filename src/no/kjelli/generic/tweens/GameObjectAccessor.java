package no.kjelli.generic.tweens;

import no.kjelli.generic.gameobjects.GameObject;
import aurelienribon.tweenengine.TweenAccessor;

public class GameObjectAccessor implements TweenAccessor<GameObject> {

	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;
	public static final int POSITION_XY = 3;

	public static final int SIZE_W = 4;
	public static final int SIZE_H = 5;
	public static final int SIZE_WH = 6;

	public static final int ROTATION = 7;

	@Override
	public int getValues(GameObject go, int type, float[] returnValues) {
		switch (type) {
		case POSITION_X:
			returnValues[0] = go.getX();
			return 1;
		case POSITION_Y:
			returnValues[0] = go.getY();
			return 1;
		case POSITION_XY:
			returnValues[0] = go.getX();
			returnValues[1] = go.getY();
			return 2;

			/*
		 * 
		 */

		case SIZE_W:
			returnValues[0] = go.getWidth();
			return 1;
		case SIZE_H:
			returnValues[0] = go.getHeight();
			return 1;
		case SIZE_WH:
			returnValues[0] = go.getWidth();
			returnValues[1] = go.getHeight();
			return 2;

			/*
		 * 
		 */

		case ROTATION:
			returnValues[0] = go.getRotation();
			return 1;

		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(GameObject go, int type, float[] newValues) {
		switch (type) {

		case POSITION_X:
			go.setX(newValues[0]);
			break;
		case POSITION_Y:
			go.setY(newValues[0]);
			break;
		case POSITION_XY:
			go.setX(newValues[0]);
			go.setY(newValues[1]);
			break;
		/*
		 * 
		 * 
		 */

		case SIZE_W:
			go.setWidth(newValues[0]);
			break;
		case SIZE_H:
			go.setHeight(newValues[0]);
			break;
		case SIZE_WH:
			go.setWidth(newValues[0]);
			go.setHeight(newValues[1]);
			break;

		/*
		 * 
		 */

		case ROTATION:
			go.setRotation(newValues[0]);
			break;

		default:
			assert false;
			break;
		}
	}

}
