package com.example.pinsurance.handin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.R
import com.example.pinsurance.databinding.ActivityDetailedBinding

class DetailedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedBinding
    private lateinit var recyclerView: RecyclerView

    private val insuranceList = ArrayList<Insurances>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // To retrieve object in second Activity
        val person = intent.getParcelableExtra("PERSON") as Person?
        if (person != null) {
            binding.handTitle.text = person.name
        }
        else {
            Log.e("XXXX", "person name passed is NULL.")
        }

        // init data. todo: get from database
        insuranceList.add(Insurances("旅平險", "到期日 2022/11/18", "投保日 2020/11/18", "繳費年期 1年期", "保障年期 2年期", "繳費類別 半年繳", "保費 10,000"))
        insuranceList.add(Insurances("年金險", "到期日 2022/9/20", "投保日 2012/9/20", "繳費年期 5年期", "保障年期 10年期", "繳費類別 季繳", "保費 250,000"))

        val insuranceAdapter = InsuranceAdapter(insuranceList)

        /** RecyclerView */
        recyclerView = binding.recyclerView
        recyclerView.adapter = insuranceAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
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