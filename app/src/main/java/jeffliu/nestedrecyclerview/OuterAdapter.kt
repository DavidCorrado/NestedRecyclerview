package jeffliu.nestedrecyclerview


import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.HashMap

class OuterAdapter(private val mItemClickListener: InnerAdapter.OnItemClickListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mList: ArrayList<ArrayList<Int>>? = null
    private val listPosition = HashMap<Int, Int>()

    fun updateList(list: ArrayList<ArrayList<Int>>?) {
        mList = list
    }

    private inner class CellViewHolder(val rv: RecyclerView, listener: InnerAdapter.OnItemClickListener?) : RecyclerView.ViewHolder(rv) {

        private var mAdapter: InnerAdapter = InnerAdapter().apply {
            setOnItemClickListener(listener)
        }

        fun getLinearLayoutManager(): LinearLayoutManager {
            return rv.layoutManager as LinearLayoutManager
        }

        fun setData(list: ArrayList<Int>?) {
            mAdapter.updateList(list)
        }

        init {
            rv.apply {
                adapter = mAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val innerRv = RecyclerView(parent.context)
        val innerLLM = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        innerRv.layoutManager = innerLLM
        return CellViewHolder(innerRv, mItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            else -> {
                val cellViewHolder = viewHolder as CellViewHolder
                cellViewHolder.setData(mList!![position])
            }
        }
    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val cellViewHolder = viewHolder as CellViewHolder
        val firstVisiblePosition = cellViewHolder.getLinearLayoutManager().findFirstVisibleItemPosition()
        listPosition[position] = firstVisiblePosition
        super.onViewRecycled(viewHolder)
    }


    override fun getItemCount(): Int {
        if (mList.isNullOrEmpty()) {
            return 0
        }
        return mList!!.size
    }
}