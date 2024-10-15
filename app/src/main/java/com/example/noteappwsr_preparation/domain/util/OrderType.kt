package com.example.noteappwsr_preparation.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}