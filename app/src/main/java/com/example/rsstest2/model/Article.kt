package com.example.rsstest2.model

 data class Article(
     var title: String? = "Titel Saknas",
     var description: String? = "Beskrivning Saknas",
     var date: String? = "Datum Saknas",
     var imageUrl: String? = "Bild Saknas",
     var content: String? = "Text Saknas",
     var link: String? = "Länk Saknas"
 )