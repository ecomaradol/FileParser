
import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selectors.byText;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParsingTest {
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void pasrePdfTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide");
        File PdfDownload = Selenide.$(byText("PDF download")).download();
        PDF pdf= new PDF(PdfDownload);
        assertThat(pdf.author).contains("Marc");
    }

    @Test
    void parseExcelTest() throws Exception {
        Selenide.open("http://romashka2008.ru/price");
        File xlsDownload = Selenide.$(".site-main__inner a[href*='prajs_ot']").download();
        XLS xls = new XLS(xlsDownload);
        System.out.println("");
      //  assertThat(xls.excel
      //          .getSheetAt(0).getRow(11).getCell(1).getStringCellValue()).contains("Сахалин");

    }

    @Test
    void parseCSVTest() throws Exception {
       // ClassLoader classLoader = getClass().getClassLoader();   //first varinant
         ClassLoader classLoader = FileParsingTest.class.getClassLoader(); // second variant
           try(InputStream is = classLoader.getResourceAsStream("files/username.csv");
              CSVReader reader = new CSVReader(new InputStreamReader(is))) {
             List<String[]> content= reader.readAll();
             assertThat(content.get(0)).contains("Username; Identifier;First name;Last name");
         }
    }
    @Test
    void parseZipTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("files/NYU Langone Advanced Access App (PC).zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).isEqualTo("BIGIPEdgeClient.exe");
            }
        }
    }
        @Test
        void parseJson() throws Exception {
            Gson gson = new Gson();
            try (InputStream is = classLoader.getResourceAsStream("files/response.json")) {
                String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
                assertThat(jsonObject.get("metadata").getAsString().contains("test"));
                assertThat(jsonObject.get("status").getAsJsonObject().get("code").getAsString().contains("28200"));
            }

        }
}
