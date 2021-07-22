package com.blogspot.svdevs.quotes.modal

import com.google.firebase.database.PropertyName

data class Quotes(var quote:String = "",
                  var author:String="",
                  @get: PropertyName("creation_time") @set: PropertyName("creation_time") var creationTime:Long = 0,
                  var user: User? = null)
