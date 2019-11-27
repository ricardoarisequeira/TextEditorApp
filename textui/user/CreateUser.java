package poof.textui.user;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;
import poof.core.*;

/**
 * Command for creating a user.
 * §2.3.1.
 */
public class CreateUser extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateUser(Shell shll) {
    super(MenuEntry.CREATE_USER, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    Form f = new Form(title());
    InputString username = new InputString(f, poof.textui.user.Message.usernameRequest());
    InputString name = new InputString(f, poof.textui.user.Message.nameRequest());
    f.parse();

    entity().getFileSystem().newUser(username.value(), name.value());
  }
}
