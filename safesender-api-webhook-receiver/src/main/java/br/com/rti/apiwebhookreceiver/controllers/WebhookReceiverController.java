package br.com.rti.apiwebhookreceiver.controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class WebhookReceiverController {
	
	@Produce("direct:webhookReceivedToSave")
	private ProducerTemplate producerTemplate;

    @PostMapping(value = "/webhook", headers = {"content-encoding=gzip","content-type=application/x-ndjson"})
    public void receberRequisicao(HttpServletRequest request, HttpServletResponse response) {
    	log.info("INICIO /webhook");
        try {
        	
            byte[] requestBody = request.getInputStream().readAllBytes();

            // Descompactar dados GZIP
            ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);

            // Ler o conteúdo descompactado como texto legível
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
            	log.info(line);
                producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), line);
            }
            
            // Responder com sucesso
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            // Responder com erro de servidor
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        log.info("FIM /webhook");
    }

}
