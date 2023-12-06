package br.com.rti.domain.enums;

public enum EmailEventStatus {

	NEW( "NEW" ),
	SEN( "SENT" ),
	CAN( "CANCEL" ),
	;

	String description;

	EmailEventStatus( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
