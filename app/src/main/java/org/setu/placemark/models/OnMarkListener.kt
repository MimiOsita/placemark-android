package org.setu.placemark.models

interface OnMarkListener {

    fun onMarkClick(mark: PlacedMark)
    fun onMarkLongClick(mark: PlacedMark): Boolean
}