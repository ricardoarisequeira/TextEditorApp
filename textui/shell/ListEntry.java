package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for showing an entry of the current working directory.
 * §2.2.2.
 */
public class ListEntry extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListEntry(Shell shll) {
    super(MenuEntry.LS_ENTRY, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    Form f = new Form(title());
    InputString name = new InputString(f, poof.textui.shell.Message.nameRequest());
    f.parse();

    Display d = new Display(title());
    d.add(entity().getFileSystem().getCurrentDirectory().getEntry(name.value()).listEntry(name.value()));
    d.display();
  }
}
