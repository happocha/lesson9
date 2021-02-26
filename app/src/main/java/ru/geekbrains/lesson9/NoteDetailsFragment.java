package ru.geekbrains.lesson9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class NoteDetailsFragment extends Fragment {

    private final static String ARG_MODEL_KEY = "arg_model_key";

    public static Fragment newInstance(@NonNull NoteModel model) {
        Fragment fragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    private EditText titleEditText;
    private EditText descriptionEditText;
    private MaterialButton updateButton;
    private MaterialButton deleteButton;
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
        deleteButton = view.findViewById(R.id.btn_note_detail_remove);
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
        if (getArguments() != null) {
            NoteModel noteModel = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
            titleEditText.setText(noteModel.getTitle());
            descriptionEditText.setText(noteModel.getDescription());
        }
    }
}
