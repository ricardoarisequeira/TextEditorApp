package poof.textui;

import static pt.utl.ist.po.ui.UserInteraction.IO;
import pt.utl.ist.po.ui.TextInteraction;

import java.lang.ClassNotFoundException;
import java.io.*;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Menu;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.exception.*;
import poof.textui.main.MainMenu;
import poof.parser.ParseFile;
import poof.core.FileSystem;

/**
 * Classe que suporta um sistema de ficheiros. É responsável por criar um sistema de ficheiros,
 * importá-lo através da classe ParseFile, carregar um sistema de ficheiros criado anteriormente
 * (pela mesma shell), ou guardar um sistema de ficheiros.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class Shell {

    /** Sistema de ficheiros que está aberto ou não (neste caso será null). */
	private FileSystem _fSystem;

    public static void main(String[] args) throws IOException, InvalidOperation {
        Shell shll = new Shell();
        MainMenu menu = new MainMenu(shll);
        String filename = System.getProperty("import");

        // Verifica se foi utilizada a propriedade import
        // Em caso afirmativo, é iniciado um sistema de ficheiros através do ficheiro especificado
        if (filename != null) {
            ParseFile parseFile = new ParseFile();
            shll._fSystem = parseFile.parse(filename);
            shll._fSystem.setState(true);
            menu.showOptionsNonEmpty();
        }
        
        menu.open();
        IO.close();
    }

    /**
     * Devolve o sistema de ficheiros que está na shell.
     *
     * @return O sistema de ficheiros aberto (ou não - null) do shell.
     */
	public FileSystem getFileSystem() {
		return _fSystem;
	}

    /**
     * Metodo responsavel por criar um novo sistema de ficheiros.
     */
	public void newFileSystem() {
		_fSystem = new FileSystem();
        _fSystem.setState(true);
	}

    /**
     * Metodo responsavel por guardar um sistema de ficheiros para posterior utilizacao.
     *
     * @throws  IOException se ocorrer um erro no processo de escrever o sistema de ficheiros (writeObject).
     */
    public void save() throws IOException {

        OutputStream fileOutput = new FileOutputStream(_fSystem.getName() + ".ser");
        OutputStream buffer = new BufferedOutputStream(fileOutput);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        _fSystem.setCurrentDirectory(_fSystem.getCurrentUser().getMainDirectory());
        objectOutput.writeObject(_fSystem);
        _fSystem.setState(false);

    }

    /**
     * Metodo responsavel por abrir um sistema de ficheiros guardado anteriormente.
     *
     * @param   fileName
     *                  Nome do ficheiro onde se encontra o sistema de ficheiros a abrir.
     *
     * @throws  ClassNotFoundException se a classe que se quer serializar não for encontrada.
     * @throws  IOException se ocorrer um erro no processo de ler o sistema de ficheiros (readObject).
     */
    public void load (String fileName) throws IOException, ClassNotFoundException {

        InputStream fileInput = new FileInputStream(fileName + ".ser");
        InputStream buffer = new BufferedInputStream(fileInput);
        ObjectInputStream objectInput = new ObjectInputStream(buffer);
        _fSystem = (FileSystem) objectInput.readObject();
        _fSystem.setState(false);
        
    }
}