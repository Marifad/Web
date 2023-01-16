package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardApplicationTest {

    private WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestPositivePath() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72345678901");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeNameFieldNumbers() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин2");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72345678901");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }


    @Test
    void shouldTestNegativeNameFieldLatin() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Evgeniy");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72345678901");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeNameFieldEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72345678901");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeTelLess() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7234567890");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeTelMore() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+723456789012");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }


    @Test
    void shouldTestNegativeTelNoPlus() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79012345678");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeTelEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeBothEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestNegativeCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгений Бабушкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+72345678901");
        driver.findElement(By.className("button_view_extra")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }
}
