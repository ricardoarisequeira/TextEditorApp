package poof.core;

import java.io.Serializable;
import poof.textui.exception.AccessDeniedException;

/**
 * Classe abstrata que representa uma entrada no sistema de ficheiros,
 * esta pode ser um ficheiro ou um diretorio, e por isso é uma superclasse
 * estendida pelas subclasses Directory e File.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public abstract class Entry implements Serializable {

	/** Nome da entrada. */
	private String _name;

	/** Dono da entrada. */
	private User _owner;

	/** Permissao publica(false) ou privada(true) da nova entrada. */
	private boolean _permission;

	/** Diretorio pai onde esta contida a entrada. */
	private Directory _parentDirectory;

	/**
	 * Construtor de uma nova entrada.
	 *
	 * @param 	name
	 *				Nome da nova entrada.
	 * @param	owner
	 *				Dono da nova entrada.
	 * @param	parentDir
	 *				Diretorio pai da (e onde se encontra a) nova entrada.
	 * @param	permission
	 *				Permissao publica(false) ou privada(true) do novo diretorio.
	 */
	public Entry (String name,  User owner, Directory parentDir, boolean permission) {
		_name = name;
		_permission = permission;
		_owner = owner;
		_parentDirectory = parentDir;
	}

	/**
	 * Devolve o nome da entrada.
	 *
	 * @return Nome da entrada.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Devolve o dono da entrada.
	 *
	 * @return Dono da entrada.
	 */
	public User getOwner() {
		return _owner;
	}

	/**
	 * Devolve a permissão da entrada.
	 * 
	 * @return O tipo de permissao da entrada: público(false) ou privado(true).
	 */
	public boolean getPermission() {
		return _permission;
	}

	/**
	 * Devolve o diretório pai da entrada.
	 *
	 * @return O diretorio pai onde esta contida a entrada.
	 */
	public Directory getParentDirectory() {
		return _parentDirectory;
	}

	/**
	 * Devolve o tamanho da entrada (será diferente calcular nos diretórios e nos ficheiros)
	 *
	 * @return O inteiro correspondente ao tamanho da entrada.
	 */
	public abstract int getSize();

	/**
	 * Lista as características da entrada (dependente do tipo de entrada e por isso abstracto).
	 *
	 * @param 	name
	 *				Nome da entrada (necessário devido aos diretórios especiais)
	 *
	 * @return 	Uma string com as características da entrada.
	 */
	public abstract String listEntry(String name);

	/**
	 * Muda o nome da entrada.
	 *
	 * @param 	name
	 * 				Novo nome para a entrada.		
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * Muda o dono da entrada.
	 *
	 * @param 	newOwner
	 * 					Novo dono da entrada.		
	 */
	public void setOwner(User newOwner) {
		_owner = newOwner;
	}

	/**
	 * Altera a permissao da entrada.
	 *
	 * @param	newPermission
	 *						Novo tipo de permissao para a entrada.
	 */
	public void setPermission(boolean newPermission) {
		_permission = newPermission;
	}

	/**
	 * Muda o diretorio pai onde esta contida a entrada.
	 *
	 * @param 	newParentDirectory
	 * 					Novo diretorio pai.		
	 */
	public void setParentDirectory(Directory newParentDirectory) {
		_parentDirectory = newParentDirectory;
	}

	/**
	 * Verifica se o utilizador é dono da entrada, se é o root ou se a entrada é pública.
	 *
	 * @param	requestUser
	 *				Utilizador para ser testado
     *
	 * @throws 	AccessDeniedException se o utilizador não tiver permissão nesta entrada.
	 *
	 * @return	True se o utilizador for dono da entrada ou for o root, caso contrário,
	 *			lança uma excepção que avisa que o acesso foi negado.
	 */
	public boolean haveAccess(User requestUser) throws AccessDeniedException {

		if (!getPermission() || 
			_owner.getUsername().equals(requestUser.getUsername()) ||
			requestUser.isRoot())

			return true;

		else
			throw new AccessDeniedException(requestUser.getUsername());
	}		 

	/**
	 * Devolve o caminho atual da entrada (hierarquia de diretórios onde se encontra).
	 *
	 * @return String com o caminho atual da entrada.
	 */
	public String getAbsolutePath() {
		if (getName().equals("/"))
			return "/";

		if (getName().equals("home"))
			return _parentDirectory.getAbsolutePath() + getName();

		else
			return _parentDirectory.getAbsolutePath() + "/" + getName();
	}
}
