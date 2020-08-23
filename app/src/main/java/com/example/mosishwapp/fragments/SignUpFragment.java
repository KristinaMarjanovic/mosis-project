package com.example.mosishwapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mosishwapp.R;
import com.example.mosishwapp.activities.MainActivity;
import com.example.mosishwapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sign_up, container, false);
        //here data must be an instance of the class MarsDataProvider
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.confirmSignTvBtn.setOnClickListener(createOnClickListener());
        binding.passwordEt.setTransformationMethod(new PasswordTransformationMethod());
        binding.repeatPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = binding.usernameEt.getText().toString();
                final String password = binding.passwordEt.getText().toString();
                final String repeatedPassword = binding.repeatPasswordEt.getText().toString();
                final String email = binding.emailEt.getText().toString().trim();
                DocumentReference documentReference = database.collection("users").document("users_details");
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String checkEmail;
                                try {
                                    HashMap<String, String> data = (HashMap)document.getData();
                                    checkEmail = data.get(username);
                                    if(checkEmail != null)
                                        Toast.makeText(getContext(), "Username is taken, please, take another.", Toast.LENGTH_LONG).show();
                                    else
                                    {
                                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                user = firebaseAuth.getCurrentUser();
                                                if(task.isSuccessful()) {
                                                    CollectionReference usersCollection = database.collection("users");
                                                    Map<String, String> userCredentials = new HashMap<>();
                                                    userCredentials.put(username, email);
                                                    usersCollection.document("users_details").set(userCredentials, SetOptions.merge());
                                                    startActivity(new Intent(getContext(), MainActivity.class));
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Email is taken, please, take another.", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                } catch (NullPointerException ex) {

                                    }
                                }
                            }
                        }
                });

            }
        };

    }
}