package poof.core;

/////////////////////////////////
//////Requisito Extra///////////
/////////////////////////////////

/**
 * Subclasse que permite calcular o tamanho total de um diretório
 * contando com o tamanho dos seus sub diretórios.
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class CalculateSize extends DirectoryVisitor {

	/** Tamanho total de um diretório. */
	private int _size;

	/** 
	 * Construtor da subclasse.
	 *	 
	 * @param 	directory
	 *				Diretório que se pretende saber o tamanho total.
	 */
	public CalculateSize(Directory directory) {
		super(directory);
	}

	/**
	 * Atualiza a variável _size, adicionando o tamanho do diretório que recebe.
	 *
	 * @param directory
	 *				Diretório que contribui para o tamanho do diretório pai.
	 */
	protected void operator(Directory directory) {
		_size += directory.getSize();
	}

	/**
	 * Executa o cálculo do tamanho do diretório que recebe (incluindo todas as entradas
	 * que se encontrem em sub diretórios.
	 *
	 * @return	Inteiro correspondente ao tamanho total do diretório indicado.
	 */
	public int execute() {
		visit(getDirectory());
		return _size;
	}
}