package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputInteger;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for showing the content of working directory.
 * §2.2.1.
 */
public class ListCurrentDir extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListCurrentDir(Shell shll) {
    super(MenuEntry.LS, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() {

    Display d = new Display(title());
    d.add(entity().getFileSystem().getCurrentDirectory().listAllEntries());
    d.display();
  }
}
