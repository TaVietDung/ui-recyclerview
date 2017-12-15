package dungtv.ui.recyclerview.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_demo_recycler_view.*
import kotlinx.android.synthetic.main.item_demo_recycler_view.view.*
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import dungtv.ui.recyclerview.R


class DemoRecyclerView : AppCompatActivity() {
    private var mAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_recycler_view)

        initAdapter()
        initAction()
    }

    private fun initAction() {
        btnLinearLayout.setOnClickListener {
            rvDemo.layoutManager = LinearLayoutManager(this)
        }

        btnGridLayout.setOnClickListener {
            rvDemo.layoutManager = gridLayoutManager(mAdapter)
        }
    }

    private fun initAdapter() {
        rvDemo.hasFixedSize()
        rvDemo.layoutManager = gridLayoutManager(mAdapter)
        rvDemo.adapter = mAdapter
    }

    private fun gridLayoutManager(mAdapter: MyAdapter): GridLayoutManager {
        val mLayoutManager = GridLayoutManager(this, SPAN_COUNT)
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mAdapter.getItemViewType(position)) {
                    MyAdapter.VIEW_TYPE_HEADER -> SPAN_SIZE_HEADER
                    else -> SPAN_SIZE_ITEM
                }
            }
        }
        return mLayoutManager
    }

    class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        companion object {
            const val VIEW_TYPE_HEADER = 0
            const val VIEW_TYPE_ITEM = 1
            const val POSITION_HEADER = 5
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                VIEW_TYPE_HEADER -> {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_header_recycler_view,
                            parent, false)
                    MyViewHolder(view)
                }

                else -> {
                    val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_demo_recycler_view,
                            parent, false)
                    MyViewHolder(view)
                }
            }

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            when (position % POSITION_HEADER) {
                VIEW_TYPE_HEADER -> {
                    //TODO: as ViewHolder of Header
                }
                else -> (holder as MyViewHolder).bindData("Column")
            }

        }

        override fun getItemCount(): Int = 20

        override fun getItemViewType(position: Int): Int {
            return when (position % POSITION_HEADER) {
                VIEW_TYPE_HEADER -> VIEW_TYPE_HEADER
                else -> VIEW_TYPE_ITEM
            }

        }


        class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
//            private var itemHeight: Int = 0

//            init {
//                if (itemView.context != null) {
//                    //<dimen name="item_height">150dp</dimen>
//                    itemHeight = itemView.context.resources.getDimensionPixelSize(R.dimen.item_height)
//                }
//            }

            fun bindData(string: String) {
                //You may be use with zigzag layout
//                val top = itemHeight.div(3).times(getMultiplierByPosition(position))
//                val layout = itemView.cvContent.layoutParams as FrameLayout.LayoutParams
//                layout.topMargin = top
//                itemView.cvContent.layoutParams = layout

                itemView.tvContent.text = string
            }

            /**
             * You may be use method below with zigzag layout
             */
//            private fun getMultiplierByPosition(position: Int) = when (position) {
//                0 -> 0
//                1 -> 2
//                2 -> 4
//                else -> when (position % 5) {
//                    4 -> 1
//                    3 -> 5
//                    else -> 3
//                }
//            }
        }

    }

    companion object {
        const val SPAN_COUNT = 4
        const val SPAN_SIZE_HEADER = 4
        const val SPAN_SIZE_ITEM = 1
    }
}