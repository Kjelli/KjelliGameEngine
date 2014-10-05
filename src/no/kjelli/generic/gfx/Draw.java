package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import no.kjelli.generic.gameobjects.GameObject;

import org.newdawn.slick.Color;

public class Draw {

	public static final Color DEFAULT_COLOR = new Color(1f, 1f, 1f, 1f);

	public static void fillRect(float x, float y, float width, float height) {
		fillRect(x, y, width, height, 0, DEFAULT_COLOR, false);
	}

	public static void fillRect(float x, float y, float width, float height,
			float rot) {
		fillRect(x, y, width, height, 0, DEFAULT_COLOR, false);
	}

	public static void fillRect(float x, float y, float width, float height,
			Color color) {
		fillRect(x, y, width, height, 0, color, false);
	}

	public static void fillRect(float x, float y, float width, float height,
			float rot, Color color, boolean followScreen) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}

			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
			glTranslatef(0.5f, 0.5f, 0.0f);
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
		sprite(drawable, 0, 0, 0, 1.0f, 1.0f, false);
	}

	public static void sprite(Drawable drawable, boolean followScreen) {
		sprite(drawable, 0, 0, 0, 1.0f, 1.0f, followScreen);
	}

	public static void sprite(Drawable drawable, float xOffset, float yOffset) {
		sprite(drawable, xOffset, yOffset, 0, 1.0f, 1.0f, false);
	}

	public static void sprite(Drawable drawable, float xOffset, float yOffset,
			float rot, boolean followScreen) {
		sprite(drawable, xOffset, yOffset, rot, 1.0f, 1.0f, followScreen);

	}

	public static void sprite(Drawable drawable, float xOffset, float yOffset,
			float rot, float xScale, float yScale, boolean followScreen) {

		Sprite sprite = drawable.getSprite();
		Color color = drawable.getColor();

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

			glTranslatef(x - Screen.getX() + drawable.getWidth() / 2, y
					- Screen.getY() + drawable.getHeight() / 2, 0);
			glRotatef(rot, 0, 0, 1.0f);
			glTranslatef(-drawable.getWidth() / 2, -drawable.getHeight() / 2, 0);
			glScalef(xScale, yScale, 1.0f);

			glColor4f(color.r, color.g, color.b, Screen.getTransparency()
					* color.a);

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
		line(x, y, destX, destY, DEFAULT_COLOR, false);
	}

	public static void line(float x, float y, float destX, float destY,
			Color color) {
		line(x, y, destX, destY, color, false);
	}

	public static void line(float x, float y, float destX, float destY,
			Color color, boolean followScreen) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}
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
		rect(x, y, width, height, 0, Draw.DEFAULT_COLOR, false);
	}

	public static void rect(float x, float y, float width, float height,
			float rot) {
		rect(x, y, width, height, rot, Draw.DEFAULT_COLOR, false);
	}

	public static void rect(float x, float y, float width, float height,
			Color color) {
		rect(x, y, width, height, 0, color, false);
	}

	public static void rect(float x, float y, float width, float height,
			float rot, Color color, boolean followScreen) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}
			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
			glTranslatef(0.5f, 0.5f, 0.0f);
			glRotatef(rot, 0, 0, 0);

			glBegin(GL_LINE_LOOP);
			{
				// BOTTOM LEFT
				glVertex2f(0, 0);
				// BOTTOM RIGHT
				glVertex2f(width, 0);
				// TOP RIGHT
				glVertex2f(width, height);
				// TOP LEFT
				glVertex2f(0, height);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void rect(GameObject object, Color color) {
		rect(object.getX(), object.getY(), object.getWidth() - 1,
				object.getHeight() - 1, color);
	}

	public static void rect(GameObject object) {
		rect(object.getX(), object.getY(), object.getWidth() - 1,
				object.getHeight() - 1);
	}

	public static void rect(GameObject object, float xOffset, float yOffset) {
		rect(object.getX() + xOffset, object.getY() + yOffset,
				object.getWidth(), object.getHeight());
	}

	public static void string(String drawString, float xOffset, float yOffset) {
		string(drawString, xOffset, yOffset, 1, 1, DEFAULT_COLOR, false);
	}

	public static void string(String string, float xOffset, float yOffset,
			Color color) {
		string(string, xOffset, yOffset, 1, 1, color, false);
	}

	public static void string(String string, float xOffset, float yOffset,
			float xScale, float yScale, Color color, boolean followScreen) {
		char current;
		float x = xOffset, xRunning = 0, y = yOffset;
		for (int i = 0; i < string.length(); i++) {
			current = string.charAt(i);
			if (current == 10) {
				xRunning = 0;
				yOffset -= Sprite.CHAR_HEIGHT * yScale;
				continue;
			}
			Sprite sprite = Sprite.resolveChar(current);

			x = xOffset + xRunning;
			y = yOffset;
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}

			xRunning += Sprite.CHAR_WIDTH * xScale;

			glColor4f(color.r, color.g, color.b, Screen.getTransparency()
					* color.a);
			glPushMatrix();
			{
				glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
				glScalef(xScale, yScale, 1.0f);

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
