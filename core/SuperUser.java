package poof.core;

/**
 * Classe que representa um super utilizador. Este tem mais capacidades que um
 * utilizador normal já que consegue criar novos utilizadores bem como aceder
 * a qualquer entrada do sistema de ficheiros.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class SuperUser extends User {

	/**
	 * Construtor de um super utilizador com base no construtor
	 * de um utilizador normal.
	 *
	 * @param home
	 *			Diretório home que vai ser atribuido ao super utilizador.
	 */
	public SuperUser(Directory home) { 
		super("root", "Super User", home);
		home.setOwner(this);
	}

	/**
	 * Cria um novo utilizador e atribui lhe o diretorio home,
	 * possibilidade apenas ao alcance de um super utilizador.
	 *
	 * @param 	username
	 *				Identificador do novo utilizador.
	 * @param 	name
	 *				Nome do novo utilizador.
	 * @param	userHome
	 *				Diretorio home do novo utilizador.
	 *
	 * @return	O novo utilizador que foi criado.
	 */
	public User createNewUser(String username, String name, Directory userHome) {
		User newUser = new User(username, name, userHome);
		userHome.setOwner(newUser);
		return newUser;
	}
}
