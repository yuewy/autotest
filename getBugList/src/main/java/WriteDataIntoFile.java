import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

public class WriteDataIntoFile {

    public static void writeDataIntoFile(List bugID, List bugDescription){
        //开始写入excel,创建模型文件头
        String[] titleA = {"bug_id","description"};
        //创建Excel文件，B库CD表文件
        File fileA = new File("G:\\a.xls");
        if(fileA.exists()){
            //如果文件存在就删除
            fileA.delete();
        }
        try {
            fileA.createNewFile();
            //创建工作簿
            WritableWorkbook workbookA = Workbook.createWorkbook(fileA);
            //创建sheet
            WritableSheet sheetA = workbookA.createSheet("sheet1", 0);
            Label labelA = null;
            //设置列名
            for (int i = 0; i < titleA.length; i++) {
                labelA = new Label(i,0,titleA[i]);
                sheetA.addCell(labelA);
            }
            //获取数据源
            for (int i = 1; i < bugID.size(); i++) {
                labelA = new Label(0,i,bugID.get(i)+" ");
                sheetA.addCell(labelA);
                labelA = new Label(1,i,bugDescription.get(i)+"");
                sheetA.addCell(labelA);
            }
            workbookA.write();//写入数据        
            workbookA.close();//关闭连接
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
