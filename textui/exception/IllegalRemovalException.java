package poof.textui.exception;

import pt.utl.ist.po.ui.InvalidOperation;

/**
 * Thrown when an attempt is made to remove "." or ".."
 */
public class IllegalRemovalException extends InvalidOperation {
	/** Class serial number (serialization). */
	private static final long serialVersionUID = 201408261552L;

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "Não é possível remover as entradas \".\" e \"..\".";
	}
}
