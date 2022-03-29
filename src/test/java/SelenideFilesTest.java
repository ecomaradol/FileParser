import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFilesTest {

    @Test
    void selenideDownloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedFile = Selenide.$("#raw-url").download();
        try (InputStream is = new FileInputStream(downloadedFile)) {
            assertThat(new String(is.readAllBytes(), StandardCharsets.UTF_8)).contains("This repository");
        }

    }

    @Test
    void selenideUploadTest() {
        Selenide.open("https://the-internet.herokuapp.com/upload");
        Selenide.$("input[type='file']")
                //             .uploadFile(new File("C:/Users/Zlata/IdeaProjects/ParseJson/src\test\resources\files\1.txt")); //bad practice
                .uploadFromClasspath("files/1.txt");
        Selenide.$("#file-submit").click();
        Selenide.$("div.example").shouldHave(text("File Uploaded!"));
        Selenide.$("#uploaded-files").shouldHave(text("1.txt"));
    }
}
