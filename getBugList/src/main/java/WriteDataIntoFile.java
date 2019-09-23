import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.List;

public class WriteDataIntoFile {
    private static String img;

    public static void writeDataIntoFile(List bugID, List bugDescription,List bugImgUrl,List bugStatus) throws Exception{

        //开始写入excel,创建模型文件头
        String[] titleA = {"编号","问题描述","截图","状态"};
        //创建Excel文件，B库CD表文件
        File fileA = new File("C:\\Users\\201917687\\Desktop\\GMP\\GMP备份文件0923\\GMP.xlsx");
        //File fileA = new File("D:\\GMP\\GMP.xlsx");
        if(fileA.exists()){
            //如果文件存在就删除
            fileA.delete();
        }
        fileA.createNewFile();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("sheet");
        XSSFRow row = spreadsheet.createRow((short)0);
        XSSFCell cell;
        CreationHelper createHelper = workbook.getCreationHelper();
        //设置超链接字体样式
        XSSFCellStyle hlinkstyle = workbook.createCellStyle();
        XSSFFont hlinkfont = workbook.createFont();
        hlinkfont.setUnderline(XSSFFont.U_SINGLE);
        hlinkfont.setColor(HSSFColor.BLUE.index);
        hlinkstyle.setFont(hlinkfont);

        //Hyperlink to a file in the current directory
        XSSFHyperlink link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);


        //设置列名
        for (int i = 0; i < titleA.length; i++) {
            cell = row.createCell(i+0);
            cell.setCellValue(titleA[i]);
        }
        //获取数据源
        for (int i = 0; i < bugID.size(); i++) {
            row= spreadsheet.createRow((short)(i+1));
            cell = row.createCell(0);
            cell.setCellValue(bugID.get(i)+"");
            cell = row.createCell(1);
            cell.setCellValue(bugDescription.get(i)+"");
            String imgUrl = bugImgUrl.get(i)+"";
            if (imgUrl.contains("http")){
                saveImage(imgUrl,bugID.get(i)+"");
                cell = row.createCell(2);
                cell.setCellValue(bugID.get(i)+"");
                link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_FILE);
                link.setAddress(bugID.get(i)+".png");
                cell.setHyperlink(link);
                cell.setCellStyle(hlinkstyle);
            }
            cell = row.createCell(3);
            cell.setCellValue(bugStatus.get(i)+"");
        }
        //调整列宽
        int width=95;
        spreadsheet.setColumnWidth(1, 256*width+184);
        FileOutputStream out = new FileOutputStream(fileA);
        workbook.write(out);
        out.flush();
        out.close();
    }

    public static void saveImage(String imgUrl,String bugId) throws Exception{
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(imgUrl);
        HttpResponse response = httpClient.execute(httpget);

        // Get hold of the response entity
        HttpEntity entity = response.getEntity();
        InputStream stream= entity.getContent();
        BufferedInputStream bis = new BufferedInputStream(stream);
        img = "C:\\Users\\201917687\\Desktop\\GMP\\GMP备份文件0923\\"+bugId+".png";
        //img = "D:\\GMP\\"+bugId+".png";
        OutputStream writerStrem = new FileOutputStream(img);
        BufferedOutputStream bos = new BufferedOutputStream(writerStrem);
        int len = 0;
        byte[] bytes =new byte[1024];
        while ((len=bis.read(bytes)) != -1) {
            bos.write(bytes,0,len);
        }
        bis.close();
        bos.close();
    }
}
