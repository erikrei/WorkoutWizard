package com.example.workoutwizard.helper

fun percentageTwoNumbers(
    numbers: Pair<Int, Int>
): Pair<String, Double> {
    val percentageValue = (numbers.first.toDouble() / numbers.second.toDouble()) * 100
    return Pair(String.format("%.1f", percentageValue), percentageValue)
}