package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Menu;
import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputInteger;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.shell.*;

import poof.textui.Shell;

/**
 * Command for showing the shell menu.
 */
public class ShowMenuShell extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ShowMenuShell(Shell shll) {
    super(MenuEntry.MENU_SHELL, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    Command<?>[] commands = {
      new ListCurrentDir(entity()),
      new ListEntry(entity()),
      new RemoveEntry(entity()),
      new ChangeCurrentDirectory(entity()),
      new CreateFile(entity()),
      new CreateDirectory(entity()),
      new ShowPathOfCurrentDirectory(entity()),
      new WriteFile(entity()),
      new ViewFile(entity()),
      new ChangePermission(entity()),
      new ChangeOwner(entity())
    };
    
    Menu shellMenu = new Menu(poof.textui.shell.MenuEntry.TITLE, commands);
    shellMenu.open();
  }
}
