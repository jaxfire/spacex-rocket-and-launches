package com.jaxfire.spacexinfo.internal

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.jaxfire.spacexinfo.data.db.entity.RocketEntity


class RocketResponseDiffCallback(private val mOldEmployeeList: List<RocketEntity>, private val mNewEmployeeList: List<RocketEntity>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].rocketId === mNewEmployeeList[newItemPosition].rocketId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldEmployeeList[oldItemPosition]
        val newEmployee = mNewEmployeeList[newItemPosition]

        return oldEmployee.rocketName.equals(newEmployee.rocketName)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}