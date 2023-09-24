package com.emirozturk.retrofit.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emirozturk.retrofit.R
import com.emirozturk.retrofit.adapter.RecyclerViewAdapter
import com.emirozturk.retrofit.model.CryptoModel
import com.emirozturk.retrofit.model.showToast
import com.emirozturk.retrofit.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private val baseURL = "https://raw.githubusercontent.com/"
    private var models : ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        models = ArrayList(it)
                        recyclerViewAdapter = RecyclerViewAdapter(models!!, this@MainActivity)
                        recyclerView.adapter = recyclerViewAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        showToast(this, "${cryptoModel.currency} clicked")
    }
}