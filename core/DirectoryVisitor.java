package poof.core;

/////////////////////////////////
//////Requisito Extra///////////
/////////////////////////////////

/**
 * Super classe que permite explorar todo o conteudo de um diretorio.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public abstract class DirectoryVisitor {

	/** Diretório onde vai ser aplicado o comando pretendido. */
	private Directory _directory;

	/** 
	 * Construtor da super classe.
	 *	 
	 * @param 	directory
	 *				Diretório onde vai ser aplicado o comando pretendido.
	 */
	public DirectoryVisitor(Directory directory) {
		_directory = directory;
	}

	/**
	 * Devolve o diretório onde vai ser aplicado o comando pretendido.
	 *
	 * @return Diretório onde vai ser aplicado o comando pretendido
	 */
	public Directory getDirectory() {
		return _directory;
	}

	/**
	 * Método responsável por operar no diretório indicado como também nos sub diretórios.
	 *
	 * @param directory
	 *				Diretório alvo da operação pretendida.
	 */
	public void visit(Directory directory) {

		operator(directory);

		for (String name : directory.getDirectories().keySet()) {

			if (!(name.equals(".") || name.equals("..")))

				visit(directory.getDirectories().get(name));
		}
	}

	/**
	 * Método que permite às sub classes usar o método visit aplicando a operação pertencente.
	 */
	protected abstract void operator(Directory directory);
}