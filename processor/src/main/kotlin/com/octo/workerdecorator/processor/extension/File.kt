package com.octo.workerdecorator.processor.extension

import java.io.File

fun File.children(fileName: String, `package`: String): File {
    var folder = this
    `package`.split(".").forEach {
        folder = File(folder, it)
    }
    folder.mkdirs()

    return File(folder, fileName)
}