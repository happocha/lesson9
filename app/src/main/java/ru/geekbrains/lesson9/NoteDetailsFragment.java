package ru.geekbrains.lesson9;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteDetailsFragment extends Fragment {

    private final static String ARG_MODEL_KEY = "arg_model_key";

    private static final String TAG = "NoteDetailsFragment";
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static Fragment newInstance(@Nullable NoteModel model) {
        Fragment fragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    private EditText titleEditText;
    private EditText descriptionEditText;
    private MaterialButton updateButton;
    private MaterialToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleEditText = view.findViewById(R.id.et_note_details_title);
        descriptionEditText = view.findViewById(R.id.et_note_details_description);
        updateButton = view.findViewById(R.id.btn_note_detail_update);
        toolbar = view.findViewById(R.id.toolbar_note_details);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = titleEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                saveDataToDatabase(title, description);
            }
        });
        if (getArguments() != null) {
            NoteModel noteModel = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
            if (noteModel != null) {
                titleEditText.setText(noteModel.getTitle());
                descriptionEditText.setText(noteModel.getDescription());
            }
        }
    }

    private void saveDataToDatabase(
        @Nullable String title,
        @Nullable String description) {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            final String id = UUID.randomUUID().toString();
            final Map<String, Object> note = new HashMap<>();
            note.put("id", id);
            note.put("title", title);
            note.put("description", description);
            firebaseFirestore
                .collection("notes")
                .document(id)
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireContext(), "Запись успешно создана", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            Toast.makeText(requireContext(), "Поля не могут быть пустые", Toast.LENGTH_SHORT).show();
        }
    }


}
