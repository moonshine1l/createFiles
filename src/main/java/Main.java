import org.apache.poi.hslf.usermodel.HSLFShapePlaceholderDetails;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


import java.io.*;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        System.out.println("Select the file format: ");
        System.out.println("1.docx\n" + "2.txt\n" + "3.pptx\n" + "4.xlsx");

        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the title: ");

        String name = scanner.nextLine();

        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\user\\IdeaProjects\\gradletest\\src\\main\\resources\\text.properties"));

        String text = properties.getProperty("text");
        String path = properties.getProperty("path");


        switch (num) {
            case  (1):
                String typeFile = properties.getProperty("docx");
                File document = new File(path + name + typeFile);
                try (XWPFDocument docx = new XWPFDocument()) {
                    XWPFParagraph paragraph = docx.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(text);
                    try (FileOutputStream out = new FileOutputStream(document)) {
                        docx.write(out);
                    }
                }
                System.out.println("successfully");
                break;
            case (2):
                typeFile = properties.getProperty("txt");
                document = new File(path + name + typeFile);
                document.createNewFile();
                FileWriter write = new FileWriter(document);
                write.write(text);
                write.flush();
                break;
            case (3):
                typeFile = properties.getProperty("pptx");
                document = new File(path + name + typeFile);
                XMLSlideShow ppt = new XMLSlideShow();

                XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);

                XSLFSlideLayout titleLayout = slideMaster.getLayout(SlideLayout.TITLE);

                XSLFSlide slide = ppt.createSlide(titleLayout);

                XSLFTextShape title = slide.getPlaceholder(0);
                title.setText(text);

                FileOutputStream fos = new FileOutputStream(document);
                ppt.write(fos);
                System.out.println("successfully");
                break;
            case (4):
                typeFile = properties.getProperty("xlsx");
                document = new File(path + name + typeFile);

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("TEXT");
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue(text);

                FileOutputStream out = new FileOutputStream(document);
                workbook.write(out);
                workbook.close();
                break;
            default:
                System.out.println("You made a mistake when entering a number.");
        }

    }

}

