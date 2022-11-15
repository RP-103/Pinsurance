package com.example.pinsurance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.enableSavedStateHandles
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.handin.Person
import com.example.pinsurance.handin.PersonAdapter

class HandInActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var personList: ArrayList<Person>
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hand_in)

        /** adding back button */
        val display = supportActionBar
        display?.setDisplayHomeAsUpEnabled(true)

        /** recyclerView */
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(this)

        personList = ArrayList()

        personList.add(Person("RP"))
        personList.add(Person("CanFlyHHH"))
        personList.add(Person("Yuki"))
        personList.add(Person("Wen"))
        personList.add(Person("Eating"))

        personAdapter = PersonAdapter(personList)
        recyclerView.adapter = personAdapter
    }

    /** clicking back button */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}