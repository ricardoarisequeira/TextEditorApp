package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for changing the permission of an entry of the current working directory.
 * §2.2.10.
 */
public class ChangePermission extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangePermission(Shell shll) {
    super(MenuEntry.CHMOD, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Form f = new Form(title());
    InputString entry = new InputString(f, poof.textui.shell.Message.nameRequest());
    InputBoolean permission = new InputBoolean(f, poof.textui.shell.Message.writeMode());
    f.parse();

    entity().getFileSystem().changePermission(entry.value(), !permission.value());    
  }
}
