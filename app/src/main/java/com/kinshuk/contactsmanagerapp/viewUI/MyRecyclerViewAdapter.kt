package com.kinshuk.contactsmanagerapp.viewUI

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kinshuk.contactsmanagerapp.R
import com.kinshuk.contactsmanagerapp.databinding.CardItemBinding
import com.kinshuk.contactsmanagerapp.room.User

class MyRecyclerViewAdapter(private val userList:List<User>
, private val clickListener: (User)->Unit):RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

class MyViewHolder(val binding: CardItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(user:User,clickListener: (User) -> Unit){
        val name = "<b>NAME:</b>" + user.name
        val mail = "<b>EMAIL:</b>" + user.email
        val contact = "<b>CONTACT:</b>" + user.contact
        binding.mailTv.text = Html.fromHtml(mail)
        binding.nameTv.text= Html.fromHtml(name)
        binding.contactTv.text = Html.fromHtml(contact)

        binding.ListItemLayout.setOnClickListener{
            clickListener(user)
        }
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:CardItemBinding = CardItemBinding.inflate(layoutInflater)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position],clickListener)
    }
}

