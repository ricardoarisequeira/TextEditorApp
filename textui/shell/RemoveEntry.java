package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for removing an entry of the current working directory.
 * §2.2.3.
 */
public class RemoveEntry extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public RemoveEntry(Shell shll) {
    super(MenuEntry.RM, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Form f = new Form(title());
    InputString entry = new InputString(f, poof.textui.shell.Message.nameRequest());
    f.parse();

    entity().getFileSystem().removeEntry(entry.value());
  }
}
