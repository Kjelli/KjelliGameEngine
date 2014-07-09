package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.towerdefense.TowerDefense;

import org.newdawn.slick.Color;

public class Draw {
	public static final Color DEFAULT_COLOR = new Color(1f, 1f, 1f, 1f);

	public static void fillRect(float x, float y, float width, float height) {
		fillRect(x, y, width, height, 0, DEFAULT_COLOR);
	}

	public static void fillRect(float x, float y, float width, float height,
			float rot) {
		fillRect(x, y, width, height, 0, DEFAULT_COLOR);
	}

	public static void fillRect(float x, float y, float width, float height,
			Color color) {
		fillRect(x, y, width, height, 0, color);
	}

	public static void fillRect(float x, float y, float width, float height,
			float rot, Color color) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
			glRotatef(rot, 0, 0, 0);

			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(0, height);
				glVertex2f(width, height);
				glVertex2f(width, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void sprite(Drawable drawable) {
		sprite(drawable, 0, 0, 1.0f, 1, 1.0f, 1.0f, false);
	}

	public static void sprite(Drawable drawable, float xOffset, float yOffset) {
		sprite(drawable, xOffset, yOffset, 1.0f, 1, 1.0f, 1.0f, false);
	}

	public static void sprite(Drawable drawable, float alpha) {
		sprite(drawable, 0, 0, alpha, 1, 1.0f, 1.0f, false);
	}

	public static void sprite(Drawable drawable, boolean followScreen) {
		sprite(drawable, 0, 0, 1.0f, 1, 1.0f, 1.0f, followScreen);
	}

	public static void sprite(Drawable drawable, float alpha,
			boolean followScreen) {
		sprite(drawable, 0, 0, alpha, 1, 1.0f, 1.0f, followScreen);
	}

	public static void sprite(Drawable drawable, float xOffset, float yOffset,
			float alpha, float rot, float xScale, float yScale,
			boolean followScreen) {

		Sprite sprite = drawable.getSprite();
		if (sprite == null) {
			System.err.println("No working sprite! [" + drawable + "]");
			return;
		}

		float x = drawable.getX() + xOffset;
		float y = drawable.getY() + yOffset;
		if (followScreen) {
			x += Screen.getX();
			y += Screen.getY();
		}

		glPushMatrix();
		{
			glColor4f(1.0f, 1.0f, 1.0f, alpha);

			glTranslatef(x - Screen.getX() + drawable.getWidth() / 2, y
					- Screen.getY() + drawable.getHeight() / 2, 0);
			glRotatef(rot, 0, 0, 1.0f);
			glTranslatef(-drawable.getWidth() / 2, -drawable.getHeight() / 2, 0);
			glScalef(xScale, yScale, 1.0f);

			glBindTexture(GL_TEXTURE_2D, sprite.getTextureRegion().getTexture()
					.getTextureID());

			glBindBuffer(GL_ARRAY_BUFFER, sprite.getVertexBufferObject()
					.getVertexHandle());
			glVertexPointer(sprite.getVertexBufferObject().getDimension(),
					GL_FLOAT, 0, 0L);

			glBindBuffer(GL_ARRAY_BUFFER, sprite.getVertexBufferObject()
					.getTextureHandle());
			glTexCoordPointer(2, GL_FLOAT, 0, 0L);

			glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glDrawArrays(GL_TRIANGLES, 0, sprite.getVertexBufferObject()
					.getVertexCount());
			glDisableClientState(GL_VERTEX_ARRAY);
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);

		}
		glPopMatrix();
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);

	}

	public static void line(float x, float y, float destX, float destY) {
		line(x, y, destX, destY, DEFAULT_COLOR);
	}

	public static void line(float x, float y, float destX, float destY,
			Color color) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
			glBegin(GL_LINE_STRIP);
			{
				glVertex2d(0, 0);
				glVertex2d((destX - x), (destY - y));
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void rect(float x, float y, float width, float height) {
		rect(x, y, width, height, 0, Draw.DEFAULT_COLOR);
	}

	public static void rect(float x, float y, float width, float height,
			float rot) {
		rect(x, y, width, height, rot, Draw.DEFAULT_COLOR);
	}

	public static void rect(float x, float y, float width, float height,
			Color color) {
		rect(x, y, width, height, 0, color);
	}

	public static void rect(float x, float y, float width, float height,
			float rot, Color color) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
			glRotatef(rot, 0, 0, 0);

			glBegin(GL_LINE_LOOP);
			{
				// BOTTOM LEFT
				glVertex2f(0, 0);
				// BOTTOM RIGHT
				glVertex2f(width, 0);
				// TOP RIGHT
				glVertex2f(width + 1, height);
				// TOP LEFT
				glVertex2f(0, height);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void rect(GameObject object) {
		rect(object.getX(), object.getY(), object.getWidth() - 1,
				object.getHeight() - 1);
	}

	public static void rect(GameObject object, float xOffset, float yOffset) {
		rect(object.getX() + xOffset, object.getY() + yOffset,
				object.getWidth(), object.getHeight());
	}

	public static void string(String drawString) {
		string(drawString, 0, 0, DEFAULT_COLOR, false);
	}

	public static void string(String drawString, float xOffset, float yOffset) {
		string(drawString, xOffset, yOffset, DEFAULT_COLOR, false);
	}

	public static void string(String string, float xOffset, float yOffset,
			Color color) {
		string(string, xOffset, yOffset, color, false);
	}

	public static void string(String string, float xOffset, float yOffset,
			Color color, boolean followScreen) {
		char current;
		float x = xOffset, xRunning = 0, y = yOffset;
		for (int i = 0; i < string.length(); i++) {
			current = string.charAt(i);
			if (current == 10) {
				xRunning = 0;
				yOffset -= Sprite.CHAR_HEIGHT;
				continue;
			}
			Sprite sprite = Sprite.resolveChar(current);

			x = xOffset + xRunning;
			y = yOffset;
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}

			xRunning += Sprite.CHAR_WIDTH;

			glColor4f(color.r, color.g, color.b, Screen.getTransparency()
					* color.a);
			glPushMatrix();
			{
				glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);

				glBindTexture(GL_TEXTURE_2D, sprite.getTextureRegion()
						.getTexture().getTextureID());

				glBindBuffer(GL_ARRAY_BUFFER, sprite.getVertexBufferObject()
						.getVertexHandle());
				glVertexPointer(sprite.getVertexBufferObject().getDimension(),
						GL_FLOAT, 0, 0L);

				glBindBuffer(GL_ARRAY_BUFFER, sprite.getVertexBufferObject()
						.getTextureHandle());
				glTexCoordPointer(2, GL_FLOAT, 0, 0L);

				glEnableClientState(GL_VERTEX_ARRAY);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glDrawArrays(GL_TRIANGLES, 0, sprite.getVertexBufferObject()
						.getVertexCount());
				glDisableClientState(GL_VERTEX_ARRAY);
				glDisableClientState(GL_TEXTURE_COORD_ARRAY);

			}
			glPopMatrix();
			glBindBuffer(GL_ARRAY_BUFFER, 0); // TODO ?
			glBindTexture(GL_TEXTURE_2D, 0);
		}
	}

}
