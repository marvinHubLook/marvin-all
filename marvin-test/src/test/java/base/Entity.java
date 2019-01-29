package base;

import org.apache.commons.lang3.StringUtils;


public class Entity {
    private float[] floats;
    private String floatStr;

    public void setFloats(float[] floats) {
        if(null==floatStr ) this.floatStr=floatToString(floats);
        this.floats = floats;
    }

    public void setFloatStr(String floatStr) {
        if(null==floats) this.floats=parseStrToFloat(floatStr);
        this.floatStr = floatStr;
    }

    public float[] getFloats() {
        return floats;
    }

    public String getFloatStr() {
        return floatStr;
    }

    public static float[] parseStrToFloat(String str){
        if(StringUtils.isEmpty(str)) return null;
        String[] strArr = StringUtils.split(str, ',');
        float[] fs = new float[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            fs[i] = Float.parseFloat(strArr[i]);
        }
        return fs;
    }

    public static String floatToString(float[] fs) {
        if(null==fs || fs.length<1) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fs.length; i++) {
            stringBuilder.append(fs[i] + " ");
        }
        return stringBuilder.toString();
    }

}
