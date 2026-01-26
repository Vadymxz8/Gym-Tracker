package com.vadim.tkach.gym_tracker.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${resend.api-key}")
    private String apiKey;

    @Value("${resend.from}")
    private String from;

    @Value("${resend.from-name:Gym Tracker}")
    private String fromName;

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            String html = text.replace("\n", "<br/>");

            String body = """
            {
              "from": "%s <%s>",
              "to": ["%s"],
              "subject": "%s",
              "html": "%s"
            }
            """.formatted(
                    escape(fromName),
                    escape(from),
                    escape(to),
                    escape(subject),
                    escape(html)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 300) {
                log.error("Resend error {}: {}", response.statusCode(), response.body());
                throw new IllegalStateException("Resend failed");
            }

            log.info("Email sent to {}", to);

        } catch (Exception e) {
            log.error("Email send failed", e);
            throw new RuntimeException(e);
        }
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
