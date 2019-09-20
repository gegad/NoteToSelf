package com.egs.notetoself;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DialogShowNote extends DialogFragment {
    private Note mNote;
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_show_note, null);

        ImageView ivImportant = dialogView.findViewById(R.id.imageViewImortant);
        ImageView ivIdea= dialogView.findViewById(R.id.imageViewIdea);
        ImageView ivTodo = dialogView.findViewById(R.id.imageViewTodo);

        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        TextView txtDescription = dialogView.findViewById(R.id.txtDescription);
        txtTitle.setText(mNote.getTitle());
        txtDescription.setText(mNote.getDescription());

        if (!mNote.isImportant()) {
            ivImportant.setVisibility(View.GONE);
        }
        if (!mNote.isIdea()) {
            ivIdea.setVisibility(View.GONE);
        }
        if (!mNote.isTodo()) {
            ivTodo.setVisibility(View.GONE);
        }

        Button btnOk = dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setView(dialogView).setMessage("Note");
        return builder.create();
    }
    public void sendNoteSelected(Note noteSelected) {
        mNote = noteSelected;
    }

}
