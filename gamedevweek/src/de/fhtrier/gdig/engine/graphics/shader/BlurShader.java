package de.fhtrier.gdig.engine.graphics.shader;

import org.newdawn.slick.geom.Vector2f;

public class BlurShader extends Shader
{
	private int scrwidth = 0;
	private int scrheight = 0;

	/**
	 * A Gaussian Blur Shader which operates in one dimension only. Call
	 * initialize() to prepare the shader and setVertical for the second pass.
	 */
	public BlurShader() {
		super("content/jumpnrun/shader/simple.vert",
				"content/jumpnrun/shader/blur1D.frag");
	}

	/**
	 * Initializes the Shader for the given image width and height. This method
	 * can only be called if the Shader is set active and should be called each
	 * time it is activated. It also initializes for horizontal blurring.
	 * 
	 * @param imagewidth
	 *            The Width of the Image to render in pixels.
	 * @param imageheight
	 *            The Height of the Image to render in pixels.
	 */
	public void initialize(int imagewidth, int imageheight) {
		setHorizontal();

		if (imagewidth != scrwidth || imageheight != scrheight) {
			scrwidth = imagewidth;
			scrheight = imageheight;

			Vector2f pixelsize = new Vector2f(1.0f / imagewidth,
					1.0f / imageheight);
			setValue("blurparam", 2, new float[] { 0, 0.16f,
					1.5f * pixelsize.x, 0.15f, 3.5f * pixelsize.x, 0.12f,
					5.5f * pixelsize.x, 0.09f, 7.5f * pixelsize.x, 0.05f });
		}
	}

	/**
	 * Activates Horizontal Blurring.
	 */
	private void setHorizontal() {
		setValue("direction", 1, 0);
	}

	/**
	 * Activates Vertical Blurring, call this for the second blur-step.
	 */
	public void setVertical() {
		setValue("direction", 0, 1);
	}
}
