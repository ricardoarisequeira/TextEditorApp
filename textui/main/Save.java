package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.main.Message;
import poof.textui.Shell;
import poof.core.FileSystem;

/**
 * Command for saving the relevant applicaion state.
 */
public class Save extends Command<Shell>  {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Save(Shell shll) {
    super(MenuEntry.SAVE, shll);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    
    FileSystem fSystem = entity().getFileSystem();

    if (fSystem.getState() == true) {

      if (fSystem.getName() == null) {

        Form f = new Form(title());
        InputString filename = new InputString(f, poof.textui.main.Message.newSaveAs());
        f.parse();

        fSystem.setName(filename.value());
      }

      try {
        entity().save();
        
      // Professor disse que excepção não seria testada.  
      } catch (IOException e) {
        Display d = new Display(title());
        d.add("Ocorreu um erro inesperado a guardar o sistema de ficheiros!");
        d.display();
      }
    }
  }
}
