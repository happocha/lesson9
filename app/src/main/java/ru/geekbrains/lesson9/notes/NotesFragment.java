package ru.geekbrains.lesson9.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.lesson9.R;
import ru.geekbrains.lesson9.model.NoteModel;
import ru.geekbrains.lesson9.notedetails.NoteDetailsFragment;
import ru.geekbrains.lesson9.notes.adapter.NoteAdapterCallbacks;
import ru.geekbrains.lesson9.notes.adapter.NoteItemCallback;
import ru.geekbrains.lesson9.notes.adapter.NoteListAdapter;
import ru.geekbrains.lesson9.notes.adapter.NoteSpaceDecorator;

public class NotesFragment extends Fragment implements NoteAdapterCallbacks, NotesFirestoreCallbacks {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private final List<NoteModel> noteList = new ArrayList<>();
    private final NotesRepository repository = new NotesRepositoryImpl(this);
    private final NoteListAdapter noteListAdapter = new NoteListAdapter(new NoteItemCallback(), this);

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_notes);
        floatingActionButton = view.findViewById(R.id.fb_notes_add);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.addItemDecoration(new NoteSpaceDecorator(getResources().getDimensionPixelSize(R.dimen.default_space)));
        recyclerView.setAdapter(noteListAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(null);
            }
        });
        repository.requestNotes();
    }

    @Override
    public void onItemClicked(int position) {
        NoteModel model = noteList.get(position);
        replaceFragment(model);
    }

    @Override
    public void onLongItemClicked(int position) {
        NoteModel model = noteList.get(position);
        repository.onDeleteClicked(model.getId());
    }

    private void replaceFragment(@Nullable NoteModel model) {
        Fragment fragment = NoteDetailsFragment.newInstance(model);
        requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.layout_container, fragment)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onSuccessNotes(@NonNull List<NoteModel> items) {
        noteList.clear();
        noteList.addAll(items);
        noteListAdapter.submitList(items);
    }

    @Override
    public void onErrorNotes(@Nullable String message) {
        showToast(message);
    }

    private void showToast(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
