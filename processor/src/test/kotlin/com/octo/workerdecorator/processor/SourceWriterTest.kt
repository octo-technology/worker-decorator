package com.octo.workerdecorator.processor

import com.google.testing.compile.CompilationRule
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Document
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class SourceWriterTest {

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Test
    fun `Source writer writes the given string through the filer`() {
        // Given
        val folder = testFolder.newFolder()

        val document = Document("stuff", "DogeDecoration", mock(), mock())
        val source = "much source, wow!"
        val sourceWriter = SourceWriter(folder)

        // When
        sourceWriter.write(document, source)

        // Then
        val writtenFile = File(folder, "DogeDecoration.kt").readText()
        assertThat(writtenFile).isEqualTo(source)
    }
}