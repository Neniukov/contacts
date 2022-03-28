package com.example.contacts.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.util.concurrent.CopyOnWriteArrayList

open class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items: CopyOnWriteArrayList<Any> = CopyOnWriteArrayList()

    val entities: MutableMap<Class<*>, Int> = mutableMapOf()

    var isRecyclable = true

    val itemsTyped: MutableMap<Int, BaseAdapterItemModel<*, out ViewBinding>> = mutableMapOf()

    val itemTypes: ArrayList<Int> = arrayListOf()

    fun itemsLoaded(newItems: List<Any>?) {
        items.clear()
        newItems?.let(items::addAll)
        notifyDataSetChanged()
    }

    fun itemLoaded(item: Any) {
        items.clear()
        items.add(item)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.animation?.start()
    }

    inline fun <reified T : Any> replaceIf(item: T, predicate: (T) -> Boolean): Boolean {
        var isReplace = false
        items.forEachIndexed { index, itemToChange ->
            if (itemToChange is T) {
                if (predicate.invoke(itemToChange)) {
                    items[index] = item
                    isReplace = true
                    notifyItemChanged(index)
                }
            }
        }
        return isReplace
    }

    fun itemAdded(item: Any) {
        items.add(item)
        notifyItemInserted(items.indexOf(item))
    }

    fun itemAdded(position: Int, item: Any) {
        if (items.size > position) items.add(position, item)
        else items.add(item)
        notifyItemInserted(items.indexOf(item))
    }

    inline fun <reified T> removeItem(predicate: (T) -> Boolean) {
        val item = items.firstOrNull {
            val itemToCheck = if (it is T) it else null
            itemToCheck?.let { predicate.invoke(it) } ?: false
        } ?: return
        items.remove(item)
        notifyDataSetChanged()
    }

    fun itemsAdded(newItems: List<Any>?) {
        newItems?.let(items::addAll)
        notifyItemInserted(items.lastIndex)
    }

    fun remove(item: Any) {
        val index = items.indexOf(item)
        if (items.remove(item)) notifyItemRemoved(index)
    }

    fun itemChanged(item: Any) {
        val index = items.indexOf(item)
        if (-1 != index) notifyItemChanged(index)
    }

    fun setIsNotRecycled() {
        isRecyclable = false
    }

    fun getAllItems(): List<Any> = items

    inline fun <reified T : Any> getAllTypedItems() =
        getAllItems().filterIsInstance<T>()

    inline fun <reified T : Any, B : ViewBinding> map(
        layout: Int,
        holder: Holder<T, B>
    ): BaseAdapter {
        val itemType = entities.size
        entities[T::class.java] = itemType
        itemsTyped[itemType] = BaseAdapterItemModel(layout, holder)
        itemTypes.add(itemType)
        return this
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: ViewDataBinding = try {
            DataBindingUtil.inflate(
                LayoutInflater.from(view.context),
                itemsTyped[viewType]?.layout ?: 0,
                view,
                false
            )
        } catch (exception: Exception) {
            DataBindingUtil.inflate(
                LayoutInflater.from(view.context),
                itemsTyped[viewType]?.layout ?: 0,
                view,
                false
            )
        }
        val holder =
            RecyclerViewHolder(itemView, itemsTyped[viewType]?.holder as? Holder<*, ViewBinding>)
        holder.create(items)
        return holder
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return entities[items[position]::class.java] ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bind(holder, items[position])
    }

    inline fun <reified T> bind(holder: RecyclerView.ViewHolder, item: T) {
        (holder as? RecyclerViewHolder<T, out ViewBinding>)?.bind(item)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as? RecyclerViewHolder<Any, out ViewBinding>)?.recycle()
    }
}

abstract class Holder<T, B : ViewBinding> {
    abstract fun bind(binding: B, item: T)
    open fun create(binding: B, items: List<Any?>, holder: RecyclerViewHolder<T, B>) {}
    open fun recycle() {}
}

class RecyclerViewHolder<T, B : ViewBinding>(
    private val viewBinding: B,
    val holder: Holder<T, B>?
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: T) {
        holder?.bind(viewBinding, item)
    }

    fun recycle() {
        holder?.recycle()
    }

    fun create(items: List<Any?>) {
        holder?.create(viewBinding, items, this)
    }
}

data class BaseAdapterItemModel<T, B : ViewBinding>(
    @DrawableRes val layout: Int,
    val holder: Holder<T, B>,
)