package com.rdapps.carlauncher.ui

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rdapps.carlauncher.data.read
import com.rdapps.carlauncher.databinding.ActivityLauncherBinding
import com.rdapps.carlauncher.models.App
import com.rdapps.carlauncher.ui.fragments.AppListDialogFragment

class LauncherActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLauncherBinding.inflate(layoutInflater)
    }

    private val resolveInfos: List<ResolveInfo> get() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager.queryIntentActivities(intent, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        initListeners()
    }

    private fun initViews() {
        prepareView(binding.img1, "short_1")
        prepareView(binding.img2, "short_2")
        prepareView(binding.img3, "short_3")
        prepareView(binding.img4, "short_4")
        prepareView(binding.img5, "short_5")
        prepareView(binding.img6, "short_6")
    }

    private fun initListeners() {
        binding.imgAllApps.setOnClickListener {
            startActivity(Intent(this, AppDrawerActivity::class.java))
        }

        binding.img1.setOnClickListener {
            prepareListener(it, "short_1")
        }

        binding.img1.setOnLongClickListener {
            AppListDialogFragment.newInstance("short_1") {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
            true
        }

        binding.img2.setOnClickListener {
            prepareListener(it, "short_2")
        }

        binding.img2.setOnLongClickListener {
            AppListDialogFragment.newInstance("short_2") {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
            true
        }

        binding.img3.setOnClickListener {
            prepareListener(it, "short_3")
        }

        binding.img3.setOnLongClickListener {
            AppListDialogFragment.newInstance("short_3") {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
            true
        }

        binding.img4.setOnClickListener {
            prepareListener(it, "short_4")
        }

        binding.img4.setOnLongClickListener {
            AppListDialogFragment.newInstance("short_4") {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
            true
        }

        binding.img5.setOnClickListener {
            prepareListener(it, "short_5")
        }

        binding.img5.setOnLongClickListener {
            AppListDialogFragment.newInstance("short_5") {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
            true
        }

        binding.img6.setOnClickListener {
            prepareListener(it, "short_6")
        }

        binding.img6.setOnLongClickListener {
            AppListDialogFragment.newInstance("short_6") {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
            true
        }
    }

    private fun prepareView(imageView: ImageView, shortcut: String) {
        val appPackage = read(shortcut)
        val resolveInfo = resolveInfos.find { it.activityInfo.packageName == appPackage }
        resolveInfo?.let {
            val app = App.prepareFrom(packageManager, it)
            imageView.setImageDrawable(app.icon)
            imageView.tag = app
        }
    }

    private fun prepareListener(view: View, shortcut: String) {
        val tag = view.tag
        if (tag is App) {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                setClassName(tag.packageName, tag.className)
            })
        } else {
            AppListDialogFragment.newInstance(shortcut) {
                initViews()
            }.show(supportFragmentManager, AppListDialogFragment.TAG)
        }
    }
}