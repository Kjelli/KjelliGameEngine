package no.kjelli.julekalender.luke20;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;

import org.newdawn.slick.Color;

public class ChristmasDinner {
	Person[] people;
	Person bottleHolder;
	public boolean allDrunk = false;

	public static void main(String[] args) {
		long time = System.nanoTime();
		int n = 1500;
		int answer = (int) (2 * (n - Math.pow(2, Math.ceil(binlog(n)))) + 1);
		float now = (float) (System.nanoTime() - time) / 1_000_000;
		System.out.println(answer + " took " + now + "ms");
	}

	public static int binlog(int bits) // returns 0 for bits=0
	{
		int log = 0;
		if ((bits & 0xffff0000) != 0) {
			bits >>>= 16;
			log = 16;
		}
		if (bits >= 256) {
			bits >>>= 8;
			log += 8;
		}
		if (bits >= 16) {
			bits >>>= 4;
			log += 4;
		}
		if (bits >= 4) {
			bits >>>= 2;
			log += 2;
		}
		return log + (bits >>> 1);
	}

	public ChristmasDinner(int count) {
		people = new Person[count];
		for (int i = 0; i < count; i++) {
			people[i] = new Person(i);
		}
		bottleHolder = people[0];
	}

	public void draw() {
		for (Person p : people) {
			p.draw();
		}
	}

	public void allDrunk() {
		System.out.println("All people are drunk except the man at seat "
				+ bottleHolder.pos);
		allDrunk = true;
	}

	class Person {
		boolean drunk = false;
		int pos;

		public Person(int pos) {
			this.pos = pos;
		}

		public void draw() {
			Draw.rect(
					Screen.getCenterX()
							+ (float) JuleKalender.getGameWidth()
							/ 2
							* (float) Math.cos(2 * Math.PI
									* ((float) pos / people.length)),
					Screen.getCenterY()
							+ 250f
							* (float) Math.sin(2 * Math.PI
									* ((float) pos / people.length)), 1f, 0.5f,
					0.5f, 2f * (float) Math.PI * ((float) pos / people.length),
					drunk ? Color.red : (bottleHolder == this ? Color.blue
							: Color.green), false);
		}

		public void pass() {
			int next = pos;
			int itCount = 0;
			Person p = null;
			do {
				p = people[next = (next + 1) % people.length];
				if (next == people.length - 1) {
					itCount++;
					if (itCount == 2) {
						allDrunk();
						break;
					}
				}
			} while (p.drunk || p == this);
			bottleHolder = people[next];
		}

		public void drink() {
			this.drunk = true;
			pass();
		}
	}
}
