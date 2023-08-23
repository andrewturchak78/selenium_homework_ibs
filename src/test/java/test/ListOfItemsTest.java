package test;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class ListOfItemsTest {
    WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/food");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void addVegetableNotExotic() {

        driver.findElement(By.xpath("//button[text()='Добавить']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.id("name")).sendKeys("Картофель");
        driver.findElement(By.id("type")).click();
        driver.findElement(By.cssSelector("[value='VEGETABLE']")).click();
        driver.findElement(By.id("save")).click();
        Assertions.assertEquals("Картофель",
                driver.findElement(By.xpath("//td[text()='Картофель']")).getText());
        Assertions.assertEquals("5", driver.findElement(By.xpath("//th[text()='5']")).getText());
        Assertions.assertEquals("Овощ",
                driver.findElements(By.xpath("//tr/td[text()= 'Овощ']")).get(2).getText());
        Assertions.assertEquals("false",
                driver.findElements(By.xpath("//tr/td[text()= 'false']")).get(3).getText());

    }

    @Test
    void addFruitExotic() {
        driver.findElement(By.xpath("//button[text()='Добавить']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.id("name")).sendKeys("Ананас");
        driver.findElement(By.id("type")).click();
        driver.findElement(By.cssSelector("[value='FRUIT']")).click();
        driver.findElement(By.id("exotic")).click();
        driver.findElement(By.id("save")).click();
        Assertions.assertEquals("Ананас",
                driver.findElement(By.xpath("//td[text()='Ананас']")).getText());
        Assertions.assertEquals("5", driver.findElement(By.xpath("//th[text()='5']")).getText());
        Assertions.assertEquals("Фрукт",
                driver.findElements(By.xpath("//tr/td[text()= 'Фрукт']")).get(2).getText());
        Assertions.assertEquals("true",
                driver.findElements(By.xpath("//tr/td[text()= 'true']")).get(1).getText());

    }
}
