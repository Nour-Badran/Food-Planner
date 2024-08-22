package com.example.foodplanner.Model.Repository.Repository;

import android.util.Log;

import com.example.foodplanner.Model.Repository.MealDB.MealEntity;
import com.example.foodplanner.Model.Repository.PlanDB.Days.Monday;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private FirebaseFirestore firestore;
    public DataRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void saveToFirebase(List<MealEntity> meals, String email, String esmElFolder) {
        // Create a reference to the user's meals collection
        CollectionReference mealsRef = firestore.collection("users").document(email).collection(esmElFolder);

        // First, delete all existing documents in the collection
        mealsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            // Create a batch for deleting all existing documents
            WriteBatch deleteBatch = firestore.batch();

            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                deleteBatch.delete(document.getReference());
            }

            // Commit the delete batch
            deleteBatch.commit().addOnSuccessListener(aVoid -> {
                // After successful deletion, write the new documents
                WriteBatch saveBatch = firestore.batch();

                for (MealEntity meal : meals) {
                    DocumentReference mealRef = mealsRef.document(); // Firestore generate a unique ID
                    saveBatch.set(mealRef, meal);
                }

                // Commit the save batch
                saveBatch.commit()
                        .addOnSuccessListener(aVoid1 -> {
                            Log.d("DataRepository", "Meals successfully written after deletion!");
                        })
                        .addOnFailureListener(e -> {
                            Log.e("DataRepository", "Error writing meals", e);
                        });

            }).addOnFailureListener(e -> {
                Log.e("DataRepository", "Error deleting existing meals", e);
            });
        }).addOnFailureListener(e -> {
            Log.e("DataRepository", "Error retrieving meals for deletion", e);
        });
    }


    // Load a list of meals
    public void loadFromFirebase(String email, String esmElFolder, OnMealsLoadedListener listener) {
        // Create a reference to the user's meals collection
        CollectionReference mealsRef = firestore.collection("users").document(email).collection(esmElFolder);

        // Get all documents in the collection
        mealsRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Create a list to hold the loaded meals
                    List<MealEntity> meals = new ArrayList<>();

                    // Loop through the documents in the collection
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        // Convert each document to a MealEntity object
                        MealEntity meal = document.toObject(MealEntity.class);
                        meals.add(meal);
                    }
                    // Pass the loaded meals back to the caller through the listener
                    listener.onMealsLoaded(meals);

                    // Log success
                    Log.d("DataRepository", "Meals successfully loaded!");
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Log.e("DataRepository", "Error loading meals", e);

                    // Pass null or an empty list back to the caller through the listener
                    listener.onMealsLoaded(null);
                });
    }
}

