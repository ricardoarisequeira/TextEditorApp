package poof.core;

import poof.textui.exception.EntryUnknownException;

/////////////////////////////////
//////Requisito Extra///////////
/////////////////////////////////

/**
 * Subclasse que permite procurar um ficheiro num diretório e nos seus
 * sub diretórios.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class SearchFile extends DirectoryVisitor {

	/** Nome do ficheiro que se pretende encontrar. */
	private String _filename;

	/** Ficheiro que se pretende encontrar. */
	private File _file;

	/** 
	 * Construtor da subclasse com a particularidade de receber o nome do ficheiro
	 * que se pretende encontrar no diretório.
	 *	 
	 * @param directory
	 *			Diretório onde começa a procura do ficheiro.
	 * @param filename
	 *			 Nome do ficheiro que se quer encontrar.
	 */
	public SearchFile(Directory directory, String filename) {
		super(directory);
		_filename = filename;
	}

	/**
	 * Atualiza a variável _file, atribuindo ao primeiro ficheiro encontrado
	 * com o nome pretendido (se for encontrado).
	 *
	 * @param directory
	 *				Diretório onde se está a procurar o ficheiro.
	 */
	protected void operator(Directory directory) {
		if (directory.getFiles().containsKey(_filename) && _file == null)
			_file = directory.getFiles().get(_filename);
	}

	/**
	 * Procura o ficheiro com o nome indicado tanto no diretório também indicado
	 * como, se não for encontrado, nos sub diretórios do mesmo.
	 *
	 * @throws	EntryUnknownException se o ficheiro não for encontrado.
	 *
	 * @return	String com o caminho atual do ficheiro, se for encontrado.
	 */
	public String execute() throws EntryUnknownException {

		visit(getDirectory());

		if (_file != null)
			return _file.getAbsolutePath();

		throw new EntryUnknownException(_filename);
	}
}