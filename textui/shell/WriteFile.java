package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for writing in a file of the current working directory.
 * §2.2.8.
 */
public class WriteFile extends Command<Shell>  {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public WriteFile(Shell shll) {
    super(MenuEntry.APPEND, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Form f = new Form(title());
    InputString file = new InputString(f, poof.textui.shell.Message.fileRequest());
    InputString text = new InputString(f, poof.textui.shell.Message.textRequest());
    f.parse();

    entity().getFileSystem().writeFile(file.value(), text.value());

  }
}
