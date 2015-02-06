package no.kjelli.pong.menu;

import no.kjelli.generic.Callback;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextStatic;
import no.kjelli.pong.config.PlayerConfig;

import org.newdawn.slick.Color;

public class ControlInput {
	private String nameStr = "Name", upStr = "OPP", downStr = "NED", powStr = "SKYT",
			pStr = "Spiller ";
	public TextStatic namelabel, label;
	public ControlInputButton up, down, pow;
	public NameInputBox name;
	public ConfirmButton ok;
	public PlayerConfig pc;
	private Callback cb;

	public ControlInput(float x, float y, PlayerConfig pc, Callback callback) {
		this.pc = pc;
		this.cb = callback;
		namelabel = new TextStatic(nameStr, x - nameStr.length() * Sprite.CHAR_WIDTH / 2, y + 90, Color.white);
		System.out.println(pc.name);
		name = new NameInputBox(pc.name, x - NameInputBox.MAX_CAP * Sprite.CHAR_WIDTH
				/ 2, y + 75);

		label = new TextStatic(pStr +" "+ (pc.playerNo+1), x - pStr.length() * Sprite.CHAR_WIDTH / 2,
				y + 120, Color.white);
		this.up = new ControlInputButton(x - ControlInputButton.WIDTH / 2, y,
				upStr + ":", this);
		this.down = new ControlInputButton(x - ControlInputButton.WIDTH / 2, y
				- ControlInputButton.HEIGHT, downStr + ":", this);
		this.pow = new ControlInputButton(x - ControlInputButton.WIDTH / 2, y
				- 2 * ControlInputButton.HEIGHT, powStr + ":", this);
		this.ok = new ConfirmButton(x - ConfirmButton.WIDTH / 2, y - 3.1f
				* ControlInputButton.HEIGHT, ControlInput.this);
		World.add(namelabel);
		World.add(name);
		World.add(label);
		World.add(up);
		World.add(down);
		World.add(pow);
		World.add(ok);
	}

	public void notifyChange(ControlInputButton src) {
		if (src == up)
			pc.upKey = src.key;
		else if (src == down)
			pc.downKey = src.key;
		else if (src == pow)
			pc.powKey = src.key;
	}

	public void complete() {
		if (pc.isValid()) {
			pc.name = name.getText();
			cb.run();
		} else {
			System.out.println("Invalid tho");
		}
	}
}
