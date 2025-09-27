package com.example.app;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FirebaseManager {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static List<Crop> getCrops() {
        List<Crop> crops = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        db.collection("crops")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Crop crop = doc.toObject(Crop.class);
                        if (crop != null) crops.add(crop);
                    }
                    latch.countDown();
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    latch.countDown();
                });

        try {
            latch.await(); // wait until Firebase responds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return crops;
    }

    public static void addCrop(Crop crop) {
        if (crop == null) return;
        db.collection("crops")
                .add(crop)
                .addOnSuccessListener(documentReference ->
                        System.out.println("Crop added: " + crop.getType()))
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }
}
