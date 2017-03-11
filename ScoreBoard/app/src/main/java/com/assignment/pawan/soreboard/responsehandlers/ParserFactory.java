package com.assignment.pawan.soreboard.responsehandlers;

public class ParserFactory {


    public static BaseParser getParser(Class className) {
        BaseParser baseParser = null;
        try {

            baseParser = (BaseParser) Class.forName(className.getName())
                    .newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return baseParser;

    }

}
