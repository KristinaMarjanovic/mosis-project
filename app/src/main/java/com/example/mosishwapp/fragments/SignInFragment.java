package com.example.mosishwapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mosishwapp.R;
import com.example.mosishwapp.activities.MainActivity;
import com.example.mosishwapp.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;

    private DocumentReference documentReference;
    private FirebaseFirestore database;
    private FirebaseUser mUser;

    public SignInFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sign_in, container, false);
        //here data must be an instance of the class MarsDataProvider
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.confirmSignTvBtn.setOnClickListener(createOnClickListener());
        binding.passwordEt.setTransformationMethod(new PasswordTransformationMethod());
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = binding.usernameEt.getText().toString();
                final String password = binding.passwordEt.getText().toString();
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    performLogin(username, password);
                } else {
                    documentReference = database.collection("users").document("users_details");
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String email;
                                    try {
                                        email = document.get(username).toString();
                                        performLogin(email, password);
                                    } catch (NullPointerException ex){
                                        Toast.makeText(getContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        };
    }

    private void performLogin(String username, String password) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mUser = firebaseAuth.getCurrentUser();
                    startActivity(new Intent(getContext(), MainActivity.class));
                } else {
                    Toast.makeText(getContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
