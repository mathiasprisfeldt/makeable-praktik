package me.mathiasprisfeldt.makeablepraktik.recycler_views

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_chat.*
import me.mathiasprisfeldt.makeablepraktik.Message
import me.mathiasprisfeldt.makeablepraktik.R

class MessagesRecyclerAdapter internal constructor(
    options: FirestoreRecyclerOptions<Message>,
    private val messages: RecyclerView,
    private val layoutManager: LinearLayoutManager
) : FirestoreRecyclerAdapter<Message, MessageHolder>(options) {

    private var atBottom: Boolean = true

    init {
        messages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                atBottom = !recyclerView.canScrollVertically(1)
            }
        })

        messages.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            if (atBottom) {
                messages.scrollToPosition(itemCount - 1)
            }
        }

        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePosition == -1 || positionStart >= itemCount - 1 && lastVisiblePosition == positionStart - 1) {
                    messages.scrollToPosition(positionStart)
                }
            }
        })
    }

    override fun onBindViewHolder(productViewHolder: MessageHolder, position: Int, productModel: Message) {
        productViewHolder.setModel(productModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return MessageHolder(view)
    }
}