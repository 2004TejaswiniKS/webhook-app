package com.tejaswini.webhook_app.service;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tejaswini.webhook_app.dto.*;

@Service
public class WebhookService {
 
	private final RestTemplate restTemplate;
	
	public WebhookService(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	public void executeFlow() {
		try {
			
			String generateUrl="https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
			
			WebhookRequest request = new WebhookRequest(
			        "Tejaswini R",
			        "22CS1048",
			        "tejaswini@email.com"
			);
			ResponseEntity<WebhookResponse> response =
			        restTemplate.postForEntity(generateUrl,request, WebhookResponse.class);

			WebhookResponse body = response.getBody();

			if (body == null) return;

			String webhookUrl = body.getWebhookUrl();
			String accessToken = body.getAccessToken();

			String finalSqlQuery = getFinalSqlQuery();

			sendFinalQuery(webhookUrl, accessToken, finalSqlQuery);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
		 private String getFinalSqlQuery() {

		        return """
		                SELECT 
		                    d.DEPARTMENT_NAME,
		                    ROUND(AVG(TIMESTAMPDIFF(YEAR, e.DOB, CURDATE())), 2) AS AVERAGE_AGE,
		                    SUBSTRING_INDEX(
		                        GROUP_CONCAT(
		                            DISTINCT CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME)
		                            ORDER BY e.EMP_ID
		                            SEPARATOR ', '
		                        ),
		                        ', ',
		                        10
		                    ) AS EMPLOYEE_LIST
		                FROM DEPARTMENT d
		                JOIN EMPLOYEE e 
		                    ON d.DEPARTMENT_ID = e.DEPARTMENT_ID
		                JOIN PAYMENTS p 
		                    ON e.EMP_ID = p.EMP_ID
		                WHERE p.AMOUNT > 70000
		                GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME
		                ORDER BY d.DEPARTMENT_ID DESC
		                """;
		    }

		 private void sendFinalQuery(
			        String webhookUrl,
			        String accessToken,
			        String finalQuery
			) {

			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);

			    // 🔥 NO "Bearer "
			    headers.set("Authorization", accessToken);

			    FinalQueryRequest request = new FinalQueryRequest(finalQuery);

			    HttpEntity<FinalQueryRequest> entity =
			            new HttpEntity<>(request, headers);

			    ResponseEntity<String> response =
			            restTemplate.postForEntity(
			                    webhookUrl,
			                    entity,
			                    String.class
			            );

			    System.out.println("Response: " + response.getBody());
			    System.out.println("Submission Completed Successfully!");
			}


}
