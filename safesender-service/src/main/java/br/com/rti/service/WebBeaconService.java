package br.com.rti.service;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class WebBeaconService {

	private static final String IMG_TEMPLATE = "<span><img></img></span>";
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern( "YYYYMMddHHmmss" );

	public String applyWebBeaconOpenedTrack( String targetUrl, String uniqueId, String originalBody ) throws Exception {

		if ( originalBody == null || originalBody.trim().isEmpty() ) {
			return originalBody;
		}

		String newBody = String.copyValueOf( originalBody.toCharArray() );

		boolean isHtml = newBody.contains( "<html" ) && newBody.contains( "</html>" );
		boolean hasBody = newBody.contains( "<body" ) && newBody.contains( "</body>" );

		String wBeaconUrl = getWebBeacon( targetUrl, uniqueId );
		String wBeaconAlt = "Imagem to identify sender " + uniqueId.substring( 0, 5 );

		if ( hasBody ) {
			originalBody = newBody.replace( "</body>", String.format( IMG_TEMPLATE, wBeaconUrl, wBeaconAlt ) + "</body>" );
		} else if ( isHtml ) {
			originalBody = newBody.replace( "</html>", "</html>" );
		}

		return newBody;
	}

	private String getWebBeacon( String targetUrl, String uniqueId ) {
		// TODO Auto-generated method stub
		return null;
	}
}
