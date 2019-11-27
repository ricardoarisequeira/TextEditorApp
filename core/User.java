package poof.core;

import java.io.Serializable;

/**
 * Classe que representa um utilizador. Estes têm um nome, identificador e um
 * diretorio principal. Têm menos capacidades relativamente ao super utilizador
 * mas ainda assim conseguem ver qualquer entrada do sistema de ficheiros onde
 * se encontra. Editar entradas é que está limitado às suas permissoes.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class User implements Serializable {

	/** Identificador do utilizador */
	private String _username;

	/** Nome do utilizador */
	private String _name;

	/** Directorio principal do utilizador */
	private Directory _mainDirectory;

	/**
	 * Construtor de um utilizador com inicializacao de atributos.
	 *
	 * @param 	username
	 *				Identificador do novo utilizador.
	 * @param 	name
	 *				Nome do novo utilizador.
	 * @param	home
	 *				Diretorio do novo utilizador.
	 */
	public User (String username, String name, Directory home) {
		_username = username;
		_name = name;
		_mainDirectory = home;
	}

	/**
	 * Devolve o nome do utilizador.
	 *
	 * @return String correspondente ao nome do utilizador.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Devolve o identificador do utilizador.
	 *
	 * @return String correspondente ao identificador do utilizador.
	 */
	public String getUsername() {
		return _username;
	}

	/**
	 * Devolve o diretório home/principal do utilizador.
	 *
	 * @return O diretorio principal do utilizador.
	 */
	public Directory getMainDirectory() {
		return _mainDirectory;
	}

	/**
	 * Atribui um diretório principal ao utilizador.
	 *
	 * @param 	directory
	 * 				Novo diretório principal do utilizador.		
	 */
	public void setMainDirectory(Directory directory) {
		_mainDirectory = directory;
	}

	/**
	 * Lista as características do utilizador incluindo o identificador, nome e diretório principal.
	 *
	 * @return Uma string que retorna as informacoes do utilizador.
	 */
	public String listUser() {
		return "" + _username + ":" + _name + ":" + _mainDirectory.getAbsolutePath();
	}

	/**
	 * Permite identificar um utilizador como super utilizador.
	 *
	 * @return Valor booleano: true se for o super utilizador, false caso contrário.
	 */
	public boolean isRoot() {
		return _username.equals("root");
	}
}
