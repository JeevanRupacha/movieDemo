package com.demo.moviedemo.core.utils

import org.junit.Test

class GetImageURLTest {
    @Test
    fun get_image_url_from_image_path_default_width(){
        val path = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
        val url = GetImageURL(path)
        assert(url == "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg")
    }

    @Test
    fun get_image_url_from_image_path_custom_width(){
        val path = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
        val url = GetImageURL(path, "w800")
        assert(url == "https://image.tmdb.org/t/p/w800/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg")
    }
}