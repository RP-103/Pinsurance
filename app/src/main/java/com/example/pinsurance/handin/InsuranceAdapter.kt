package com.example.pinsurance.handin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.databinding.EachInsuranceBinding

class InsuranceAdapter(private val insuranceList: List<Insurances>)
    :RecyclerView.Adapter<InsuranceAdapter.InsuranceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsuranceViewHolder {
        var binding = EachInsuranceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return InsuranceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InsuranceViewHolder, position: Int) {
        val insurance: Insurances = insuranceList[position]

        holder.nameTxt.text = insurance.name
        holder.maturityTxt.text = insurance.maturity
        holder.startTxt.text = insurance.start
        holder.paymentYearTxt.text = insurance.paymentYear
        holder.guaranteeYearTxt.text = insurance.guaranteeYear
        holder.categoryTxt.text = insurance.category
        holder.costTxt.text = insurance.cost

        val isExpandable: Boolean = insuranceList[position].expandable
        holder.expandableLayout.visibility = if(isExpandable) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val insurance = insuranceList[position]
            insurance.expandable = !insurance.expandable
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return insuranceList.size
    }

    inner class InsuranceViewHolder(private val binding: EachInsuranceBinding)
        :RecyclerView.ViewHolder(binding.root) {

        var nameTxt: TextView = binding.name
        var maturityTxt: TextView = binding.maturity
        var startTxt: TextView = binding.start
        var paymentYearTxt: TextView = binding.paymentYear
        var guaranteeYearTxt: TextView = binding.guaranteeYear
        var categoryTxt: TextView = binding.category
        var costTxt: TextView = binding.cost
        var linearLayout: LinearLayout = binding.linearLayout
        var expandableLayout: RelativeLayout = binding.expandableLayout

        }
}