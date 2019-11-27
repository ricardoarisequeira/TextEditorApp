package poof.parser;

import poof.core.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Classe que permite a inicialização de um sistema de ficheiros através
 * de um ficheiro de dados textuais.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */
public class ParseFile {

  /** Sistema de ficheiros que vai resultar do parse. */
  private FileSystem _fileSystem;

  /**
   * Metodo que vai fazer a leitura do ficheiro importado e criar um sistema de ficheiros.
   *
   * @param		fileName
   *               Nome do ficheiro que vai ser importado para a criacao do sistema de ficheiros.
   *
   * @throws	IOException se houver problemas a ler o ficheiro.
   */
  public FileSystem parse(String fileName) throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(fileName));

    _fileSystem = new FileSystem();

    String line;

    while ((line = reader.readLine()) != null) {
      parseLine(line);
    }
    
    return _fileSystem;
  }

  /**
   * Metodo que vai fazer a leitura de uma linha especifica e chamar o metodo apropriado.
   *
   * @param line
   *           Linha para ser lida.
   */
  public void parseLine(String line) {
    String[] args = line.split("\\|");
    
    switch (args[0]) {
    case "USER":
      createUser(args[1], args[2]);
      break;
    case "DIRECTORY":
      createDirectory(args[1], args[2], args[3]);
      break;
    case "FILE":
      createFile(args[1], args[2], args[3], args[4]);
      break;
    }
  }

  /**
   * Metodo responsavel por criar um novo utilizador.
   *
   * @param username
   *           Identificador do novo utilizador.
   * @param name
   *           Nome do novo utilizador.
   */
  public void createUser(String username, String name) {

  		Directory homeDirectory = _fileSystem.getHomeDirectory();
		Directory userHome = new Directory(username, homeDirectory, null, true);
		homeDirectory.getDirectories().put(username, userHome);
		User newUser = _fileSystem.getRoot().createNewUser(username, name, userHome);
		_fileSystem.getAllUsers().add(newUser);
  }

  /**
   * Metodo responsavel por criar uma nova entrada.
   *
   * @param   path
   *              Caminho para a nova entrada.
   * @param   username
   *              Dono da nova entrada.
   * @param   permission
   *              Permissao da nova entrada.
   * @param   isDir
   *              Booleano que permite identificar um diretorio (true) ou um ficheiro (false).
   */
  private void createEntry(String path, String username,
  						   String permission, boolean isDir, String content) {

    int last = path.lastIndexOf('/');
    String parentPath = path.substring(0, last);
    String entryName = path.substring(last + 1);

    boolean permissionBoolean = (permission.equals("public") ? false : true);
    Directory parentDir = null;
    User user = null;

    for (User aux: _fileSystem.getAllUsers()) {
      if (aux.getUsername().equals(username))
      	user = aux;
	  }

    if (isDir) {

      parentDir = _fileSystem.goTo(parentPath);
      Directory dir = new Directory(entryName, parentDir, user, permissionBoolean);
      parentDir.getDirectories().put(entryName, dir);

    } else {

      parentDir = _fileSystem.goTo(parentPath);
      File file = new File(entryName, parentDir, user, permissionBoolean, content);
      parentDir.getFiles().put(entryName, file);

    }
  }

  /**
   * Metodo responsavel por criar um novo ficheiro.
   *
   * @param   path
   *              Caminho para o novo ficheiro.
   * @param   username
   *              Dono do novo ficheiro.
   * @param   permission
   *              Permissao do novo ficheiro.
   * @param   content
   *              Conteudo do novo ficheiro.
   */
  public void createFile(String path, String username, String permission, String content) {
    createEntry(path, username, permission, false, content);
  }

  /**
   * Metodo responsavel por criar um novo diretorio.
   *
   * @param   path
   *              Caminho para o novo diretorio.
   * @param   username
   *              Dono do novo diretorio.
   * @param   permission
   *              Permissao do novo diretorio.
   */
  public void createDirectory(String path, String username, String permission) {
    createEntry(path, username, permission, true, null);
  }

}
