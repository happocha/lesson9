package ru.geekbrains.lesson9;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements NoteAdapterCallbacks {

    private static final String TAG = "NotesFragment";
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<NoteModel> noteList = new ArrayList<>();

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private final NoteAdapter adapter = new NoteAdapter(this);
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
        getNotes();
    }

    @Override
    public void onItemClicked(int position) {
//        NoteModel model = noteModels.get(position);
//        replaceFragment(model);
    }

    @Override
    public void onLongItemClicked(int position) {
        Toast.makeText(requireContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(@Nullable NoteModel model) {
        Fragment fragment = NoteDetailsFragment.newInstance(model);
        requireActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.layout_container, fragment)
            .addToBackStack(null)
            .commit();
    }

    private void getNotes() {
        firebaseFirestore.collection(Constants.TABLE_NAME_NOTES)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            noteList = task.getResult().toObjects(NoteModel.class);
                        }
                        Log.d("", "");
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }

}
