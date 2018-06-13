package com.octo.workerdecorator.processor.sourcewriter

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.DecorationDocument
import com.octo.workerdecorator.processor.entity.Document
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Answers.RETURNS_DEEP_STUBS
import javax.annotation.processing.Filer

class JavaSourceWriterTest {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Test
    fun `Java writer creates a source file with the given Filer`() {
        // Given
        val file = testFolder.newFile()
        val filer: Filer = mock(defaultAnswer = RETURNS_DEEP_STUBS)
        given(filer.createSourceFile("stuff.DogeDecoration").openWriter())
                .willReturn(file.writer())

        val document = mock<Document>()
        given(document.`package`).willReturn("stuff")
        given(document.name).willReturn("DogeDecoration")

        val source = "much source, wow!"
        val sourceWriter = JavaSourceWriter(filer)

        // When
        sourceWriter.write(document, source)

        // Then
        assertThat(file.readText()).isEqualTo(source)
    }
}