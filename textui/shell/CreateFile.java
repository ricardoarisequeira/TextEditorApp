package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for creating a file in the current working directory.
 * §2.2.5.
 */
public class CreateFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateFile(Shell shll) {
    super(MenuEntry.TOUCH, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    Form f = new Form(title());
    InputString name = new InputString(f, poof.textui.shell.Message.fileRequest());
    f.parse();

    entity().getFileSystem().addFile(name.value(), true, "");
  }
}
