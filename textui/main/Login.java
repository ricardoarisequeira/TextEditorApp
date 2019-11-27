package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;
import poof.core.FileSystem;

/**
 * Command for the login option.
 * §2.1.2.
 */
public class Login extends Command<Shell> /* FIXME: select core type for entity */ {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Login(Shell shll) {
    super(MenuEntry.LOGIN, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    Form f = new Form(title());
    InputString username = new InputString(f, poof.textui.user.Message.usernameRequest());
    f.parse();

    entity().getFileSystem().setCurrentUser(username.value());
  }
}
