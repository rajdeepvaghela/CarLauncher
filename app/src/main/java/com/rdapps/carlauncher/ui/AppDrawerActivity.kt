package com.rdapps.carlauncher.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rdapps.carlauncher.ui.adapters.AppListRecyclerAdapter
import com.rdapps.carlauncher.databinding.ActivityAppDrawerBinding
import com.rdapps.carlauncher.models.App

class AppDrawerActivity : AppCompatActivity(), AppListRecyclerAdapter.OnActionListener {

    private val binding by lazy {
        ActivityAppDrawerBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: AppListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.rvAppList.layoutManager = GridLayoutManager(this, 6)

        adapter = AppListRecyclerAdapter(fetchAllApps(), this)
        binding.rvAppList.adapter = adapter

    }

    private fun fetchAllApps(): List<App> {
        val list = arrayListOf<App>()

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolveInfos = packageManager.queryIntentActivities(intent, 0)

        for (resolveInfo in resolveInfos) {
            Log.d(TAG, "fetchAllApps: ${resolveInfo.activityInfo.name}")
            list.add(App.prepareFrom(packageManager, resolveInfo))
        }

        return list
    }

    private fun initListeners() {

    }

    override fun onClick(app: App) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            setClassName(app.packageName, app.className)
        })
    }

    companion object {
        private const val TAG = "AppDrawerActivity"
    }
}