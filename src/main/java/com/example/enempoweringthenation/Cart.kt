package com.example.enempoweringthenation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class Cart : Fragment() {

        private lateinit var itemListView: ListView
        private lateinit var viewCartButton: Button
        private val cart = Cart()

    data class Item(
        val name: String,
        val price: Double,
        var quantity: Int
    )

    class Cart {
        private val items = mutableListOf<Item>()

        fun addItem(item: Item) {
            val existingItem = items.find { it.name == item.name }
            if (existingItem != null) {
                existingItem.quantity += item.quantity
            } else {
                items.add(item)
            }
        }

        fun getItems(): List<Item> {
            return items
        }

        fun getTotalPrice(): Double {
            return items.sumOf { it.price * it.quantity }
        }

        fun clearCart() {
            items.clear()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            val view = inflater.inflate(R.layout.fragment_cart, container, false)

            itemListView = view.findViewById(R.id.itemListView)
            viewCartButton = view.findViewById(R.id.viewCartButton)


            val items = listOf(
                Item("Child Minding", 750.00, 1),
                Item("Gardening", 750.00, 1),
                Item("Cooking", 750.00, 1),
                Item("First-Aid",1500.00,1),
                Item("Sewing",1500.00,1),
                Item("Landscaping",1500.00,1),
                Item("Life-Skills",1500.00,1)
            )

            val itemNames = items.map { it.name}
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, itemNames)
            itemListView.adapter = adapter

            itemListView.setOnItemClickListener { _, _, position, _ ->
                val selectedItem = items[position]
                cart.addItem(selectedItem)
                Toast.makeText(requireContext(), "${selectedItem.name} added to cart", Toast.LENGTH_SHORT).show()
            }

            viewCartButton.setOnClickListener {
                showCart()
            }

            return view
        }

        private fun showCart() {
            val itemsInCart = cart.getItems()
            if (itemsInCart.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
            } else {
                val cartDetails = itemsInCart.joinToString("\n") {
                    "${it.name} - ${it.quantity} x R${it.price}"
                }
                val totalPrice = cart. getTotalPrice()
                Toast.makeText(requireContext(), "$cartDetails\nTotal: R$totalPrice", Toast.LENGTH_LONG).show()
            }
        }
}