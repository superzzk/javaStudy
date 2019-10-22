package designpattern.factory.abstractFactory;

import designpattern.factory.simple_factory.Circle;
import designpattern.factory.simple_factory.Rectangle;
import designpattern.factory.simple_factory.Shape;
import designpattern.factory.simple_factory.Square;

public class ShapeFactory extends AbstractFactory  {
    @Override
    public Shape getShape(String shapeType){
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}
