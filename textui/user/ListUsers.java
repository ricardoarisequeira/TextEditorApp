package poof.textui.user;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;
import poof.core.*;

/**
 * Command for the showing existing users.
 * §2.3.2.
 */
public class ListUsers extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListUsers(Shell shll) {
    super(MenuEntry.LIST_USERS, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Display d = new Display();
    d.add(entity().getFileSystem().listUsers());
    d.display();
  }
}
