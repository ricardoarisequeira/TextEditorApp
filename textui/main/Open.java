package poof.textui.main;

import java.io.IOException;
import java.lang.ClassNotFoundException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.FileSystem;
import poof.textui.Shell;
import poof.textui.main.Message;

/**
 * Command for loading a file system and the last logged user stored in the given file.
 */
public class Open extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Open(Shell shll) {
    super(MenuEntry.OPEN, shll);
  }
  
  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {

    FileSystem fSystem = entity().getFileSystem();

    if (fSystem != null && fSystem.getState() == true) {
      Form f = new Form(title());
      InputBoolean save = new InputBoolean(f, poof.textui.main.Message.saveBeforeExit());
      f.parse();

      if (save.value() == true) new Save(entity()).execute();
    }

    Form f2 = new Form(title());
    InputString filename = new InputString(f2, poof.textui.main.Message.openFile());
    f2.parse();

    try {
      entity().load(filename.value());
      ((MainMenu)menu()).showOptionsNonEmpty();

    } catch (IOException e1){
      Display d1 = new Display(title());
      d1.addNewLine(poof.textui.main.Message.fileNotFound());
      d1.display();

    } catch (ClassNotFoundException e2){
      Display d2 = new Display(title());
      d2.addNewLine(poof.textui.main.Message.fileNotFound());
      d2.display();
    }
  }
}
