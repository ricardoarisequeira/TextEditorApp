package poof.core;

/**
 * Classe que representa um ficheiro. ESta é uma subclasse da classe Entry
 * e por isso tem nome, tamanho, dono, diretorio pai e permissao. Para alem disso,
 * apresenta um conteudo que pode ser escrito por utilizadores (que tenham permissoes
 * para tal).
 *
 * @author Grupo 50 - Programacao com Objetos (1ºSemestre 2014)
 */

public class File extends Entry {

	/** Conteudo guardado no ficheiro. */
	private String _content = "";

	/**
	 * Construtor de um ficheiro e inicializacao dos seus atributos com base
	 * no construtor da superclasse Entry.
	 *
	 * @param 	fileName
	 *				Nome do novo ficheiro.
	 * @param	parentDir
	 *				Diretorio onde se encontra o novo ficheiro.
	 * @param 	owner
	 *				Dono do novo ficheiro.
	 * @param	permission
	 *				Permissao publica(false) ou privada(true) do novo ficheiro.
	 * @param	content
	 *				Conteudo do novo ficheiro.
	 */
	public File(String filename, Directory parentDir, User owner, boolean permissions, String content) {
		super(filename, owner, parentDir, permissions);

		// Verifica se o ficheiro é iniciado com conteudo ou não (a adição do "\n" depende disso)
		_content = content.equals("") ? content : content + "\n";
	}

	/**
	 * Devolve o texto que consiste no conteúdo do ficheiro.
	 *
	 * @return String representante do conteúdo do ficheiro.
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Método responsável por adicionar conteudo a um ficheiro.
	 *
	 * @param 	text
	 *				Texto que vai ser inserido no conteúdo do ficheiro.
	 */
	public void addContent(String text) {
		_content += text + "\n";
	}

	/**
	 * Calcula o tamanho de um ficheiro (depende do seu conteúdo).
	 *
	 * @return Inteiro correspondente ao tamanho do ficheiro.
	 */
	public int getSize() {
		return _content.length();
	}

	/**
	 * Lista as características do ficheiro como a permissão, dono, tamanho e nome.
	 *
	 * @param 	name
	 *				Nome do ficheiro. Parametro necessário devido às caracteristicas
	 *				observadas neste mesmo método implementado na subclasse Directory.
	 *
	 * @return	A string que lista as caracteristicas do ficheiro.
	 */
	public String listEntry(String name) {
		String permission = this.getPermission() ? "-" : "w";
		return "- " + permission + " " + this.getOwner().getUsername() + " " + this.getSize() + " " + name;
	}
}
