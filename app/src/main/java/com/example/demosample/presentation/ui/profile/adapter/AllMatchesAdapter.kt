package com.example.demosample.presentation.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demosample.R
import com.example.demosample.databinding.ItemUserProfileBinding
import com.example.demosample.domain.model.UserModel
import com.example.demosample.utils.extensions.loadImage
import com.example.demosample.utils.extensions.loadImageCircle

class AllMatchesAdapter(
    private val onRespond: (Boolean, String) -> Unit = { _, _ -> }
) : ListAdapter<UserModel, AllMatchesAdapter.AllMatchesViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMatchesViewHolder {
        val binding = ItemUserProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AllMatchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllMatchesViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class AllMatchesViewHolder(private val binding: ItemUserProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(position: Int) {
            val user = getItem(position)
            val context = binding.root.context
            binding.txtName.text = user.name
            binding.txtAddress.text = "${user.age}, "+user.address
            binding.txtStatus.text = user.invitationStatus

            binding.txtStatus.background = if (
                user.invitationStatus.equals(
                    context.getString(R.string.accepted),
                    ignoreCase = true
                )
            ) {
                ContextCompat.getDrawable(context, R.drawable.bg_accept)
            } else {
                ContextCompat.getDrawable(context, R.drawable.bg_reject)
            }

            // Show/hide buttons
            if (user.invitationStatus.isEmpty()) {
                binding.txtStatus.visibility = View.GONE
                binding.grpActButtons.visibility = View.VISIBLE
            } else {
                binding.txtStatus.visibility = View.VISIBLE
                binding.grpActButtons.visibility = View.GONE
            }

            binding.ivProfile.loadImageCircle(
                user.profilePicture,
                R.drawable.user_placeholder
            )
            binding.ivProfileBlur.loadImage(
                user.profilePictureBlur,
                R.mipmap.ic_launcher
            )

            binding.ivAccept.setOnClickListener {
                onRespond(true, user.id)
                removeUserFromPositon(position)
            }
            binding.ivDecline.setOnClickListener {
                onRespond(false, user.id)
                removeUserFromPositon(position)
            }
        }
    }

    private fun removeUserFromPositon(position: Int){
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }
}

/** DiffUtil callback for UserModel */
class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        // Compare by unique ID (replace with your unique field)
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        // Deep equality check
        return oldItem == newItem
    }
}
