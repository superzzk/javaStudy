package com.zzk.study.library.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/6/2 11:34 PM
 */
@Slf4j
public class ReadDemo {


    @Test
    public void simpleRead() {
        String fileName = this.getClass().getResource("/").getPath() + "easyexcel" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).sheet().headRowNumber(1).doRead();
    }

    @Test
    public void headerRead() {
        String fileName = this.getClass().getResource("/").getPath() + "easyexcel" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoHeadDataListener()).sheet().doRead();
    }

    /**
     * 读取公式和单元格类型
     */
    @Test
    public void cellDataRead() {
        String fileName = this.getClass().getResource("/").getPath() + "easyexcel" + File.separator + "cellDataDemo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, CellDataReadDemoData.class, new CellDataDemoHeadDataListener()).sheet().doRead();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class DemoData {
        private String string;
        private Date date;
        private Double doubleData;
        private String scienceNumber;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class CellDataReadDemoData {
        private CellData<String> string;
        // 这里注意 虽然是日期 但是 类型 存储的是number 因为excel 存储的就是number
        private CellData<Date> date;
        private CellData<Double> doubleData;
        // 这里并不一定能完美的获取 有些公式是依赖性的 可能会读不到 这个问题后续会修复
        private CellData<String> formulaValue;
        private CellData<String> scienceNumber;
    }

    public static class CellDataDemoHeadDataListener implements ReadListener<CellDataReadDemoData> {
        /**
         * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 100;

        private List<CellDataReadDemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

        @Override
        public void invoke(CellDataReadDemoData data, AnalysisContext context) {
            log.info("解析到一条数据:{}", JSON.toJSONString(data));
            log.info("科学数字getData:{}", data.getScienceNumber().getData());
            log.info("科学数字getType:{}", data.getScienceNumber().getType());
            log.info("科学数字getNumberValue:{}", data.getScienceNumber().getNumberValue());
            log.info("科学数字getStringValue:{}", data.getScienceNumber().getStringValue());
            if (cachedDataList.size() >= BATCH_COUNT) {
                saveData();
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            saveData();
            log.info("所有数据解析完成！");
        }

        /**
         * 加上存储数据库
         */
        private void saveData() {
            log.info("{}条数据，开始存储数据库！", cachedDataList.size());
            log.info("存储数据库成功！");
        }
    }
}
