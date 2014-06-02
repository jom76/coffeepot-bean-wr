/*
 * Copyright 2013 - Jeandeson O. Merelis
 */
package coffeepot.bean.wr.typeHandler;

/*
 * #%L
 * coffeepot-bean-wr
 * %%
 * Copyright (C) 2013 Jeandeson O. Merelis
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handler default for Date type.
 * Parameters supported by this handler: date, time, datetime, pattern or direct pattern.
 * Multiple params are not supported.
 * @author Jeandeson O. Merelis
 */
public class DefaultDateHandler implements TypeHandler<Date> {

    protected SimpleDateFormat dateFormat;
    protected static final String DEFAULT;
    protected static String patternDefault;
    protected static String patternForDate;
    protected static String patternForTime;
    protected static String patternForDateTime;

    static {
        DEFAULT = "yyyy-MM-dd'T'HH:mm:ss";
        patternDefault = DEFAULT;
        patternForDate = "yyyy-MM-dd";
        patternForTime = "HH:mm:ss";
        patternForDateTime = DEFAULT;
    }

    public DefaultDateHandler() {
        if (patternDefault == null) {
            dateFormat = new SimpleDateFormat(DEFAULT);
        } else {
            dateFormat = new SimpleDateFormat(patternDefault);
        }
    }

    @Override
    public Date parse(String text) throws HandlerParseException {
        if (text == null || "".equals(text)) {
            return null;
        }
        Date d;
        try {
            d = dateFormat.parse(text);
            return d;
        } catch (Exception ex) {
            throw new HandlerParseException(ex.getMessage());
        }

    }

    @Override
    public String toString(Date obj) {
        if (obj == null) {
            return null;
        }
        return dateFormat.format(obj);
    }

    @Override
    public void setConfig(String[] params) {
        if (params == null || params.length == 0) {
            resetPatterns();
            return;
        }
        for (String s : params) {
            String[] param = s.split("=");
            String key = param[0].trim();
            String value;
            if (param.length > 1) {
                value = param[1];
            } else {
                value = key;
            }
            switch (key) {
                case "date":
                    dateFormat = new SimpleDateFormat(patternForDate);
                    break;
                case "time":
                    dateFormat = new SimpleDateFormat(patternForTime);
                    break;
                case "datetime":
                    dateFormat = new SimpleDateFormat(patternForDateTime);
                    break;
                case "pattern":
                    dateFormat = new SimpleDateFormat(value);
                    break;
                default:
                    dateFormat = new SimpleDateFormat(value);
            }
        }
    }

    public static void resetPatterns() {
        patternForDate = "yyyy-MM-dd";
        patternForTime = "HH:mm:ss";
        patternDefault = "yyyy-MM-dd'T'HH:mm:ss";
        patternForDateTime = DEFAULT;        
    }

    public static String getPatternDefault() {
        return patternDefault;
    }

    public static void setPatternDefault(String patternDefault) {
        DefaultDateHandler.patternDefault = patternDefault;
    }

    public static String getPatternForDate() {
        return patternForDate;
    }

    public static void setPatternForDate(String patternForDate) {
        DefaultDateHandler.patternForDate = patternForDate;
    }

    public static String getPatternForTime() {
        return patternForTime;
    }

    public static void setPatternForTime(String patternForTime) {
        DefaultDateHandler.patternForTime = patternForTime;
    }

    public static String getPatternForDateTime() {
        return patternForDateTime;
    }

    public static void setPatternForDateTime(String patternForDateTime) {
        DefaultDateHandler.patternForDateTime = patternForDateTime;
    }

}
