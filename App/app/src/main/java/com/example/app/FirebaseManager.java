package com.example.app;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseManager {

    // Single instance of Firestore
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseManager() {}
    public static CompletableFuture<List<Crop>> getCrops() {
        CompletableFuture<List<Crop>> future = new CompletableFuture<>();

        db.collection("crops")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Crop> crops = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Crop crop = doc.toObject(Crop.class);
                        if (crop != null) {
                            crops.add(crop);
                        }
                    }
                    future.complete(crops);
                })
                .addOnFailureListener(future::completeExceptionally);

        return future;
    }

    public static CompletableFuture<Void> addCrop(Crop crop) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        db.collection("crops")
                .add(crop)
                .addOnSuccessListener(documentReference -> future.complete(null))
                .addOnFailureListener(future::completeExceptionally);

        return future;
    }
}
