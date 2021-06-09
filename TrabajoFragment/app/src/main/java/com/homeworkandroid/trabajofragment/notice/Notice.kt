package com.homeworkandroid.trabajofragment.notice

data class Notice(val imgUrl:String = "DEFAULT IMG",
                  var titulo:String = "DEFAULT TITULO",
                  var descripcion:String = "DEFAULT DESCRIPCION",
                  var texto:String = "DEFAULT TEXTO",
                  var urlStreaming:String = "DEFAULT URL STREAMING",
                  var coordenadas:String = "DEFAULT COORDENADAS") {
}