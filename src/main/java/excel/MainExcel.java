package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainExcel {

    public static void main(String[] args) throws IOException {
        WebDriver driver = setUp();
        driver.get("https://iotvega.com/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div[2]/ul/li[2]/a")).click();
        List<IotEntity> entities = getEntities(driver);
        createExcelFile(entities);
    }

    private static WebDriver setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        return new ChromeDriver();
    }

    private static List<IotEntity> getEntities(WebDriver driver) {
        List<IotEntity> entities = new LinkedList<>();
        List<WebElement> webElements3 = driver.findElements(By.cssSelector("a.main-container"));
        webElements3.forEach(webElement -> {
            String name = webElement.findElement(By.cssSelector("h2")).getText();
            List<String> features = webElement.findElement(By.cssSelector("div.product-features"))
                    .findElement(By.tagName("ul"))
                    .findElements(By.tagName("li"))
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            String price = webElement.findElement(By.cssSelector("div.product-cost"))
                    .findElement(By.cssSelector(".price_item")).getText();
            entities.add(new IotEntity(name, price, features));
        });
        return entities;
    }

    private static void createExcelFile(List<IotEntity> entities) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("iot");
        Row header = sheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Название");
        headerCell = header.createCell(1);
        headerCell.setCellValue("Цена");
        headerCell = header.createCell(2);
        headerCell.setCellValue("Опции");
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (int i = 1; i < entities.size(); i++) {
            IotEntity entity = entities.get(i);
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(entity.getName());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(entity.getPrice());

            cell = row.createCell(2);
            cell.setCellValue(String.join(", ", entity.getFeature()));
        }
        FileOutputStream outputStream = new FileOutputStream("excel/result.xlsx");
        workbook.write(outputStream);
    }
}
