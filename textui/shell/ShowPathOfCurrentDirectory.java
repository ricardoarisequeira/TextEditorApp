package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.Shell;

/**
 * Command for showing the full path of the current working directory.
 * §2.2.7.
 */
public class ShowPathOfCurrentDirectory extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ShowPathOfCurrentDirectory(Shell shll) {
    super(MenuEntry.PWD, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    Display d = new Display(title());
    d.addNewLine(entity().getFileSystem().getCurrentDirectory().getAbsolutePath());
    d.display();

  }
}
