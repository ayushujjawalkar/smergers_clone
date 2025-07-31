package com.abhi.smergersclone.AI_CALL_AGENT;

import com.twilio.Twilio;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    @PostConstruct
    public void initTwilio() {
        // Load environment variables
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Look in project root
                .ignoreIfMissing()
                .load();

        String accountSid = dotenv.get("TWILIO_ACCOUNT_SID");
        String authToken = dotenv.get("TWILIO_AUTH_TOKEN");

        // Verify credentials
        if (accountSid == null || authToken == null) {
            logger.error("Twilio credentials missing! Check your .env file");
            throw new IllegalStateException("Twilio credentials not configured");
        }

        // Initialize Twilio
        try {
            Twilio.init(accountSid, authToken);
            logger.info("Twilio initialized successfully with SID: {}...",
                    accountSid.substring(0, 5));
        } catch (Exception e) {
            logger.error("Twilio initialization failed", e);
            throw e;
        }
    }
}