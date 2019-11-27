package poof.core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.Serializable;

import poof.textui.exception.*;

/**
 * Classe que representa um sistema de ficheiros. Tem um nome através do qual é guardado
 * pela Shell, tem uma lista de utilizadores, um diretorio home e um diretório base.
 * É este diretorio que se vai estender por varios diretorios e consistir o sistema de
 * ficheiros. Tem ainda um super utilizador com caracteristicas especiais, um utilizador
 * logado, o diretorio atual onde se encontra e a variavel que representa qualquer alteracao feita.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class FileSystem implements Serializable {

	/** Nome do sistema de ficheiros (nome do ficheiro onde é guardado). */
	private String _name;

	/** Super utilizador do fileSystem */
	private SuperUser _root;

	/** Utilizador atual do sistema de ficheiros. */
	private User _currentUser;

	/** Coleçao de todos os utilizadores do sistema de ficheiros. */
	private ArrayList<User> _users = new ArrayList<User>();

	/** Directorio base do sistema de ficheiros. */
	private Directory _baseDirectory;

	/** Directorio home do sistema de ficheiros. */
	private Directory _homeDirectory;

	/** Directorio atual do sistema de ficheiros onde esta a atuar o menu shell. */
	private Directory _currentDirectory;

	/** Variavel de verifica de alteracoes ao fileSystem */
	private boolean _changesMade;

	/**
	 * Construtor do  sistema de ficheiros. 
	 */
	public FileSystem() {

		// Criação dos diretórios base e home
		_baseDirectory = new Directory("/", null, null, true);
		_homeDirectory = new Directory("home", _baseDirectory, null, true);

		// Criação do super utilizador a atribuição do seu diretório
		Directory rootHome = new Directory("root", _homeDirectory, null, true);
		_root = new SuperUser(rootHome);

		// Estabelecimento do dono do diretorio base e home (super utilizador)
		_baseDirectory.setOwner(_root);
		_homeDirectory.setOwner(_root);

		// Formação da hierarquia de diretórios do sistema de ficheiros
		_baseDirectory.getDirectories().put("home", _homeDirectory);
		_homeDirectory.getDirectories().put("root", rootHome);

		// Atualizacao dos atributos do sistema de ficheiros.
		_users.add(_root);
		_currentDirectory = rootHome;
		_currentUser = _root;
	}

	/**
	 * Devolve o diretório base do sistema de ficheiros.
	 *
	 * @return O diretório base do sistema de ficheiros.
	 */
	public Directory getBaseDirectory() {
		return _baseDirectory;
	}

	/**
	 * Devolve o diretório home do sistema de ficheiros.
	 *
	 * @return O diretório home do sistema de ficheiros.
	 */
	public Directory getHomeDirectory() {
		return _homeDirectory;
	}

	/**
	 * Devolve o diretório atual onde se encontra o sistema de ficheiros (onde está a atuar o menu shell).
	 *
	 * @return O diretorio atual onde se encontra o sistema de ficheiros.
	 */
	public Directory getCurrentDirectory() {
		return _currentDirectory;
	}

	/**
	 * Devolve o utilizador atual do sistema de ficheiros.
	 *
	 * @return O utilizador atual(logado) do sistema de ficheiros.
	 */
	public User getCurrentUser() {
		return _currentUser;
	}

	/**
	 * Devolve o utilizador com o nome indicado, se ele existir no sistema de ficheiros.
	 *
	 * @param	username
	 *				Username do utilizador que se pretende.
	 *
	 * @throws UserUnknownException se o utilizador não existir no sistema de ficheiros.
	 *
	 * @return Utilizador com o username indicado.
	 */
	public User getUser(String username) throws UserUnknownException{
		for (User user: _users)
			if (user.getUsername().equals(username))
				return user;

		throw new UserUnknownException(username);
	}

	/**
	 * Devolve o super utilizador do sistema de ficheiros.
	 *
	 * @return O super utilizador do sistema de ficheiros.
	 */
	public SuperUser getRoot() {
		return _root;
	}

	/**
	 * Devolve todos os utilizadores do sistema de ficheiros.
	 *
	 * @return Array com todos os utilizadores do sistema de ficheiros.
	 */
	public ArrayList<User> getAllUsers() {
		return _users;
	}

	/**
	 * Devolve o nome com que o sistema de ficheiros foi guardado (se já foi guardado...).
	 *
	 * @return O nome do sistema de ficheiros (nome do ficheiros como é guardado).
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Devolve o estado do sistema de ficheiros (se houve alguma alteração desde que foi guardado).
	 *
	 * @return Booleano true se o sistema de ficheiros foi alterado, false caso contrario.
	 */
	public boolean getState() {
		return _changesMade;
	}

	/**
	 * Metodo que recebe uma string com um caminho de diretorios e retorna o diretorio
	 * resultante desse caminho. Também tem a responsabilidade de criar diretórios
	 * durante esse caminho, se eles não existirem. Esses diretórios criados, são, por
	 * omissão, criados em modo privado e pertencentes ao super utilizador.
	 *
	 * @param	path
	 *				Caminho a ser percorrido.
	 *
	 * @return	Diretorio resultante do caminho percorrido.
	 */
	public Directory goTo(String path) {
		String[] absolutePath = path.split("/");
		String current = null;
		Directory currentDir = _baseDirectory;

		for (int index = 1; index < absolutePath.length; index++) {
			current = absolutePath[index];

			if (!currentDir.getDirectories().containsKey(current)) {
				Directory dir = new Directory(current, currentDir, currentDir.getOwner(), true);
				currentDir.getDirectories().put(current, dir);
			}
			currentDir = currentDir.getDirectories().get(current);
		}

		return currentDir;
	}

	/**
	 * Método responsável por chamar a função de criação de novos utilizadores se
	 * as condições o permitirem (caso contrário, lança excepções).
	 *
	 * @param	username
	 *				Username do novo utilizador.
	 * @param	name
	 *				Nome do novo utilizador.
	 *
	 * @throws 	AccessDeniedException se o utilizador atual não for o Super Utilizador,
	 *					único que pode criar novos utilizadores.
	 * @throws 	UserExistsException se já existir um utilizador com o mesmo username.
	 */
	public void newUser(String username, String name)
			throws AccessDeniedException, UserExistsException {

		if (_homeDirectory.haveAccess(_currentUser)) {
			for (User user: _users) 
				if (user.getUsername().equals(username)) 
					throw new UserExistsException(username);

			if (_homeDirectory.getDirectories().containsKey(username))
				_homeDirectory.getDirectories().remove(username);

			Directory userHome = new Directory(username, _homeDirectory, null, true);
			_homeDirectory.getDirectories().put(username, userHome);
			User newUser = _root.createNewUser(username, name, userHome);
			_users.add(newUser);
			_changesMade = true;
		}
	}

	/**
	 * Muda o utilizador atual do sistema de ficheiros. Esta mudança significa que
	 * houve alteracoes no sistema de ficheiros e que o diretorio atual passa a ser
	 * o diretorio do novo utilizador atual. Esta mudanca apenas é possivel se o
	 * utilizador que fez login realmente constar na lista de utilizadores do sistema
	 * de ficheiros.
	 *
	 * @param	username
	 *				Username do utilizador que fez login no sistema de ficheiros.
	 *
	 * @throws	UserUnknownException se o utilizador indicado não existir.
	 */
	public void setCurrentUser(String username) throws UserUnknownException {
		User user = getUser(username);

		// No caso do root ter eliminado o diretorio principal do utilizador
		if (!_homeDirectory.getDirectories().containsKey(username)) {
			Directory newUserHome = new Directory(username, _homeDirectory, user, true);
			_homeDirectory.getDirectories().put(username, newUserHome);
			user.setMainDirectory(newUserHome);
		}

		_currentUser = user;
		_currentDirectory = user.getMainDirectory();
		_changesMade = true;
	}

	/**
	 * Altera o diretorio atual do sistema de ficheiros. Este metodo
	 * é necessario no método save do Shell porque o sistema de ficheiros
	 * é guardado no diretorio principal do utilizador atual.
	 *
	 * @param	dir
	 *				Novo diretorio atual do sistema de ficheiros.
	 */
	public void setCurrentDirectory(Directory dir) {
		_currentDirectory = dir;
	}

	/**
	 * Muda o diretorio atual do sistema de ficheiros (onde está a atuar o menu shell).
	 *
	 * @param	dir
	 *				Diretorio onde entrou o sistema de ficheiros.
	 *
	 * @throws 	IsNotDirectoryException se o nome da entrada for um ficheiro no diretorio.
	 * @throws 	EntryUnknownException se o nome da entrada não existir no diretorio.
	 */
	public void changeCurrentDirectory(String directory)
			throws IsNotDirectoryException, EntryUnknownException {

		if (_currentDirectory.getFiles().containsKey(directory))
			throw new IsNotDirectoryException(directory);

		if (_currentDirectory.getDirectories().containsKey(directory))
			_currentDirectory = _currentDirectory.getDirectories().get(directory);

		else 
			throw new EntryUnknownException(directory);
	}

	/**
	 * Atribui um nome ao sistema de ficheiros.
	 *
	 * @param	name
	 *				Novo nome para o sistema de ficheiros.
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * Muda o estado do sistema de ficheiros.
	 *
	 * @param	state
	 *				True se houver alteracoes no sistema de ficheiros.
	 *				False se as alteracoes ja foram guardadas.
	 */
	public void setState(Boolean state) {
		_changesMade = state;
	}

	/**
	 * Ordena a lista de utilizadores e lista os utilizadores com as suas caracteristicas.
	 *
	 * @return	String com as informações sobre os utilizadores.
	 */
	public String listUsers() {
		String res = "";

		Collections.sort(_users, new Comparator<User>() {
            @Override
            public int compare(User  user1, User  user2) {
                return  user1.getUsername().compareTo(user2.getUsername());
          }
    	});

    	for(User user : _users) 
    		res += user.listUser() + "\n";

    	// retorna a string mas sem o "\n" final resultante do ciclo for
    	return res.substring(0, res.length() - 1);
	}

	/**
	 * Cria um novo ficheiro (vazio ou ja com conteudo incluido) no diretorio em questao.
	 *
	 * @param 	name
	 *				Nome do novo ficheiro.
	 * @param	owner
	 *				Dono do novo ficheiro.
	 * @param	permission
	 *				Permissao publica(false) ou privada(true) do novo ficheiro.
	 * @param	content
	 *				Conteudo do novo ficheiro (pode estar vazio).
	 *
	 * @throws 	AccessDeniedException se o utilizador não tiver permissões para criar um
	 *					criar um ficheiro neste diretório.
	 * @throws 	EntryExistsException se já existir uma entrada com o mesmo nome no diretório.
	 */
	public void addFile(String name, boolean permission, String content)
			throws AccessDeniedException, EntryExistsException {

		if (_currentDirectory.haveAccess(_currentUser)) {

			if (_currentDirectory.getAll().containsKey(name)) 
				throw new EntryExistsException(name);

			File file = new File(name, _currentDirectory, _currentUser, permission, content);
			_currentDirectory.getFiles().put(name, file);
			_changesMade = true;
		}
	}

	/**
	 * Cria um novo directorio filho e adiciona-o ao diretorio atual.
	 *
	 * @param 	name
	 *				Nome do novo diretorio filho.
	 * @param	permission
	 *				Permissao publica(false) ou privada(true) do novo diretorio filho.
	 *
	 * @throws 	EntryExistsException se já existir uma entrada com o mesmo nome.
	 * @throws 	AccessDeniedException se o utilizador não tiver permissão para adicionar
	 *				um diretorio ao diretorio atual.
	 */
	public void addDirectory(String name, boolean permission)
			throws EntryExistsException, AccessDeniedException {

		if (_currentDirectory.haveAccess(_currentUser)) {

			if (_currentDirectory.getAll().containsKey(name))
				throw new EntryExistsException(name);

			Directory dir = new Directory(name, _currentDirectory, _currentUser, permission);
			_currentDirectory.getDirectories().put(name, dir);
			_changesMade = true;
		}
	}

	/**
	 * Verifica se o ficheiro file existe no diretorio atual, caso exista insere o texto indicado
	 * no conteudo do ficheiro.
	 *
	 * @param 	file
	 *				Nome do ficheiro a inserir o conteudo.
	 * @param 	text
	 *				Conteudo a adicionar ao ficheiro.
	 *
	 * @throws 	IsNotFileException se a entrada indicada for um diretório.
	 * @throws	EntryUnknownException se a entrada indicada não existir no diretório.
	 * @throws 	AccessDeniedException se o utilizador não tiver permissões para escrever no ficheiro.
	 */
	public void writeFile(String file, String text)
			throws IsNotFileException, EntryUnknownException, AccessDeniedException {

		if (_currentDirectory.getDirectories().containsKey(file)) 
			throw new IsNotFileException(file);

		if (_currentDirectory.getFiles().containsKey(file)) {

			if (_currentDirectory.getFiles().get(file).haveAccess(_currentUser))
				_currentDirectory.getFiles().get(file).addContent(text);
				_changesMade = true;

		} else 
			throw new EntryUnknownException(file);
	}

	/**
	 * Verifica se o ficheiro existe no diretorio atual, caso exista retorna o conteudo do ficheiro.
	 *
	 * @param	file
	 *				Nome do ficheiro a abrir.
	 *
	 * @throws 	IsNotFileException se a entrada indicada for um diretório.
	 * @throws  EntryUnknownException se a entrada indicada não existir no diretório.
	 *
	 * @return	Conteudo do ficheiro file.
	 */
	public String viewFile(String file)
			throws IsNotFileException, EntryUnknownException {

		if (_currentDirectory.getDirectories().containsKey(file)) 
			throw new IsNotFileException(file);

		if (_currentDirectory.getFiles().containsKey(file)) 
			return _currentDirectory.getFiles().get(file).getContent();

		else 
			throw new EntryUnknownException(file);
	}

	/**
	 * Verifica se a entrada existe no diretorio atual, caso exista e o utilizador atual
	 * tenha permissoes para tal, remove a entrada do directorio.
	 *
	 * @param	entryName
	 *				Nome da entrada a ser removida.
	 *
	 * @throws	EntryUnknownException se a entrada não existir no diretório atual.
	 * @throws 	IllegalRemovalException se a entrada for um dos diretórios especiais.
	 * @throws 	AccessDeniedException se o utilizador atual não tiver permissões para
	 *			remover a entrada (inclui o acesso ao diretorio onde se encontra).
	 */
	public void removeEntry(String entryName)
			throws EntryUnknownException, IllegalRemovalException, AccessDeniedException {

		Entry entry = _currentDirectory.getEntry(entryName);

		if (entryName.equals(".") || entryName.equals(".."))
			throw new IllegalRemovalException();

		if (_currentDirectory.haveAccess(_currentUser) && entry.haveAccess(_currentUser)) {

			if (_currentDirectory.getFiles().containsKey(entryName))
				_currentDirectory.getFiles().remove(entryName);

			else 
				_currentDirectory.getDirectories().remove(entryName);

			_changesMade = true;
		}
	}

	/**
	 * Verifica a existência da entrada no diretorio atual e do utilizador no sistema de 
	 * ficheiros, caso o utilizador actual do fSystem tenha permissoes para tal altera
	 * as permissoes da entrada indicada.
	 *
	 * @param	entryName
	 *				Nome da entrada a ser removido.
	 * @param	username
	 *				Username do novo dono da entrada.
	 *
	 * @throws	AccessDeniedException se o utilizador atual não tiver permissões para
	 *			alterar as permissões da entrada.
	 * @throws	UserUnknownException se o utilizador indicado não existir.
	 * @throws	EntryUnknownException se a entrada indicada não existir.
	 */
	public void changeEntryOwner(String entryName, String username)
			throws AccessDeniedException, UserUnknownException, EntryUnknownException {

		User user = getUser(username);
		Entry entry = _currentDirectory.getEntry(entryName);

		if (entry.haveAccess(_currentUser))
			entry.setOwner(user);
			_changesMade = true;
	}

	/**
	 * Verifica se a entry existe no directorio atual, se existir e caso o utilizador tenha
	 * permissoes para tal, modifica as permissoes da entrada para a permissão indicada.
	 *
	 * @param		entryName
	 *					Nome da entrada a modificar.
	 * @param		permission
	 * 					Nova permissao da entrada.
	 *
	 * @throws 	EntryUnknownException se a entrada indicada não existir no diretorio atual.
	 * @throws 	AccessDeniedException se o utilizador não tiver permissões para mudar a 
	 *			permissão da entrada indicada.
	 */
	public void changePermission(String entryName, boolean permission)
			throws EntryUnknownException, AccessDeniedException {

		Entry entry = _currentDirectory.getEntry(entryName);

		if (entry.haveAccess(_currentUser))
			entry.setPermission(permission);
			_changesMade = true;
	}
}
