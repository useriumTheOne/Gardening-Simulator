package com.example.app;

import android.os.Build;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;

public class GeminiManager {

    public interface GeminiResultListener {
        void onSuccess(String response);
        void onFailure(Throwable t);
    }

    private static final String API_KEY = BuildConfig.API_KEY;

    public static void generateMessage(String prompt, GeminiResultListener listener) {
        GenerativeModel gm = new GenerativeModel("gemini-2.5-flash", API_KEY);        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder().addText(prompt).build();

        com.google.common.util.concurrent.ListenableFuture<com.google.ai.client.generativeai.type.GenerateContentResponse> future =
                model.generateContent(content);

        future.addListener(() -> {
            try {
                String result = future.get().getText();
                listener.onSuccess(result);
            } catch (Exception e) {
                listener.onFailure(e);
            }
        }, Runnable::run);
    }
}