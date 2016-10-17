package com.axsoftware.test.write;


import com.axsoftware.jsoneutral.file.FieldSeparator;
import com.axsoftware.jsoneutral.file.writer.FileJsonWriter;
import com.axsoftware.jsoneutral.file.writer.FileJsonWriterImpl;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    private static final String CONFIG_JSON = "board.json";

    private BoardHeader createHeader() {
        final BoardHeader header = new BoardHeader();
        header.setDate("01102016");
        header.setMessage("foobar");
        header.setCode("32");
        return header;
    }

    private BoardContent createContent() {
        final BoardContent content = new BoardContent();
        content.setCode("01");
        content.setMessage("couse");
        content.setRegister("42");
        return content;
    }

    @Test
    public void writeObject() throws Exception {
        final FileJsonWriter writer = new FileJsonWriterImpl();
        final List<String> records = new ArrayList<>();
        writer.setFieldSeparator(new FieldSeparator(";"));

        final BoardHeader header = createHeader();
        final String headerData = writer.execute(header, CONFIG_JSON, BoardHeader.ID);
        records.add(headerData);

        final BoardContent content = createContent();
        final String contentData = writer.execute(content, CONFIG_JSON, BoardContent.ID);
        records.add(contentData);

        final File file = writer.generateFile(records, "");
        assertTrue(file.exists());

        final Path expectedPath = new File(getClass().getClassLoader().getResource("board.csv").getFile()).toPath();
        assertTrue(Files.exists(expectedPath));
        assertTrue(Files.isRegularFile(expectedPath));
        final String expectedContents = new String(Files.readAllBytes(expectedPath));
        final String contents = new String(Files.readAllBytes(file.toPath()));
        assertEquals(expectedContents, contents);
    }
}
