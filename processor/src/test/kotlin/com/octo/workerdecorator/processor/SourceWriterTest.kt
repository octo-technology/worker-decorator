package com.octo.workerdecorator.processor

import com.google.testing.compile.CompilationRule
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Document
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Answers.RETURNS_DEEP_STUBS
import javax.annotation.processing.Filer

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
        val file = testFolder.newFile()

        val document = Document("DogeDecoration", mock())
        val source = "much source, wow!"

        val filer: Filer = mock(defaultAnswer = RETURNS_DEEP_STUBS)
        val sourceWriter = SourceWriter(filer)

        given(filer.createSourceFile("DogeDecoration").openWriter())
                .willReturn(file.writer())

        // When
        sourceWriter.write(document, source)

        // Then
        val writtenFile = file.readText()
        assertThat(writtenFile).isEqualTo(source)
    }
}