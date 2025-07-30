package com.abhi.smergersclone.AI_CALL_AGENT;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/call")
public class CallController {

    @Value("${twilio.phone.number}")
    private String fromNumber;

    private final AgentService agentService;

    public CallController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/start")
    public String startCall(@RequestParam String toNumber) throws Exception {
        Call call = Call.creator(
                new PhoneNumber("+918120663735"),
                new PhoneNumber("+14027048249"),
                new URI("https://your-ngrok-url.ngrok.io/call/handle")
        ).create();
        return "Call started with SID: " + call.getSid();
    }

    @PostMapping("/handle")
    public void handleCall(HttpServletResponse response) throws IOException, java.io.IOException {
        String xml = agentService.getInitialTwiml();
        response.setContentType("application/xml");
        response.getWriter().write(xml);
    }

    @PostMapping("/process")
    public void processRecording(@RequestParam("RecordingUrl") String recordingUrl,
                                 HttpServletResponse response) throws IOException, java.io.IOException {
        String replyXml = agentService.processUserSpeech(recordingUrl);
        response.setContentType("application/xml");
        response.getWriter().write(replyXml);
    }


}