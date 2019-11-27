package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Menu;
import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.user.CreateUser;
import poof.textui.user.ListUsers;

import poof.textui.Shell;

/**
 * Command for showing the user menu.
 */
public class ShowMenuUser extends Command<Shell>  {
  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ShowMenuUser(Shell shll) {
    super(MenuEntry.MENU_USER_MGT, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Command<?>[] commands = {
    	new CreateUser(entity()),
    	new ListUsers(entity()),
    };
    
    Menu userMenu = new Menu(poof.textui.user.MenuEntry.TITLE, commands);
    userMenu.open();
  }
}
