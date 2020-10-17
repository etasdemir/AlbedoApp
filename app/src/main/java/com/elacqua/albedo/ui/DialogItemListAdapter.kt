package com.elacqua.albedo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import kotlinx.android.synthetic.main.dialog_recycler_item.view.*

class DialogItemListAdapter (private val listener: DialogItemClickListener)
    : RecyclerView.Adapter<DialogItemListAdapter.DialogViewHolder>(){
    private val items = ArrayList<String>()

    fun setItems(itemListNames: List<String>){
        items.clear()
        items.addAll(itemListNames)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.dialog_recycler_item , parent, false)
        return DialogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.run {
            onBind(position)
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DialogViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun onBind(position: Int) {
            view.txt_dialog_rv_list_name.text = items[position]
        }

        fun onClick(position: Int){
            view.setOnClickListener {
                listener.onClick(items[position])
            }
        }
    }
}