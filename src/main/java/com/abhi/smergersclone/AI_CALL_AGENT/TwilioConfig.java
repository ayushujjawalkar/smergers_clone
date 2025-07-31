package com.abhi.smergersclone.AI_CALL_AGENT;

import com.twilio.Twilio;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    // Fallback values from application.properties
    @Value("${twilio.account.sid:}")
    private String propAccountSid;

    @Value("${twilio.auth.token:}")
    private String propAuthToken;

    @PostConstruct
    public void initTwilio() {
        // Try loading from .env file first
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        // Get credentials with priority: .env > system env > properties
        String accountSid = firstNonNull(
                dotenv.get("TWILIO_ACCOUNT_SID"),
                System.getenv("TWILIO_ACCOUNT_SID"),
                propAccountSid
        );

        String authToken = firstNonNull(
                dotenv.get("TWILIO_AUTH_TOKEN"),
                System.getenv("TWILIO_AUTH_TOKEN"),
                propAuthToken
        );

        if (accountSid == null || authToken == null) {
            throw new IllegalStateException("""
                Twilio credentials not found in:
                1. .env file
                2. System environment
                3. application.properties
                """);
        }

        Twilio.init(accountSid, authToken);
        logger.info("Twilio initialized with SID: {}...", accountSid.substring(0, 5));
    }

    private String firstNonNull(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return null;
    }
}