package com.emirozturk.retrofit.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emirozturk.retrofit.R
import com.emirozturk.retrofit.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    private val colors: Array<String> = arrayOf("#EAECEC", "#FFFFFF")

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun binding(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.findViewById<TextView>(R.id.textName).text = cryptoModel.currency
            itemView.findViewById<TextView>(R.id.textPrice).text = cryptoModel.price
            itemView.findViewById<LinearLayout>(R.id.linearLayout).setBackgroundColor(Color.parseColor(colors[position % 2]))
            //itemView.setBackgroundColor(Color.parseColor(colors[position % 2]))
            itemView.findViewById<LinearLayout>(R.id.linearLayout).setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.binding(cryptoList[position], colors, position, listener)
    }
}