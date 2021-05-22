import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class test_gittigidiyor {
    WebDriverWait wait;
    protected WebDriver driver;
    public static String loginUrl = "https://www.gittigidiyor.com/uye-girisi";
    public static String baseUrl = "https://www.gittigidiyor.com";
//test tamamen calısıyor fakat login olurken
// bot testi yaptıgı için giremiyor program eğer bunu isterse o aşamada kalıyor

    @Before
    public void Start() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\yekta\\Downloads\\Selenium\\ChromeDriver\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);

    }

    @Test
    public void Anasayfa() throws InterruptedException {
        driver.get(baseUrl);


        Assert.assertEquals("https://www.gittigidiyor.com/", driver.getCurrentUrl());


        System.out.println("Sayfa başarıyla yüklendi \n");


        WebElement girisYapAnaButon = driver.findElement(By.xpath("//div[text()='Giriş Yap']"));

        girisYapAnaButon.click();

        Thread.sleep(2000);

        WebElement girisYap = driver.findElement(By.xpath("//a[@data-cy='header-login-button']"));
        girisYap.click();

        String email = "testgg01@hotmail.com";
        driver.findElement(By.cssSelector("#L-UserNameField")).sendKeys(email);
        Thread.sleep(2000);

        String password = "Testgg01";
        driver.findElement(By.cssSelector("#L-PasswordField")).sendKeys(password);
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#gg-login-enter")).click();

        Thread.sleep(3000);

       WebElement kullaniciAdi = driver.findElement(By.xpath("//span[text()='ilyektayakali140391']"));


        Assert.assertEquals("ilyektayakali140391",kullaniciAdi.getText());
        System.out.println("Login işlemi başarıyla gerçekleşti \n");

        WebElement searchArea = driver.findElement(By.xpath("//input[@placeholder='Keşfetmeye Bak']"));
        searchArea.sendKeys("Bilgisayar");
        searchArea.sendKeys(Keys.ENTER);


        WebElement next =   driver.findElement(By.xpath("//a[text()='2']"));
        JavascriptExecutor js=(JavascriptExecutor)driver;


        js.executeScript("arguments[0].scrollIntoView();",next);
        next.click();

        Assert.assertEquals("https://www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2",driver.getCurrentUrl() );

        System.out.println("2. Sayfaya geçilmiştir \n");


        List<WebElement> products = driver.findElements(By.cssSelector("ul.catalog-view>li"));

        int rand = (int) (Math.random()* ((List<?>) products).size());

        products.get(rand).click();



        WebElement ilkfiyat = driver.findElement(By.cssSelector("table.gg-ui-table-striped>:nth-child(2)>:nth-child(3)"));
        String fiyattext = ilkfiyat.getText();
        System.out.println("ilk fiyat   " + ilkfiyat.getText());


        WebElement sepeteEkle =driver.findElement(By.cssSelector("button#add-to-basket"));


        wait.until(ExpectedConditions.visibilityOf(sepeteEkle));
        js.executeScript("window.scrollBy(0,200)");
        sepeteEkle.click();


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Sepete Git']"))).click();//tıklanılabilir olana kadar bekle ve tıkla

        Thread.sleep(3000);

        WebElement sepettekiFiyat = driver.findElement(By.cssSelector("div.total-price>strong"));
        System.out.println("sepet fiyatı   " + sepettekiFiyat.getText());

        String sepetSon = sepettekiFiyat.getText();

        Assert.assertEquals(fiyattext,sepetSon);

        Thread.sleep(2000);

        System.out.println("Fiyatlar aynı");


        WebElement amount = driver.findElement(By.xpath("//select[@class='amount']"));

        Select slc = new Select(amount);
        slc.selectByValue("2");

        Thread.sleep(3000);
        WebElement amount2 = driver.findElement(By.xpath("(//div[@class='gg-d-16 detail-text'])[1]"));

        String amount2s = amount2.getText();


        Thread.sleep(3000);
        Assert.assertTrue(amount2s.contains("2"));
        System.out.println("Sepetinizde bu üründen 2 adet bulunmaktadır");




        driver.findElement(By.xpath("//a[@title='Sil']")).click();
        WebElement emptyMessage = driver.findElement(By.xpath("(//h2)[1]"));
        wait.until(ExpectedConditions.visibilityOf(emptyMessage));
        String emptyMessageActual = emptyMessage.getText();
        String emptyMessageExpected = "Sepetinizde ürün bulunmamaktadır.";

        Assert.assertEquals(emptyMessageExpected,emptyMessageActual);
        Thread.sleep(2000);
        System.out.println("Sepetinizde ürün bulunmamaktadır");

        driver.quit();


    }
}
