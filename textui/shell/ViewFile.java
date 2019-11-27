package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for viewing the content of a file of the current working directory.
 * §2.2.9.
 */
public class ViewFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ViewFile(Shell shll) {
    super(MenuEntry.CAT, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Form f = new Form(title());
    InputString file = new InputString(f, poof.textui.shell.Message.fileRequest());
    f.parse();

    Display d = new Display(title());
    d.add(entity().getFileSystem().viewFile(file.value()));
    d.display();
  }
}
