package com.example.demosample.presentation.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.demosample.R
import com.example.demosample.databinding.ItemUserProfileBinding
import com.example.demosample.domain.model.UserModel
import com.example.demosample.utils.extensions.loadImage
import com.example.demosample.utils.extensions.loadImageCircle

class MyChoiceUsersProfileAdapter(
    val itemList: ArrayList<UserModel>,
    val onRespond : (Boolean, Int)-> Unit={_,_->}
): RecyclerView.Adapter<MyChoiceUsersProfileAdapter.ChoiceViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChoiceViewHolder {
        return ChoiceViewHolder(
            binding = ItemUserProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ChoiceViewHolder,
        position: Int
    ) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ChoiceViewHolder(val binding: ItemUserProfileBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindData(position: Int){
            val user = itemList[position]
            binding.txtName.text = user.name
            binding.txtAddress.text = "${user.age}, "+user.address
            binding.txtStatus.text = user.invitationStatus
            binding.txtStatus.background = if(user.invitationStatus.equals(
                    binding.root.context.getString(
                        R.string.accepted
                    ),true
            )){
                ContextCompat.getDrawable(binding.root.context, R.drawable.bg_accept)
            }else{
                ContextCompat.getDrawable(binding.root.context, R.drawable.bg_reject)
            }

            /*
            * until no action is performed then show button otherwise show status*/
            if(user.invitationStatus.isEmpty()){
                binding.txtStatus.visibility = View.GONE
                binding.grpActButtons.visibility = View.VISIBLE
            }else{
                binding.txtStatus.visibility = View.VISIBLE
                binding.grpActButtons.visibility = View.GONE
            }

            binding.ivProfile.loadImageCircle(
                user.profilePicture,
                R.mipmap.ic_launcher,
            )
            binding.ivProfileBlur.loadImage(
                user.profilePictureBlur,
                R.mipmap.ic_launcher,
            )

            binding.ivAccept.setOnClickListener {
                onRespond(true,position)
            }
            binding.ivDecline.setOnClickListener {
                onRespond(false,position)
            }
        }
    }
}