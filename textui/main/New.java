package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.main.Save;
import poof.textui.Shell;
import poof.core.FileSystem;

/**
 * Command for creating a new file system and logging the root user.
 */
public class New extends Command<Shell> {
  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public New(Shell shll) {
    super(MenuEntry.NEW, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    FileSystem fSystem = entity().getFileSystem();

    if (fSystem != null && fSystem.getState() != false) {
      Form f = new Form(title());
      InputBoolean save = new InputBoolean(f, poof.textui.main.Message.saveBeforeExit());
      f.parse();

      if (save.value() == true) new Save(entity()).execute();
    }

    entity().newFileSystem();
    ((MainMenu)menu()).showOptionsNonEmpty();
  }
}
