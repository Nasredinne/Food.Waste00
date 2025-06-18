package com.example.foodwasting.classification

import java.util.EnumSet.range

val labels = listOf(
    "fresh apple", "fresh banana", "fresh bitterground", "fresh capsicum", "fresh cucumber", "fresh okra",
    "fresh orange", "fresh potato", "fresh tomato", "rotten apple", "rotten banana", "rotten bitterground", "rotten capsicum",
    "rotten cucumber", "rotten okra", "rotten orange", "rotten potato", "rotten tomato"
)

fun classify(
    probs: FloatArray,
  //  labels: List<String> = labels,
    k: Int = 3
):String {
    val mergedList = labels.zip(probs.toTypedArray())
    return mergedList.sortedByDescending { it.second }
        .take(k)
        .joinToString("\n") { (l, p) -> "%-10s : %.5f %%".format(l, p * 100) }
}
fun getTopClassificationLabel(probs: FloatArray): String {
    // Find the index of the highest probability
    val maxIndex = probs.indices.maxByOrNull { probs[it] } ?: -1
    // Return the corresponding label, or a default if something goes wrong
    return if (maxIndex != -1) labels[maxIndex] else "unknown food"
}