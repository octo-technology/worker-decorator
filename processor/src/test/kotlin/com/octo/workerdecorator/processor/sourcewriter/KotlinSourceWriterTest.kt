package com.octo.workerdecorator.processor.sourcewriter

import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Document
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class KotlinSourceWriterTest {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Test
    fun `Kotlin writer creates a source file in the given folder`() {
        // Given
        val folder = testFolder.newFolder()

        val document = Document("stuff", "DogeDecoration", mock(), mock(), false)
        val source = "much source, wow!"
        val sourceWriter = KotlinSourceWriter(folder)

        // When
        sourceWriter.write(document, source)

        // Then
        val packageFolder = File(folder, "stuff")
        val writtenFile = File(packageFolder, "DogeDecoration.kt").readText()
        assertThat(writtenFile).isEqualTo(source)
    }
}