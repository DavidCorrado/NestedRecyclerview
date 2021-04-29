package jeffliu.nestedrecyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        val subList1 = ArrayList<Int>()
        for (i in 0..7) {
            subList1.add(i)
        }
        val list = ArrayList<ArrayList<Int>>()
        for (i in 0..49) {
            list.add(subList1)
        }

        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = OuterAdapter(object : InnerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, data: Int) {
                Toast.makeText(this@MainActivity, "click $data", Toast.LENGTH_SHORT).show()
            }

            override fun onItemLongClick(view: View, data: Int) {
                Toast.makeText(this@MainActivity, "long click $data", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = adapter
        adapter.updateList(list)
    }
}