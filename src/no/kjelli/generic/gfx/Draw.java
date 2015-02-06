package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import no.kjelli.generic.gameobjects.GameObject;

import org.newdawn.slick.Color;

public class Draw {

	public static final Color DEFAULT_COLOR = new Color(1f, 1f, 1f, 1f);

	public static void fillRect(float x, float y,float z, float width, float height) {
		fillRect(x, y, z, width, height, 0, DEFAULT_COLOR, false);
	}

	public static void fillRect(float x, float y, float z, float width, float height,
			float rot) {
		fillRect(x, y, z, width, height, rot, DEFAULT_COLOR, false);
	}

	public static void fillRect(float x, float y, float width, float height,
			Color color) {
		fillRect(x, y, 0, width, height, 0, color, false);
	}

	public static void fillRect(float x, float y, float width, float height,
			float rot, Color color, boolean followScreen) {
		fillRect(x, y, 0, width, height, rot, color, followScreen);
	}

	public static void fillRect(float x, float y, float z, float width,
			float height, Color color) {
		fillRect(x, y, z, width, height, 0, color, false);
	}

	public static void fillRect(float x, float y, float z, float width,
			float height, float rot, Color color) {
		fillRect(x, y, z, width, height, rot, color, false);
	}

	public static void fillRect(float x, float y, float z, float width,
			float height, float rot, Color color, boolean followScreen) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}

			glTranslatef(x - Screen.getX() + width / 2, y - Screen.getY()
					+ height / 2, z);
			glRotatef(rot, 0, 0, 1);
			glTranslatef(-width / 2, -height / 2, 0);

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

	public static void drawable(Drawable drawable) {
		drawable(drawable, 0, 0, 0, 0, false);
	}

	public static void drawable(Drawable drawable, boolean followScreen) {
		drawable(drawable, 0, 0, 0, 0, followScreen);
	}

	public static void drawable(Drawable drawable, float xOffset, float yOffset) {
		drawable(drawable, xOffset, yOffset, 0, 0, false);
	}

	public static void drawable(Drawable drawable, float xOffset,
			float yOffset, float zOffset) {
		drawable(drawable, xOffset, yOffset, zOffset, 0.0f, false);
	}

	@Deprecated
	public static void drawable(Drawable drawable, float xOffset,
			float yOffset, float zOffset, float rot, boolean followScreen) {

		Sprite sprite = drawable.getSprite();

		if (sprite == null) {
			System.err.println("No working sprite! [" + drawable + "]");
			return;
		}

		float x = drawable.getX() + xOffset;
		float y = drawable.getY() + yOffset;
		float z = drawable.getZ() + zOffset;
		float xScale = drawable.getXScale();
		float yScale = drawable.getYScale();
		sprite(sprite, x, y, z, rot, xScale, yScale, followScreen);

	}

	public static void sprite(Sprite sprite, float x, float y) {
		sprite(sprite, x, y, 0.0f, 0.0f, 1.0f, 1.0f, false);
	}

	public static void sprite(Sprite sprite, float x, float y, float z) {
		sprite(sprite, x, y, z, 0.0f, 1.0f, 1.0f, false);
	}

	public static void sprite(Sprite sprite, float x, float y, float z,
			float rot, float xScale, float yScale, boolean followScreen) {
		sprite(sprite, x, y, z, 0.0f, 1.0f, 1.0f, false, false, false);
	}

	public static void sprite(Sprite sprite, float x, float y, float z,
			float rot, float xScale, float yScale, boolean xFlip,
			boolean yFlip, boolean followScreen) {
		glPushMatrix();

		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}

			glTranslatef(x - Screen.getX() + sprite.getWidth()*xScale / 2,
					y - Screen.getY() + sprite.getHeight()*xScale / 2, z);
			glRotatef(rot, 0, 0, 1);
			glTranslatef(-sprite.getWidth()*xScale / 2, -sprite.getHeight()*xScale / 2, 0);
			glScalef(xScale * (xFlip ? -1 : 1), yScale * (yFlip ? -1 : 1), 1.0f);
			
			if (xFlip)
				glTranslatef(-sprite.getWidth() - 1, 0.0f, 0.0f);

			Color color = sprite.getColor();

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
		line(x, y, 0, destX, destY, DEFAULT_COLOR, false);
	}

	public static void line(float x, float y, float destX, float destY,
			Color color) {
		line(x, y, 0, destX, destY, color, false);
	}

	public static void line(float x, float y, float destX, float destY,
			Color color, boolean followScreen) {
		line(x, y, 0, destX, destY, color, followScreen);
	}

	public static void line(float x, float y, float z, float destX,
			float destY, Color color, boolean followScreen) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}
			glTranslatef(x - Screen.getX(), y - Screen.getY(), z);
			glBegin(GL_LINE_STRIP);
			{
				glVertex2d(0, 0);
				glVertex2d((destX - x), (destY - y));
			}
			glEnd();
		}
		glPopMatrix();
	}

	public static void rect(GameObject object) {
		rect(object.getX(), object.getY(), object.getZ(), object.getWidth(),
				object.getHeight(), object.getRotation(), Draw.DEFAULT_COLOR,
				false);
	}

	public static void rect(GameObject object, float z) {
		rect(object.getX(), object.getY(), z, object.getWidth(),
				object.getHeight(), object.getRotation(), Draw.DEFAULT_COLOR,
				false);
	}
	
	public static void rect(GameObject object, float z, Color color) {
		rect(object.getX(), object.getY(), z, object.getWidth(),
				object.getHeight(), object.getRotation(), color,
				false);
	}

	public static void rect(GameObject object, Color color) {
		rect(object.getX(), object.getY(), object.getZ(), object.getWidth(),
				object.getHeight(), object.getRotation(), color, false);
	}

	public static void rect(float x, float y, float z, float width, float height) {
		rect(x, y, z, width, height, 0, Draw.DEFAULT_COLOR, false);
	}

	public static void rect(float x, float y, float z, float width,
			float height, float rot) {
		rect(x, y, z, width, height, rot, Draw.DEFAULT_COLOR, false);
	}

	public static void rect(float x, float y, float width, float height,
			Color color) {
		rect(x, y, 0, width, height, 0, color, false);
	}

	public static void rect(float x, float y, float width, float height) {
		rect(x, y, 0, width, height, 0, DEFAULT_COLOR, false);
	}

	public static void rect(float x, float y, float z, float width,
			float height, float rot, Color color) {
		rect(x, y, z, width, height, 0, color, false);
	}

	public static void rect(float x, float y, float z, float width,
			float height, Color color) {
		rect(x, y, z, width, height, 0, color, false);
	}

	public static void rect(GameObject object, float xOffset, float yOffset) {
		rect(object.getX() + xOffset, object.getY() + yOffset, object.getZ(),
				object.getWidth(), object.getHeight(), object.getRotation());
	}

	public static void rect(float x, float y, float z, float width,
			float height, float rot, Color color, boolean followScreen) {

		glColor4f(color.r, color.g, color.b, color.a);
		glPushMatrix();
		{
			if (followScreen) {
				x += Screen.getX();
				y += Screen.getY();
			}
			glTranslatef(x - Screen.getX() + width/2, y - Screen.getY() + height/2, z);
			glRotatef(rot, 0, 0, 1);
			glTranslatef(-width/2,-height/2, 0);

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

	public static void string(String drawString, float xOffset, float yOffset) {
		string(drawString, xOffset, yOffset, 0, 1, 1, DEFAULT_COLOR, false);
	}

	public static void string(String drawString, float xOffset, float yOffset,
			float zOffset) {
		string(drawString, xOffset, yOffset, zOffset, 1, 1, DEFAULT_COLOR,
				false);
	}

	public static void string(String string, float xOffset, float yOffset,
			Color color) {
		string(string, xOffset, yOffset, 0, 1, 1, color, false);
	}
	
	public static void string(String string, float xOffset, float yOffset,
			float z, Color color) {
		string(string, xOffset, yOffset, z, 1, 1, color, false);
	}

	public static void string(String string, float xOffset, float yOffset,
			float xScale, float yScale, Color color, boolean followScreen) {
		string(string, xOffset, yOffset, 0, xScale, yScale, color, followScreen);
	}

	public static void string(String string, float xOffset, float yOffset,
			float z, float xScale, float yScale, Color color,
			boolean followScreen) {
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
				glTranslatef(x - Screen.getX(), y - Screen.getY(), z);
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
