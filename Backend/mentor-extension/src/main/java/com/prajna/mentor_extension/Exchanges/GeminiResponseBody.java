package com.prajna.mentor_extension.Exchanges;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponseBody {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Candidate {
    private Content content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Content {
    private List<Part> parts;
    private String role;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Part {
    private String text;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class SafetyRating {
    private String category;
    private String probability;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UsageMetadata {
    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;
}