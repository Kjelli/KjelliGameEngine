package no.kjelli.generic.gfx;

public interface VBO extends Drawable {

	public int getVBOVertexHandle();

	public int getVBOTextureHandle();

	public int getDimensions();

	public int getVertexCount();

	public void destroy();
}
