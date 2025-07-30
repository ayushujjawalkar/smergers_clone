package com.abhi.smergersclone.AI_CALL_AGENT;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.voice.Record;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/voice")
public class VoiceWebhookController {

    @GetMapping
    public void handleVoice(HttpServletResponse response) throws IOException {
        VoiceResponse twiml = new VoiceResponse.Builder()
                .say(new Say.Builder("Hello! How can I help you today?")
                        .voice(Say.Voice.ALICE)
                        .build())
                .record(new Record.Builder()
                        .action("/voice/process")
                        .method(com.twilio.http.HttpMethod.POST)
                        .timeout(5)
                        .maxLength(30)
                        .build())
                .build();

        response.setContentType("application/xml");
        response.getWriter().write(twiml.toXml());
    }
}
