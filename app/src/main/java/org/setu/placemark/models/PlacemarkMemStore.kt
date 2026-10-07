package org.setu.placemark.models

import java.util.concurrent.atomic.AtomicLong

class PlacemarkMemStore {
    private val placemarks = ArrayList<PlacedMark>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<PlacedMark> {
        return placemarks
    }

    fun create(placemark: PlacedMark) {
        placemark.id = lastId.incrementAndGet()
        placemarks.add(placemark)
    }

    fun update(placemark: PlacedMark): Boolean {
        val foundPlacedMark = findOne(placemark.id)
        return if (foundPlacedMark != null) {
            foundPlacedMark.title = placemark.title
            foundPlacedMark.description = placemark.description
            foundPlacedMark.x = placemark.x
            foundPlacedMark.y = placemark.y
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundPlacedMark = findOne(id)
        return if (foundPlacedMark != null) {
            placemarks.remove(foundPlacedMark)
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): PlacedMark? {
        return placemarks.find { p -> p.id == id }
    }
}