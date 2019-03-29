package com.example.employeepoc.ui.showlist.adapter

import android.databinding.DataBindingUtil
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.example.employeepoc.BR
import com.example.employeepoc.R
import com.example.employeepoc.databinding.ItemEmployeeBinding
import com.example.employeepoc.repo.showlistrepo.EmployeeEntity

/**
 * Adapter for recycler view
 */

internal class RecyclerViewBindingAdapter(val listener: DeleteImageClickListener) :
    RecyclerSwipeAdapter<RecyclerViewBindingAdapter.EmployeeViewHolder>() {

    private var mEmployeeList: List<EmployeeEntity> = emptyList()

    /**
     * Custom listener interface for recyclerView item clicks
     */
    interface DeleteImageClickListener {

        fun onDeleteImageClicked(employee: EmployeeEntity)
    }

    @LayoutRes
    fun layoutId(): Int = R.layout.item_employee

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemEmployeeBinding = DataBindingUtil.inflate(layoutInflater, layoutId(), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: EmployeeViewHolder, position: Int) {
        val employeeEntity = mEmployeeList[position]
        mItemManger.bindView(viewHolder.itemView, position)
        viewHolder.bindItems(employeeEntity)
    }

    override fun getItemCount(): Int {
        return mEmployeeList.size
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe_layout
    }

    /**
     * View holder class for recycler view
     */
    internal inner class EmployeeViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: EmployeeEntity) {
            with(binding) {
                setVariable(BR.employee, item)
                setVariable(BR.listenerDelete, listener)
                executePendingBindings()
            }
        }
    }

    /**
     * Populates the recyclerVIew
     *  Uses DiffUtil to find the difference between the existing list and the new list
     *  and notify updates to a RecyclerView Adapter.
     *
     *  @param employeeList The new list of employees
     */
    fun setEmployeeList(employeeList: List<EmployeeEntity>) {
        if (mEmployeeList.isEmpty()) {
            mEmployeeList = employeeList
            notifyItemRangeInserted(0, employeeList.size)
        } else {
            val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mEmployeeList[oldItemPosition].id == employeeList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mEmployeeList[oldItemPosition].employeeName == employeeList[newItemPosition].employeeName
                }

                override fun getOldListSize() = mEmployeeList.size

                override fun getNewListSize() = employeeList.size
            })
            mEmployeeList = employeeList
            diff.dispatchUpdatesTo(this)
        }
    }
}