package com.zzk.study.library.jfree;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.Test;

import java.awt.*;

public class Demo {
    @Test
    public void demo() {
        SVGGraphics2D g = new SVGGraphics2D(110, 100);
        g.translate(10, 20);
        g.setColor(Color.RED);
        g.fillRect(10, 10, 100, 100);
        g.clip(new Rectangle(0, 0, 60, 60));
        g.setPaint(Color.BLUE);
        g.fillRect(10, 10, 100, 100);
        g.setClip(null);
        g.setPaint(Color.GREEN);
        g.fillRect(60, 60, 50, 50);

        System.out.println(g.getSVGDocument());
    }



}
