package hello;

class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1550591317965257967L;

	public UserNotFoundException(String lastName) {
		super("could not find user '" + lastName + "'.");
	}
}