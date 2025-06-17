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
    var mergedList = mutableListOf<Pair<String, Float>>()
    for (i in probs.indices) {
        mergedList.add(Pair(labels[i], probs[i]))
    }
    /* var x = probs
        .mapIndexed { i, p -> labels[i] to p }
        .sortedByDescending { it.second }
        .take(k)
        .joinToString("\n") { (l, p) -> "%-10s : %.2f %%".format(l, p * 100) }
    return x

     */
    var x =  mergedList.sortedByDescending { it.second }
        .take(k)
        .joinToString("\n") { (l, p) -> "%-10s : %.5f %%".format(l, p * 100) }

    return x
}