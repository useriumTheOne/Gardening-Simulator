package com.example.app;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
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

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        db.collection("users")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        User user = doc.toObject(User.class);
                        if (user != null) users.add(user);
                    }
                    users.sort(Comparator.comparingDouble(User::getMoney).reversed());
                    latch.countDown();
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    latch.countDown();
                });

        try {
            latch.await(); // wait for Firebase
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void updateUser(User user) {
        if (user == null || user.getName() == null) return;
        DocumentReference docRef = db.collection("users").document(user.getName());
        docRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                docRef.set(user)
                        .addOnSuccessListener(aVoid -> System.out.println("User updated: " + user.getName()))
                        .addOnFailureListener(Throwable::printStackTrace);
            } else {
                docRef.set(user)
                        .addOnSuccessListener(aVoid -> System.out.println("User created: " + user.getName()))
                        .addOnFailureListener(Throwable::printStackTrace);
            }
        }).addOnFailureListener(Throwable::printStackTrace);
    }
}
