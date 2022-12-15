package com.example.pinsurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.databinding.ActivityHandInBinding
import com.example.pinsurance.handin.DetailedActivity
import com.example.pinsurance.handin.Person
import com.example.pinsurance.handin.PersonAdapter

class HandInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHandInBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var personList: ArrayList<Person>
    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        personAdapter.OnItemClick = {
            val intent = Intent(this, DetailedActivity::class.java)
            intent.putExtra("PERSON" , it)
            startActivity(intent)
        }
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