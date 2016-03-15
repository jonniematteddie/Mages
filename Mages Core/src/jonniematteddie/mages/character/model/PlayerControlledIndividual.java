package jonniematteddie.mages.character.model;

public class PlayerControlledIndividual extends Individual {
	
	private static final long serialVersionUID = 8498816685303672560L;
	private int clientId;
	
	
	public int getClientId() {
		return clientId;
	}
	
	
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}
