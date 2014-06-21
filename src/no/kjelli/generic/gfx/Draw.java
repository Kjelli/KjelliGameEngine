package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL11.*;
import no.kjelli.generic.gameobjects.GameObject;

import org.newdawn.slick.Color;

public class Draw {
	public static final Color DEFAULT_COLOR = new Color(1f, 1f, 1f);

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

		glPushMatrix();
		{
			glColor4f(color.r, color.g, color.b, color.a);
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

	public static void texture(Drawable drawable) {
		texture(drawable, 0, 0, 1.0f, 1, false);
	}

	public static void texture(Drawable drawable, float xOffset, float yOffset) {
		texture(drawable, xOffset, yOffset, 1.0f, 1, false);
	}

	public static void texture(Drawable drawable, float alpha) {
		texture(drawable, 0, 0, alpha, 1, false);
	}

	public static void texture(Drawable drawable, boolean isGUIComponent) {
		texture(drawable, 0, 0, 1.0f, 1, isGUIComponent);
	}

	public static void texture(Drawable drawable, float alpha,
			boolean isGUIComponent) {
		texture(drawable, 0, 0, alpha, 1, isGUIComponent);
	}

	public static void texture(Drawable drawable, float xOffset, float yOffset,
			float alpha, float rot, boolean followScreen) {
		if (drawable.getTexture() == null) {
			System.err.println("No texture loaded! [" + drawable + "]");
			return;
		}
		float x = drawable.getX() + xOffset;
		float y = drawable.getY() + yOffset;
		if (followScreen) {
			x += Screen.getX();
			y += Screen.getY();
		}
		float width = drawable.getWidth();
		float height = drawable.getHeight();
		glPushMatrix();
		{
			glColor4f(1, 1, 1, Screen.getTransparency() * alpha);
			glBindTexture(GL_TEXTURE_2D, drawable.getTexture().getTextureID());
			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
			glRotatef(rot, 0, 0, 0);

			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 1);
				glVertex2f(0, 0);
				glTexCoord2f(1, 1);
				glVertex2f(width, 0);
				glTexCoord2f(1, 0);
				glVertex2f(width, height);
				glTexCoord2f(0, 0);
				glVertex2f(0, height);

			}
			glEnd();

			glDisable(GL_BLEND);

			glBindTexture(GL_TEXTURE_2D, 0);
		}
		glPopMatrix();

	}

	public static void line(float x, float y, float destX, float destY) {
		line(x, y, destX, destY, DEFAULT_COLOR);
	}

	public static void line(float x, float y, float destX, float destY,
			Color color) {

		glPushMatrix();
		{
			glColor4f(color.r, color.g, color.b, color.a);
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
		glPushMatrix();
		{
			glColor4f(color.r, color.g, color.b, color.a);
			glTranslatef(x - Screen.getX(), y - Screen.getY(), 0);
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

	public static void rect(GameObject object) {
		rect(object.getX(), object.getY(), object.getWidth(),
				object.getHeight());
	}

	public static void rect(GameObject object, float xOffset, float yOffset) {
		rect(object.getX() + xOffset, object.getY() + yOffset,
				object.getWidth(), object.getHeight());
	}

}
