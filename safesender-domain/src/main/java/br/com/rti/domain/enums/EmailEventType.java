package br.com.rti.domain.enums;

public enum EmailEventType {

	PRO( "PROCESSED" ),
	DEL( "DELIVERED" ),
	OPE( "OPENED" ),
	BOU( "BOUNCE" ),
	DIS( "DISCARDED" ),
	ERR( "ERROR" ),
	;

	String description;

	EmailEventType( String description ) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
