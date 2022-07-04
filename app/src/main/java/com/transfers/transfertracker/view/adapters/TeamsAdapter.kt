package com.transfers.transfertracker.view.adapters

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Composable
private fun TeamItemView(team: Team){

}

private class TeamsAdapter : ListAdapter<Team, TeamsViewHolder>(TeamDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder = TeamsViewHolder(ComposeView(parent.context))
    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) = holder.bind(getItem(position))
}

private class TeamsViewHolder(view: ComposeView): RecyclerView.ViewHolder(view) {
    fun bind(team: Team) {
        (itemView as ComposeView).setContent {
            TransferTrackerTheme {
                TeamItemView(team = team)
            }
        }
    }
}

private class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {

    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}