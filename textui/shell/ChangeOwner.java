package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for changing the owner of an entry of the current working directory.
 * §2.2.11.
 */
public class ChangeOwner extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangeOwner(Shell shll) {
    super(MenuEntry.CHOWN, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    Form f = new Form(title());
    InputString entry = new InputString(f, poof.textui.shell.Message.nameRequest());
    InputString username = new InputString(f, poof.textui.shell.Message.usernameRequest());
    f.parse();

    entity().getFileSystem().changeEntryOwner(entry.value(), username.value());
  }
}
