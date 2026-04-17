package com.rdapps.carlauncher.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.rdapps.carlauncher.data.save
import com.rdapps.carlauncher.databinding.FragmentAppListDialogBinding
import com.rdapps.carlauncher.models.App
import com.rdapps.carlauncher.ui.adapters.AppListRecyclerAdapter

class AppListDialogFragment : DialogFragment(), AppListRecyclerAdapter.OnActionListener {

    private var _binding: FragmentAppListDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AppListRecyclerAdapter

    private val shortcut by lazy {
        arguments?.getString(SHORTCUT) ?: throw RuntimeException("$SHORTCUT required")
    }

    private var onSet: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.rvAppList.layoutManager = GridLayoutManager(requireContext(), 6)

        adapter = AppListRecyclerAdapter(fetchAllApps(), this)
        binding.rvAppList.adapter = adapter

        binding.root.post {
            val width = resources.displayMetrics.widthPixels * 90 / 100
            dialog?.window?.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        }
    }

    private fun fetchAllApps(): List<App> {
        val list = arrayListOf<App>()

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolveInfos = requireActivity().packageManager.queryIntentActivities(intent, 0)

        for (resolveInfo in resolveInfos) {
            Log.d(TAG, "fetchAllApps: ${resolveInfo.activityInfo.icon}")
            list.add(App.prepareFrom(requireActivity().packageManager, resolveInfo))
        }

        return list
    }

    override fun onClick(app: App) {
        requireActivity().save(shortcut, app.packageName)
        dismiss()
        onSet()
    }

    companion object {
        const val TAG = "AppListDialogFragment"
        private const val SHORTCUT = "shortcut"

        fun newInstance(shortcut: String, onSet: () -> Unit) = AppListDialogFragment().apply {
            this.onSet = onSet
            arguments = Bundle().apply {
                putString(SHORTCUT, shortcut)
            }
        }
    }
}