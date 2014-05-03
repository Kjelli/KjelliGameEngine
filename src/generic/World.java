package generic;

import generic.gameobjects.GameObject;

import java.util.ArrayList;

public class World {
	private ArrayList<GameObject> objects;

	public World() {
		objects = new ArrayList<GameObject>();
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void add(GameObject object) {
		objects.add(object);
	}

	public void remove(GameObject object) {
		objects.remove(object);
	}

	public void render() {
		for (GameObject object : objects) {
			if (object.isVisible())
				object.draw();
		}

	}

	public void update() {
		Physics.clear();
		Physics.addObjects(getCollidables());

		for (GameObject gameObject : objects) {
			gameObject.update();
		}
	}

	private ArrayList<Collidable> getCollidables() {
		ArrayList<Collidable> collidables = new ArrayList<>();
		for (GameObject e : objects) {
			if (e instanceof Collidable) {
				collidables.add((Collidable) e);
			}
		}
		return collidables;
	}
}
