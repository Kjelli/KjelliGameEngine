package no.kjelli.generic.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import org.lwjgl.opengl.Display;

import no.kjelli.generic.Game;
<<<<<<< HEAD
import no.kjelli.generic.gamewrapper.GameWrapper;
=======
import no.kjelli.generic.main.Launcher;
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene

@SuppressWarnings("serial")
public abstract class AppletLauncher extends Applet {

	Canvas display_parent;
	Launcher launcher;

	public void init() {
		setLayout(new BorderLayout());
		try {
			display_parent = new Canvas() {
				public final void addNotify() {
					super.addNotify();
					startLWJGL();
				}

				public final void removeNotify() {
					stopLWJGL();
					super.removeNotify();
				}
			};
			setSize(getAppletSize());
			display_parent.setSize(getAppletSize());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}

	public abstract Dimension getAppletSize();

	public void destroy() {
		remove(display_parent);
		super.destroy();
	}

	public void startLWJGL() {
		launcher = getGameLauncher();
		launcher.launch();
	}

	private void stopLWJGL() {
		launcher.stop();
	}

	protected abstract Launcher getGameLauncher();

	public Canvas getDisplay_parent() {
		return display_parent;
	}

	public void setDisplay_parent(Canvas display_parent) {
		this.display_parent = display_parent;
	}

}
