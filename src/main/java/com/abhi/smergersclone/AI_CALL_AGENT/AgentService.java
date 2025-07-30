package com.abhi.smergersclone.AI_CALL_AGENT;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType; // ✅ CORRECT (v5.x)
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
@Service
public class AgentService {

    @Value("${elevenlabs.api.key}")
    private String elevenApiKey;

    @Value("${elevenlabs.voice.id}")
    private String voiceId;

    public String getInitialTwiml() {
        return """
                <Response>
                    <Say voice="alice">Hi, I'm your AI assistant. What would you like to know?</Say>
                    <Record action="/call/process" method="POST" maxLength="15" />
                </Response>
                """;
    }

    public String processUserSpeech(String recordingUrl) throws IOException {
        byte[] audioBytes = Request.get(recordingUrl + ".mp3").execute().returnContent().asBytes();

        // 1. Transcribe audio (simulate)
        String transcribed = "What is the weather today?"; // Use ElevenLabs STT or Whisper here

        // 2. Generate AI reply (dummy)
        String aiReply = "Today it's sunny and 25 degrees Celsius.";

        // 3. Convert AI reply to speech (ElevenLabs TTS)
        byte[] speechAudio = synthesizeSpeech(aiReply);

        // Optional: Save to S3/local/public URL

        // 4. TwiML with URL to generated speech (dummy fallback)
        return """
        <Response>
            <Say voice="alice">Hi, I'm your AI assistant. What would you like to know?</Say>
            <Record action="/call/process" method="POST" maxLength="15" />
        </Response>
        """;

    }

    private byte[] synthesizeSpeech(String text) throws IOException {
        JSONObject payload = new JSONObject();
        payload.put("text", text);
        payload.put("model_id", "eleven_monolingual_v1");
        payload.put("voice_settings", new JSONObject().put("stability", 0.5).put("similarity_boost", 0.75));

        return Request.post("https://api.elevenlabs.io/v1/text-to-speech/" + voiceId)
                .addHeader("xi-api-key", elevenApiKey)
                .bodyString(payload.toString(), ContentType.APPLICATION_JSON)
                .execute().returnContent().asBytes();
    }
}
