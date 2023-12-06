package com.kinshuk.contactsmanagerapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinshuk.contactsmanagerapp.myViewModel.UserViewModel
import com.kinshuk.contactsmanagerapp.databinding.ActivityMainBinding
import com.kinshuk.contactsmanagerapp.room.User
import com.kinshuk.contactsmanagerapp.room.UserDataBase
import com.kinshuk.contactsmanagerapp.room.UserRepo
import com.kinshuk.contactsmanagerapp.room.VmFactory
import com.kinshuk.contactsmanagerapp.viewUI.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.teal_200))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //room
        val dao = UserDataBase.getInstance(application).userDao
        val repo = UserRepo(dao)
        val factory = VmFactory(repo)

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.userViewModel = userViewModel

        binding.lifecycleOwner = this

        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.recyview.layoutManager = LinearLayoutManager(this)
        DisplayUserList()
    }
    private fun DisplayUserList(){
        userViewModel.users.observe(this,{
            binding.recyview.adapter = MyRecyclerViewAdapter(it,{selectedItem:User->ListItemClicked(selectedItem)})
        })
    }
    private fun ListItemClicked(selctedItem: User){
            Toast.makeText( this,"Selected Name is ${selctedItem.name}",Toast.LENGTH_LONG).show()

        userViewModel.initUpdateAndDelete(selctedItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.clearTxts->{
                userViewModel.reset()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }

    }
}