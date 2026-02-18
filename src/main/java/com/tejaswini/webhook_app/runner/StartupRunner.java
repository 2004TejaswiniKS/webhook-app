package com.tejaswini.webhook_app.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.tejaswini.webhook_app.service.WebhookService;

@Component
public class StartupRunner implements CommandLineRunner{

	
	  private final WebhookService webhookService;

	    public StartupRunner(WebhookService webhookService) {
	        this.webhookService = webhookService;
	    }
	    
	@Override
	public void run(String... args) {
		 webhookService.executeFlow();
	}

}
