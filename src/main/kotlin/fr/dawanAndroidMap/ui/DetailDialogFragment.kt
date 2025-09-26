package fr.dawanAndroidMap.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Fenêtre popup (DialogFragment) affichant les détails d’un centre.
 */
class DetailDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val name = requireArguments().getString("name").orEmpty()
        val addr = requireArguments().getString("address").orEmpty()
        return AlertDialog.Builder(requireContext())
            .setTitle(name)
            .setMessage(addr)
            .setPositiveButton("OK", null)
            .create()
    }

    companion object {
        /**
         * Crée une nouvelle instance du Dialog avec les données d’un centre.
         */
        fun newInstance(name: String, address: String) =
            DetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("address", address)
                }
            }
    }
}