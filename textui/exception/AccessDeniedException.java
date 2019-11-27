package poof.textui.exception;

import pt.utl.ist.po.ui.InvalidOperation;

/**
 * Thrown when a restricted operation is attempted.
 */
public class AccessDeniedException extends InvalidOperation {
	/** Class serial number (serialization). */
	private static final long serialVersionUID = 201408261552L;

	/** Invalid user id. */
	private final String _username;

	/**
	 * @param name
	 */
	public AccessDeniedException(String name) {
		_username = name;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "O utilizador '" + _username + "' não tem permissão para realizar a operação.";
	}
}
