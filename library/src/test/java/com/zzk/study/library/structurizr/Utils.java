package com.zzk.study.library.structurizr;

import com.structurizr.io.plantuml.BasicPlantUMLWriter;
import com.structurizr.io.plantuml.C4PlantUMLWriter;
import com.structurizr.io.plantuml.PlantUMLWriter;
import com.structurizr.view.View;

public class Utils {
    // 输出适配学城
    public static String getC4StringForXuecheng(View view) {
        PlantUMLWriter plantUMLWriter = new C4PlantUMLWriter();
        plantUMLWriter.clearIncludes();
        plantUMLWriter.addIncludeFile("<C4/C4_Component>");
        final String c4String = plantUMLWriter.toString(view);
        return c4String;
    }

    public static String getC4String(View view) {
        PlantUMLWriter plantUMLWriter = new C4PlantUMLWriter();
        final String c4String = plantUMLWriter.toString(view);
        return c4String;
    }

    public static String getPlantUmlStr(View view) {
        PlantUMLWriter writer = new BasicPlantUMLWriter();
        return writer.toString(view);
    }
}
