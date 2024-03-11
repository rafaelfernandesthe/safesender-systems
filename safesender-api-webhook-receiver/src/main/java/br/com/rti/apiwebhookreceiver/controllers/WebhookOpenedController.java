package br.com.rti.apiwebhookreceiver.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.rti.domain.dtos.restapi.EmailEventPmtaDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class WebhookOpenedController {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	
	private final static byte[] SINGLE_PIXEL_TRANSPARENT_SHIM_BYTES = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -128, 0, 0, -1,
			-1, -1, 0, 0, 0, 33, -7, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59 };
	
	@Produce("direct:webhookOpenedToSave")
	private ProducerTemplate producerTemplate;

	private static final String OPEN = "open";

	private static final String MONITORING_ENVID = "jj0122@202310250315277372ey6@u0001.wl001";

    @GetMapping(value = "/opened/{internalid}")
    public void emailOpened(@PathVariable("internalid") String internalid, HttpServletRequest request, HttpServletResponse response) {
    	
    	log.info("INICIO /webhook opened: {}", internalid);
        try {
        	EmailEventPmtaDto event = EmailEventPmtaDto.builder()
        			.timeLogged(SDF.format(new Date()))
        			.dlvType(OPEN)
        			.envId(internalid)
        			.build();
        	
        	if(!isMonitoring(event)) {
        		producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), event );
        	}
            
        } catch (Exception e) {
            log.error("EMAIL OPENED", e);
        } finally {
        	returnBlank(response);
        }
        
        log.info("FIM /webhook opened:{}", internalid);
    }
    
    private boolean isMonitoring(EmailEventPmtaDto event) {
    	if(MONITORING_ENVID.equals(event.getEnvId())) {
    		log.info("--MONITORING_ENVID OK--");
    		return true;
    	}
    		
		return false;
	}

	private void returnBlank(HttpServletResponse response) {
		InputStream in = new ByteArrayInputStream(SINGLE_PIXEL_TRANSPARENT_SHIM_BYTES);
		response.setContentType(MediaType.IMAGE_GIF_VALUE);
		try {
			IOUtils.copy(in, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
