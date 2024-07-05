package com.prajna.mentor_extension.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequestBody {
    private List<Content> contents;
    private List<SafetySetting> safetySettings;
    private GenerationConfig generationConfig;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Part {
            private String text;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SafetySetting {
        private String category;
        private String threshold;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenerationConfig {
        private List<String> stopSequences;
        private double temperature;
        private int maxOutputTokens;
        private double topP;
        private int topK;
        private String responseMimeType;
        private String responseSchema;
    }
}
