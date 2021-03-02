package ru.geekbrains.lesson9.notedetails;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.UUID;

import ru.geekbrains.lesson9.R;
import ru.geekbrains.lesson9.model.NoteModel;

public class NoteDetailsFragment extends Fragment implements NoteFirestoreCallbacks {

    private final static String ARG_MODEL_KEY = "arg_model_key";
    private String deleteId = "";

    private final NoteRepository repository = new NoteRepositoryImpl(this);

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
        toolbar.inflateMenu(R.menu.note_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_note_delete) {
                    repository.onDeleteClicked(deleteId);
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
                return false;
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = titleEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                update(title, description);
            }
        });
        if (getArguments() != null) {
            NoteModel noteModel = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
            if (noteModel != null) {
                deleteId = noteModel.getId();
                titleEditText.setText(noteModel.getTitle());
                descriptionEditText.setText(noteModel.getDescription());
            }
        }
    }

    @Override
    public void onSuccess(@Nullable String message) {
        showToastMessage(message);
    }

    @Override
    public void onError(@Nullable String message) {
        showToastMessage(message);
    }

    private void update(
        @Nullable String title,
        @Nullable String description) {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            if (getArguments() != null) {
                NoteModel noteModel = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
                if (noteModel != null) {
                    repository.setNote(noteModel.getId(), title, description);
                } else {
                    String id = UUID.randomUUID().toString();
                    deleteId = id;
                    repository.setNote(id, title, description);
                }
            }
        } else {
            showToastMessage("Поля не могут быть пустые");
        }
    }

    private void showToastMessage(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

}
