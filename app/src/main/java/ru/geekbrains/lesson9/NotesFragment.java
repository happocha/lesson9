package ru.geekbrains.lesson9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements NoteAdapterCallbacks {

    private RecyclerView recyclerView;
    private final NoteAdapter adapter = new NoteAdapter(this);
    private final NoteListAdapter noteListAdapter = new NoteListAdapter(new NoteItemCallback(), this);
    private final List<NoteModel> noteModels = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildList();
    }

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.addItemDecoration(new NoteSpaceDecorator(getResources().getDimensionPixelSize(R.dimen.default_space)));
//        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(noteListAdapter);
        noteListAdapter.submitList(noteModels);
    }

    @Override
    public void onItemClicked(int position) {
        NoteModel model = noteModels.get(position);
        replaceFragment(model);
    }

    @Override
    public void onLongItemClicked(int position) {
        final List<NoteModel> items = new ArrayList<>();
        noteModels.remove(position);
        items.addAll(noteModels);
        noteListAdapter.submitList(items);
        Toast.makeText(requireContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(@NonNull NoteModel model) {
        Fragment fragment = NoteDetailsFragment.newInstance(model);
        requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.layout_container, fragment)
            .addToBackStack(null)
            .commit();
    }

    private void buildList() {
        for (int i = 0; i < 100; i++) {
            String title = String.format("title %s", i);
            String description = String.format("description %s", i);
            int color = R.color.design_default_color_background;
            if (i  % 2 == 0) {
                color = R.color.design_default_color_primary;
            }
            noteModels.add(new NoteModel(String.valueOf(i), title, description, color));
        }
    }
}
