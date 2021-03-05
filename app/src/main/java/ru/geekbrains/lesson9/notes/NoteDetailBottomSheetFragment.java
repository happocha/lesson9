package ru.geekbrains.lesson9.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.geekbrains.lesson9.R;
import ru.geekbrains.lesson9.model.NoteModel;

public class NoteDetailBottomSheetFragment extends BottomSheetDialogFragment {

    private final static String ARG_MODEL_KEY = "arg_model_key";

    public static BottomSheetDialogFragment create(@Nullable NoteModel model) {
        BottomSheetDialogFragment fragment = new NoteDetailBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MODEL_KEY, model);
        fragment.setArguments(bundle);
        return fragment;
    }

    interface OnClickListener {
        void onTitleClicked(String title);
    }

    private OnClickListener clickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        clickListener = (OnClickListener) getParentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleTextView = view.findViewById(R.id.tv_note_detail_title);
        TextView descriptionTextView = view.findViewById(R.id.tv_note_detail_description);

        if (getArguments() != null) {
            NoteModel model = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
            if (model != null) {
                titleTextView.setText(model.getTitle());
                descriptionTextView.setText(model.getDescription());
                titleTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onTitleClicked(titleTextView.getText().toString());
                        dismiss();
                    }
                });
            }
        }
    }
}
