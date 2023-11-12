import Utility.BaseDriver;
import Utility.Fonksiyon;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

public class TechnoStudy_Testing extends BaseDriver {
    @Test
    public void testViewCoursesDropdownOnHomePage() throws Exception {
        driver.get("https://techno.study/tr/");
        Fonksiyon.bekle(2);
        takeScreenshot(driver);

        WebElement kurslar = driver.findElement(By.xpath("//a[@class='t-menu__link-item t966__tm-link']"));
        Actions aksiyon = new Actions(driver);
        aksiyon.moveToElement(kurslar).perform();
        Fonksiyon.bekle(2);
        takeScreenshot(driver);

        WebElement yeniPencere = driver.findElement(By.xpath("//div[@class='t966__content']"));
        aksiyon.moveToElement(yeniPencere).perform();
        Fonksiyon.bekle(2);
        takeScreenshot(driver);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", yeniPencere);
        Fonksiyon.bekle(2);

        WebElement master = driver.findElement(By.xpath("//div[@field='li_title__1663426353172']"));
        master.click();
        Fonksiyon.bekle(2);
        takeScreenshot(driver);

        String actualURL = driver.getCurrentUrl();
        String expectedURL = "https://techno.study/masters";
        Assert.assertTrue(expectedURL.equals(actualURL), "URL Doğrulanamadı: " + actualURL);

        BekleVeKapat();
    }

    public static void takeScreenshot(WebDriver driver) throws Exception {
        SimpleDateFormat tarihFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        TakesScreenshot ss = (TakesScreenshot) driver;
        String tarih = tarihFormat.format(date);
        File screenShot = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, new File("src/US_TST_01/screenshots/screenshot_" + tarih + ".png"));
    }

    @Test
    public void Anasayfadan_Campus_Platformuna_Giris_Yapabilme() {
        driver.get("https://techno.study/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//img[@class='tn-atom__img t-img loaded'])[1]")));
        ScreenshotCapture();

        WebElement signIn = driver.findElement(By.linkText("SIGN IN"));
        Assert.assertTrue(signIn.isDisplayed(), "Login functionality not found!");
        signIn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[loading='eager']")));

        Assert.assertTrue(driver.getCurrentUrl().contains("https://campus.techno.study"),
                "CAMPUS GİRİŞ SAYFASI DIŞINDA BİR SAYFAYA YÖNLENDİRME MEVCUT");
        ScreenshotCapture();

        BekleVeKapat();
    }

    public void ScreenshotCapture() {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.YYYY-hh.mm.ss");
        LocalDateTime dt = LocalDateTime.now();


        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File hafizadakiHali = ts.getScreenshotAs(OutputType.FILE);

            String dosyaYolu = "src\\US_TST_02\\ekranGoruntuleri\\" + dt.format(f) + ".png";
            FileUtils.copyFile(hafizadakiHali, new File(dosyaYolu));

        } catch (Exception ex) {
            System.out.print("ex.getMessage() = " + ex.getMessage());
            System.out.println("EKRAN GÖRÜNTÜSÜ ALMA HATASI");
        }
    }

    @Test
    void testApplyForCourseWithHomepageApplyButton() {

        driver.get("https://techno.study/tr/");


        WebElement basVurBtn = driver.findElement(By.xpath("(//td[text()='BAŞVUR'])[1]"));
        basVurBtn.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement bizeUlas = driver.findElement(By.xpath("//strong[text()='Bize ulaşın!']"));
        js.executeScript("arguments[0].scrollIntoView(true);", bizeUlas);

        WebElement firtName = driver.findElement(By.xpath("//input[@placeholder='Adı Soyadı']"));
        firtName.clear();

        firtName.sendKeys("Test Braby");


        WebElement e_mail = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        e_mail.sendKeys("Test_Team12_@gmail.com");


        WebElement phone = driver.findElement(By.xpath("//input[@type='tel']"));
        phone.sendKeys("99999999999");


        WebElement country = driver.findElement(By.xpath("//select[@name='country']"));
        country.click();


        Select ddMenu = new Select(country);
        ddMenu.selectByIndex(4);


        WebElement course = driver.findElement(By.xpath("//select[@name='course']"));
        course.click();

        Select ddMenu1 = new Select(course);
        ddMenu1.selectByIndex(1);

        WebElement nrdDuydun = driver.findElement(By.xpath("//select[@name='survey']"));
        Select ddMenu2 = new Select(nrdDuydun);
        ddMenu2.selectByIndex(1);

        WebElement prmsCd = driver.findElement(By.xpath("//input[@name='promo code']"));
        prmsCd.sendKeys("12345");

        WebElement checkBox = driver.findElement(By.xpath("//span[text()='Şartları']"));
        checkBox.click();
        Fonksiyon.bekle(2);

        WebElement bsvrBtnClick = driver.findElement(By.xpath("//button[@type='submit']"));
        bsvrBtnClick.click();


        WebElement iframe = driver.findElement(By.id("captchaIframeBox"));
        driver.switchTo().frame(iframe);

        WebElement iframe1 = driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']"));
        driver.switchTo().frame(iframe1);
        Fonksiyon.bekle(10);
        WebElement rbtChckBox = driver.findElement(By.xpath("//span[@id='recaptcha-anchor']"));
        rbtChckBox.click();

        Fonksiyon.bekle(20);


        WebElement bsvrMsg = driver.findElement(By.xpath("(//div/p/span[text()='Başvurunuz alınmıştır. Bilgilendirme emailinize gönderilmiştir.'])[2]"));
        System.out.println(bsvrMsg.getText());

        Assert.assertTrue(bsvrMsg.getText().contains("Başvurunuz "));


    }

    @Test
    public void testAccessCoursesFromSubmenu() throws Exception {

        driver.get("https://techno.study/tr");
        Fonksiyon.bekle(2);

        WebElement kurslarText = driver.findElement(By.cssSelector("[class='t-menu__link-item t966__tm-link']"));
        Assert.assertTrue(kurslarText.getText().equals("KURSLAR"), "KURSLAR ana sayfada gorunmedi");
        takeScreenShot(driver);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Fonksiyon.bekle(1);
        takeScreenShot(driver);

        WebElement kurs1 = driver.findElement(By.linkText("SDET Yazılım Test Mühendisi"));
        kurs1.click();
        Fonksiyon.bekle(1);
        takeScreenShot(driver);

        WebElement kurs1IsimKontrol = driver.findElement(By.xpath("//div[text()='Yazılım Test Mühendisi']"));
        Assert.assertTrue(kurs1IsimKontrol.getText().equals("Yazılım Test Mühendisi"));

        WebElement kurs1BilgiKontrol = driver.findElement(By.linkText("Detaylı bilgi"));
        Assert.assertTrue(kurs1BilgiKontrol.getText().equals("Detaylı bilgi"));
        Fonksiyon.bekle(1);

        driver.navigate().back();

        WebElement kurs2 = driver.findElement(By.linkText("Android Uygulama Geliştirme"));
        kurs2.click();
        Fonksiyon.bekle(1);
        takeScreenShot(driver);

        WebElement kurs2IsimKontrol = driver.findElement(By.xpath("//div[@class='tn-atom']/p"));
        Assert.assertTrue(kurs2IsimKontrol.getText().equals("Android uygulama geliştirme"));
        Fonksiyon.bekle(1);

        WebElement kurs2BilgiKontrol = driver.findElement(By.linkText("Detaylı bilgi"));
        Assert.assertTrue(kurs2BilgiKontrol.getText().equals("Detaylı bilgi"));
        Fonksiyon.bekle(1);

        driver.navigate().back();

        WebElement kurs3 = driver.findElement(By.linkText("Veri bilimi"));
        kurs3.click();
        Fonksiyon.bekle(1);
        takeScreenShot(driver);

        WebElement kurs3IsimKontrol = driver.findElement(By.xpath("//div[text()='Veri Bilimi Bootcamp']"));
        Assert.assertTrue(kurs3IsimKontrol.getText().equals("Veri Bilimi Bootcamp"));
        Fonksiyon.bekle(1);

        WebElement kurs3BilgiKontrol = driver.findElement(By.linkText("Detaylı bilgi"));
        Assert.assertTrue(kurs3BilgiKontrol.getText().equals("Detaylı bilgi"));
        Fonksiyon.bekle(1);

        driver.navigate().back();

        WebElement kurs4 = driver.findElement(By.linkText("Master's Program"));
        kurs4.click();
        Fonksiyon.bekle(1);
        takeScreenShot(driver);

        WebElement kurs4IsimKontrol = driver.findElement(By.xpath("//h1[@class='tn-atom']"));
        Assert.assertTrue(kurs4IsimKontrol.getText().equals("Master's Degree"));
        Fonksiyon.bekle(1);

        WebElement kurs4BilgiKontrol = driver.findElement(By.linkText("Learn more"));
        Assert.assertTrue(kurs4BilgiKontrol.getText().equals("Learn more"));
        Fonksiyon.bekle(1);

        BekleVeKapat();

    }

    public static void takeScreenShot(WebDriver driver) throws Exception {
        SimpleDateFormat tarihFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        TakesScreenshot ss = (TakesScreenshot) driver;
        String tarih = tarihFormat.format(date);
        File screenShot = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, new File("src/US_TST_04/screenshots/screenshot_" + tarih + ".png"));
    }

    @Test
    public void socialMenu() {
        driver.get("https://techno.study/tr");

        WebElement submenu = driver.findElement(By.cssSelector(".t420__logo.t-title"));
        String SubmenuText = submenu.getText();
        Assert.assertTrue(SubmenuText.contains("Techno Study"));

        WebElement facebook = driver.findElement(By.cssSelector("li.t-sociallinks__item_facebook>a[aria-label='facebook']"));
        Assert.assertNotNull(facebook);
        Assert.assertTrue(facebook.isEnabled() && facebook.isDisplayed(), "facebook ikonu tiklanabilir değil veya görünmez.");


        WebElement instagram = driver.findElement(By.cssSelector("li.t-sociallinks__item_instagram > a[aria-label='instagram']"));
        Assert.assertNotNull(instagram);
        Assert.assertTrue(instagram.isEnabled() && instagram.isDisplayed(), "Instagram ikonu tiklanabilir değil veya görünmez.");

        WebElement youtube = driver.findElement(By.cssSelector("li.t-sociallinks__item_youtube > a[aria-label='youtube']"));
        Assert.assertNotNull(youtube);
        Assert.assertTrue(youtube.isEnabled() && youtube.isDisplayed(), "youtube ikonu tiklanabilir değil veya görünmez.");

        WebElement linkedin = driver.findElement(By.cssSelector("li.t-sociallinks__item_linkedin > a[aria-label='linkedin']"));
        Assert.assertNotNull(linkedin);
        Assert.assertTrue(linkedin.isEnabled() && linkedin.isDisplayed(), "linkedin ikonu tiklanabilir değil veya görünmez.");

        BekleVeKapat();
    }

    @Test
    public void TechoStudyLogo_Test() throws Exception {
        driver.get("https://techno.study/tr/");
        Fonksiyon.bekle(2);
        takeScreenshoT(driver);

        WebElement tstLogo = driver.findElement(By.cssSelector("[class='t228__imglogo ']"));
        tstLogo.click();

        WebElement ekranYazisiGorme = driver.findElement(By.cssSelector("a[class='tn-atom js-click-zero-stat']"));
        Assert.assertTrue(ekranYazisiGorme.getText().equals("APPLY NOW"));
        takeScreenshoT(driver);

        BekleVeKapat();
    }

    public static void takeScreenshoT(WebDriver driver) throws Exception {
        SimpleDateFormat tarihFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        TakesScreenshot ss = (TakesScreenshot) driver;
        String tarih = tarihFormat.format(date);
        File screenShot = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, new File("src/US_TST_06/screenshots/screenshot_" + tarih + ".png"));
    }

    @Test
    public void DetayliBilgi_Test() throws Exception {
        driver.get("https://techno.study/tr/");
        Fonksiyon.bekle(2);
        TakeScreenshot(driver);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 1900);");
        Fonksiyon.bekle(2);
        TakeScreenshot(driver);

        WebElement detayliBilgi = driver.findElement(By.cssSelector("a[class='tn-atom'][href='https://techno.study/tr/data']"));
        detayliBilgi.click();
        TakeScreenshot(driver);

        WebElement detayliBilgiSayfa = driver.findElement(By.cssSelector("a[class='tn-atom'][href='#rec516068360']"));
        Assert.assertTrue(detayliBilgiSayfa.isDisplayed(), "Detaylı Bilgi gözükmedi! =");
        TakeScreenshot(driver);

        BekleVeKapat();

    }

    public static void TakeScreenshot(WebDriver driver) throws Exception {
        SimpleDateFormat tarihFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        TakesScreenshot ss = (TakesScreenshot) driver;
        String tarih = tarihFormat.format(date);
        File screenShot = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, new File("src/US_TST_21/screenshots/screenshot_" + tarih + ".png"));
    }

    @Test
    public void Kullanim_Sartlari_Sayfasina_Erisebilme() {
        driver.get("https://techno.study/tr");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='t-checkbox__labeltext']>span")));

        WebElement sartlar = driver.findElement(By.cssSelector("span[class='t-checkbox__labeltext']>span"));
        js.executeScript("arguments[0].scrollIntoView(true);", sartlar);

        WebElement checkBox = driver.findElement(By.cssSelector("div[class='t-checkbox__indicator']"));
        checkBox.click();

        Fonksiyon.bekle(2);
        ScreenshotCapture();
        sartlar.click();
        BekleVeKapat();
    }

    @Test
    public void Kullanim_Sartlari_Sayfasina_ErisebilmeGlobal() {
        driver.get("https://techno.study");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[text()='Terms of Use'])[1]")));

        WebElement termsOfUse = driver.findElement(By.xpath("(//a[text()='Terms of Use'])[1]"));
        js.executeScript("arguments[0].scrollIntoView(true);", termsOfUse);

        WebElement checkBox = driver.findElement(By.cssSelector("div[class='t-checkbox__indicator']"));
        checkBox.click();

        ScreenshotCapture();
        termsOfUse.click();

        String anasayfaWindowId = driver.getWindowHandle();
        Set<String> windowsIdler = driver.getWindowHandles();
        for (String id : windowsIdler) {
            if (id.equals(anasayfaWindowId)) continue;
            {
                driver.switchTo().window(id);
            }
        }

        Fonksiyon.bekle(2);
        ScreenshotCapture();
        BekleVeKapat();
    }
}
