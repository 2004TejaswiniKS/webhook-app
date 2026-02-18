package com.tejaswini.webhook_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookResponse {

	 @JsonProperty("webhook")
	    private String webhookUrl;   // variable name can stay same

	    @JsonProperty("accessToken")
	    private String accessToken;

	    public String getWebhookUrl() {
	        return webhookUrl;
	    }

	    public void setWebhookUrl(String webhookUrl) {
	        this.webhookUrl = webhookUrl;
	    }

	    public String getAccessToken() {
	        return accessToken;
	    }

	    public void setAccessToken(String accessToken) {
	        this.accessToken = accessToken;
	    }
    
}
