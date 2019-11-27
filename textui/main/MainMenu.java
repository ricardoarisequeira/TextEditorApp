package poof.textui.main;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Menu;

import poof.textui.Shell;
import poof.core.FileSystem;

/**
 * Represents the main menu of this apllication. This is the first menu
 * shown to the users.
 ***/ 
public class MainMenu extends Menu {
  /**
   * Constructor
   **/
  public MainMenu(Shell shll) {
    super(MenuEntry.TITLE,
	  new Command<?>[] {
	    new New(shll),
	      new Open(shll),
	      new Save(shll),
	      new Login(shll),
	      new ShowMenuShell(shll),
	      new ShowMenuUser(shll),
	      });
    entry(2).invisible();
    entry(3).invisible();
    entry(4).invisible();
    entry(5).invisible();
  }

  /**
   * Hide options when the application does not have a file system.
   **/
  public void hideOptionsEmpty() {
    entry(2).invisible();
    entry(3).invisible();
    entry(4).invisible();
    entry(5).invisible();
  }

  /**
   * Show hidden options when the application has a file system.
   **/
  public void showOptionsNonEmpty() {
    entry(2).visible();
    entry(3).visible();
    entry(4).visible();
    entry(5).visible();
  }
}
