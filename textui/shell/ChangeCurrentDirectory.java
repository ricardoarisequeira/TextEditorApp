package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for changing current working directory.
 * §2.2.4.
 */
public class ChangeCurrentDirectory extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangeCurrentDirectory(Shell shll) {
    super(MenuEntry.CD, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Form f = new Form(title());
    InputString directory = new InputString(f, poof.textui.shell.Message.directoryRequest());
    f.parse();

    entity().getFileSystem().changeCurrentDirectory(directory.value());
  }
}
