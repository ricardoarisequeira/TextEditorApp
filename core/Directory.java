package poof.core;

import java.util.Map;
import java.util.TreeMap;

import poof.textui.exception.EntryUnknownException;

/**
 * Classe que representa um diretorio de trabalho. Esta é uma subclasse da classe Entry
 * e por isso tem nome, tamanho, dono, diretorio pai e permissao. Para alem disso, pode ter,
 * por exemplo, ficheiros e/ou diretorios contidos nele e por ai adiante.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class Directory extends Entry {

	/** Colecao de ficheiros contidos no diretorio. */
	private Map<String, File> _files = new TreeMap<String, File>();

	/** Colecao de diretorios contidos no diretorio. */
	private Map<String, Directory> _childDirectories = new TreeMap<String, Directory>();

	/** Constante referente ao tamanho (em bytes) de cada entrada contida no diretorio. */
	private static final int ENTRYCOST = 8;

	/**
	 * Construtor de um novo diretorio.
	 * 
	 * @param 	name
	 *				Nome do novo diretorio.
	 * @param 	parentDir
	 *				Diretorio pai do (e onde esta incluido o) novo diretorio a ser criado.
	 * @param	owner
	 *				Dono do novo diretorio.
	 * @param	permission
	 *				Permissao publica(false) ou privada(true) do novo diretorio.
	 */
	public Directory(String name, Directory parentDir, User owner, boolean permission) {
		
		// Criação do diretório
		super(name, owner, parentDir, permission);

		// Verifica se é o directorio base, se for, define-se como o seu proprio pai
		if (name.equals("/")) {
			parentDir = this;
			setParentDirectory(parentDir);
		}

		// Posicionamento dos diretórios especiais
		_childDirectories.put(".", this);
		_childDirectories.put("..", parentDir);
	}

	/**
	 * Devolve todos os diretórios contidos no diretório.
	 *
	 * @return Map com os diretórios filho do diretório.
	 */
	public Map<String, Directory> getDirectories() {
		return _childDirectories;
	}

	/**
	 * Devolve todos os ficheiros contidos no diretório.
	 *
	 * @return Map com ficheiros contidos no diretorio.
	 */
	public Map<String, File> getFiles() {
		return _files;
	}

	/**
	 * Devolve todos os ficheiros e diretórios contidos no diretório.
	 *
	 * @return Map que consiste na junção dos maps dos diretórios e dos ficheiros.
	 */
	public Map<String, Entry> getAll() {
		Map<String, Entry> all = new TreeMap<String, Entry>();
		all.putAll(_childDirectories);
		all.putAll(_files);
		return all;
	}

	/**
	 * Devolve a entrada especificada pelo nome, se se encontrar no diretório.
	 *
	 * @param 		name
	 *					Nome da entrada pretendida.
	 * 
	 * @throws 		EntryUnknownException se a entrada não existir.
	 *
	 * @return		A entrada pedida.
	 */
	public Entry getEntry(String name) throws EntryUnknownException {

		if (_files.containsKey(name)) 
			return (Entry) _files.get(name);

		if (_childDirectories.containsKey(name))
			return (Entry) _childDirectories.get(name);

		else 
			throw new EntryUnknownException(name);
	}

	/**
	 * Devolve o tamanho atual do diretório.
	 *
	 * @return Inteiro correspondente ao tamanho atual do diretório.
	 */
	public int getSize() {
		return getAll().size() * ENTRYCOST;
	}

	/**
	 * Lista as características do diretorio como a permissão, dono, tamanho e nome.
	 *
	 * @param 	name
	 *				Nome do diretorio (como está registado no sistema de ficheiros!).
	 *				A necessidade deste parâmetro está relacionada com a existência
	 *				dos diretorios "." e ".." que apenas possuem este nome no
	 *				diretorio em que se encontram, mas sendo referencias para diretorios
	 *				com um nome diferente e verdadeiro.
	 *
	 * @return	A string que lista as caracteristicas do diretorio.
	 */
	public String listEntry(String name) {
		String permission = getPermission() ? "-" : "w";
		return "d " + permission + " " + getOwner().getUsername() + " " + getSize() + " " + name;
	}

	/**
	 * Lista as características de todos os diretórios e ficheiros contidos no diretório.
	 *
	 * @return A string que lista as caracteristicas de todas as entradas contidas no diretório.
	 */
	public String listAllEntries() {
		String res = "";
		Map<String, Entry> entries = getAll();

		for (String name : entries.keySet()) {
			res += entries.get(name).listEntry(name) + "\n";
		}

		// retorna a string mas sem o "\n" final resultante do ciclo for
		return res.substring(0, res.length() - 1);
	}
}
