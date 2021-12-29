package com.example.studentscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class ProfileFragmentnew extends Fragment {

    EditText fullNameUpdate, GenderUpdate, emailUpdate, PasswordUpdate;
    Button updateButton;
    FirebaseAuth fauth;
    FirebaseFirestore fStore;
    String userId;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment,container,false);
        fullNameUpdate = view.findViewById(R.id.newfullname);
        GenderUpdate = view.findViewById(R.id.newgender);
        emailUpdate = view.findViewById(R.id.newemail);
        PasswordUpdate = view.findViewById(R.id.newpassword);
        updateButton = view.findViewById(R.id.update);

        fauth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();
        userId = fauth.getCurrentUser().getUid();
//        DatabaseReference reference ;
//
//        reference = FirebaseDatabase.getInstance().getReference("users");



        DocumentReference docref = fStore.collection("users").document(userId);
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("User Data", "onSuccess: "+documentSnapshot.getData());

                fullNameUpdate.setText(documentSnapshot.getString("fname"));
                emailUpdate.setText(documentSnapshot.getString("email"));
                GenderUpdate.setText(documentSnapshot.getString("gender"));
                PasswordUpdate.setText(documentSnapshot.getString("password"));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Getting user", "onFailure: "+e);
            }
        });

//        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                fullNameUpdate.setText(value.getString("fname"));
//                emailUpdate.setText(value.getString("email"));
//                GenderUpdate.setText(value.getString("gender"));
//                PasswordUpdate.setText(value.getString("password"));
//            }
//        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fullNameUpdate.getText().toString();
                String emailname = emailUpdate.getText().toString();
                String gendername = GenderUpdate.getText().toString();
                String passwordname = PasswordUpdate.getText().toString();

                HashMap h = new HashMap();
                h.put("fname", fname);
                h.put("email", emailname);
                h.put("gender", gendername);
                h.put("password", passwordname);
                docref.update(h).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "On update failure" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


//                public void UpdateMethod(View view){
//                    if (isPasswordUpdateChanged()) {
//                        Toast.makeText(getContext(), "Password updated", Toast.LENGTH_SHORT).show();
//                    }
//                }

                FirebaseUser user = fauth.getInstance().getCurrentUser();


                user.updatePassword(passwordname).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("User Password Updation", "Password updated");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("User Password Updation" +
                                "", "onFailure: ");
                    }
                });


                AuthCredential credential = EmailAuthProvider
                        .getCredential(emailname, passwordname);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("User Reauthentication", "onComplete: User Reauthenticated");

                            startActivity (new Intent(getContext(),Login.class));
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("User Reauthentication", "onFailure: "+e);
                    }
                });


            }


//            private boolean isPasswordUpdateChanged() {
//            }
        });



        return view;
    }
}
