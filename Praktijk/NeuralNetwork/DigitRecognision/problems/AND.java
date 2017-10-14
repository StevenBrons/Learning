
class AND extends LogicGate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean gate(boolean a, boolean b) {
		return a && b;
	}
	
	@Override
	public String toString() {
		return "AND";
	}
	
}