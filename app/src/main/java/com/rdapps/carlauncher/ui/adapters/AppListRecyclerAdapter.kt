package com.rdapps.carlauncher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rdapps.carlauncher.databinding.ViewAppIconBinding
import com.rdapps.carlauncher.models.App

class AppListRecyclerAdapter(
    private val appList: List<App>,
    private val onActionListener: OnActionListener
) :
    RecyclerView.Adapter<AppListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewAppIconBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(appList[position])
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ViewAppIconBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onActionListener.onClick(appList[adapterPosition])
            }
        }

        fun bind(app: App) {
            binding.imgAppIcon.setImageDrawable(app.icon)
            binding.tvAppName.text = app.name
        }
    }

    interface OnActionListener {
        fun onClick(app: App)
    }
}