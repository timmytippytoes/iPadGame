package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent; 
/**
 * This is a controller which controls a game world
 * model.
 * 
 * @author Tim
 *
 */

public class Game extends Form implements Runnable{
	private GameWorld gw;
	UITimer timer;

	public Game () {
		timer = new UITimer(this);
		timer.schedule(100, true, this);
		MapView mv = new MapView();
		ScoreView sv = new ScoreView();
		gw = new GameWorld();
		gw.addObserver(mv);
		gw.addObserver(sv);
		this.setLayout(new BorderLayout());
		Container eastContainer = new Container();
		Container westContainer = new Container();
		Container southContainer = new Container();

		westContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		//BUTTONS
		Button aButton = new Button();
		CommandMaker aCom = new CommandMaker(this, "Accelerate", 'a');
		aButton.setCommand(aCom);
		aButton.setText("accelerate");
		changeButton(aButton);
		westContainer.add(aButton);
		
		CommandMaker bCom = new CommandMaker(this, 'b');
		Button bButton = new Button();
		bButton.setCommand(bCom);
		bButton.setText("brake");
		changeButton(bButton);
		westContainer.add(bButton);
		
		Button lButton = new Button();
		CommandMaker lCom = new CommandMaker(this, 'l');
		lButton.setCommand(lCom);
		lButton.setText("left turn");
		changeButton(lButton);
		eastContainer.add(lButton);
		
		Button rButton = new Button();
		CommandMaker rCom = new CommandMaker(this, 'r');
		rButton.setCommand(rCom);
		rButton.setText("right turn");
		changeButton(rButton);
		eastContainer.add(rButton);

		
		
		southContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		Button csButton = new Button("change strategies");
		CommandMaker csCom = new CommandMaker(this, "change strategies");
		csButton.setCommand(csCom);
		csButton.setText("change strategies");
		changeButton(csButton);
		southContainer.add(csButton);
		
		Button posButton = new Button("Position");
		Position posCom = new Position(this, "Position");
		posButton.setCommand(posCom);
		southContainer.add(posButton);
		
		
		
		
		
		//SIDE MENU ITEMS
		Toolbar sideMenu = new Toolbar();
		this.setToolbar(sideMenu);
		Toolbar.setOnTopSideMenu(false);
		
		sideMenu.addCommandToSideMenu(aCom);
		
		CommandMaker soundCom = new CommandMaker(this, "Sound");
		CheckBox soundMenu	= new CheckBox();
		soundMenu.setCommand(soundCom);
		soundMenu.setSelected(true);
		soundMenu.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		soundMenu.setText("Sound");
		
		CommandMaker aboutCom = new CommandMaker(this, "About");
		sideMenu.addCommandToSideMenu(aboutCom);
		
		CommandMaker exitCom = new CommandMaker(this, "Exit");
		sideMenu.addCommandToSideMenu(exitCom);
		sideMenu.addComponentToSideMenu(soundMenu);
		
		//HELP
		Toolbar.setOnTopSideMenu(true);
		CommandMaker helpCom = new CommandMaker(this, "Help");
		sideMenu.addCommandToRightBar(helpCom);
		
		//PAUSE BUTTON
		Button pauseButton = new Button();
		EspCommand pauseCom = new EspCommand(this, "Pause", posButton, soundMenu, rButton, lButton, bButton, aCom, aButton);
		pauseButton.setCommand(pauseCom);
		//pauseButton.setText("Pause");
		//changeButton(tButton);
		pauseButton.setWidth(50);
		southContainer.add(pauseButton);
		
		this.add(BorderLayout.EAST, eastContainer);
		this.add(BorderLayout.WEST, westContainer);
		this.add(BorderLayout.SOUTH, southContainer);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		this.show();
		gw.init(mv.getWidth(), mv.getHeight());
		
	}
	private void changeButton(Button but) {//changes the style of buttons
		but.getAllStyles().setFgColor(ColorUtil.BLUE);
		but.getAllStyles().setBorder(Border.createEtchedRaised(ColorUtil.LTGRAY, ColorUtil.GRAY));
		Button b = new Button();
		b.getAllStyles().setBorder(Border.createEtchedLowered(ColorUtil.LTGRAY, ColorUtil.GRAY));
		but.setPressedStyle(b.getStyle());
	}
		
	//defines actions when action items are activated in the GUI
	private class CommandMaker extends Command{
		private Form aForm;
		private char keyCode;
		public CommandMaker(Form f, String title) {
			super(title);
		}
		public CommandMaker(Form f, char key) {
			super(new String (""+key));
			aForm = f;
			aForm.addKeyListener(key, this);
			keyCode = key;
		}
		
		public CommandMaker(Form f, String title, char key) {
			super(title);
			aForm = f;
			aForm.addKeyListener(key, this);
			keyCode = key;
		}
		
		public void removeListener() {
			aForm.removeKeyListener(keyCode, this);
		}
		
		public void addListener() {
			aForm.addKeyListener(keyCode, this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = this.getCommandName();
			switch(command) {
				case "Accelerate":
					gw.accelerate();//accelerates player robot
					break;
				case "b":
					gw.brake();//slows player Robot
					break;
				case "l":
					gw.steerLeft();//steers player left
					break;
				case "r":
					gw.steerRight();//steers player right
					break;
				case "Sound":
					gw.sound();//changes sound setting
					break;
				case "About"://about prompt
					Dialog.show("About", "By Timothy Darrow\nCSC 133: Object Oriented Computer Graphics\nVersion 1.0", "Ok", null);
					break;
				case "Exit":
					Command yes = new Command("Yes");
					Command no = new Command("No");
					Command co = Dialog.show("Exit", "Are you sure?", yes, no);//exit prompt
					if (co == yes)
						System.exit(0);
					break;
				case "Help":
					Dialog.show("Help", "Keyboard Commands:\na = accelerate\nb = brake\nl = left turn\nr = right turn\n"
							+ "e = collide with energy base\ng = collide with drone\n t = tick", "Ok", null);
					break;
				case "change strategies"://changes NPR strategy
					gw.changeStrats();
					break;
				default://in case input is invalid..more so for error checking
					Dialog.show("Invalid Input", command + " is not valid", "Ok", null);
					break;
			}
			
		}
		
	}
	//command that handles pausing
	private class EspCommand extends Command{
		private Form aForm;
		private boolean paused;
		private Button posButton;
		private Button rButton;
		private Button lButton;
		private Button bButton;
		private CommandMaker aCom;
		private Button aButton;
		private CheckBox soundButton;
		public EspCommand(Form f, String title, Button pos, CheckBox sound, Button right, Button left, Button brake, CommandMaker acc, Button accb) {
			super(title);
			aForm = f;
			paused = false;
			posButton = pos;
			soundButton = sound;
			rButton = right;
			lButton = left;
			bButton = brake;
			aCom = acc;
			aButton = accb;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!gw.getPause()) {
				((Button)e.getActualComponent()).setText("Play");
				timer.cancel();
				gw.setPause(true);
				gw.sound();
				posButton.setEnabled(true);
				soundButton.setEnabled(false);
				rButton.setEnabled(false);
				((CommandMaker)rButton.getCommand()).removeListener();
				lButton.setEnabled(false);
				((CommandMaker)lButton.getCommand()).removeListener();
				bButton.setEnabled(false);
				((CommandMaker)bButton.getCommand()).removeListener();
				aButton.setEnabled(false);
				((CommandMaker)aButton.getCommand()).removeListener();
				aCom.setEnabled(false);
			}
			else {
				((Button)e.getActualComponent()).setText("Pause");
				timer.schedule(100, true, aForm);
				gw.setPause(false);
				gw.sound();
				posButton.setEnabled(false);
				soundButton.setEnabled(true);
				rButton.setEnabled(true);
				((CommandMaker)rButton.getCommand()).addListener();
				lButton.setEnabled(true);
				((CommandMaker)lButton.getCommand()).addListener();
				bButton.setEnabled(true);
				((CommandMaker)bButton.getCommand()).addListener();
				aButton.setEnabled(true);
				((CommandMaker)aButton.getCommand()).addListener();
				aCom.setEnabled(true);
			}
		}
	}
	//command that enables repositioning
	private class Position extends Command{
		private Form aForm;
		public Position(Form f, String title) {
			super(title);
			aForm = f;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (gw.getPause() && !gw.getEnablePositioning())
				gw.enablePositioning(true);
			else
				gw.enablePositioning(false);
		}
		
	}

	public void run() {
		gw.tick();
		
	}
}
