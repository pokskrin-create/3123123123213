package org.apache.tika.sax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.sax.StandardReference;

/* loaded from: classes4.dex */
public class StandardsText {
    private static final String REGEX_APPLICABLE_DOCUMENTS = "(?i:.*APPLICABLE\\sDOCUMENTS|REFERENCE|STANDARD|REQUIREMENT|GUIDELINE|COMPLIANCE.*)";
    private static final String REGEX_FALLBACK = "\\(?(?<mainOrganization>[A-Z]\\w{1,64}+)\\)?((\\s?(?<separator>\\/)\\s?)(\\w{1,64}+\\s)*\\(?(?<secondOrganization>[A-Z]\\w{1,64}+)\\)?)?(\\s(?i:Publication|Standard))?(-|\\s)?(?<identifier>([0-9]{3,64}+|([A-Z]{1,64}+(-|_|\\.)?[0-9]{2,64}+))((-|_|\\.)?[A-Z0-9]{1,64}+){0,64}+)";
    private static final String REGEX_HEADER = "(\\d{1,10}+\\.(\\d{1,10}+\\.?){0,10}+)\\p{Blank}+([A-Z]{1,64}+(\\s[A-Z]{1,64}+){0,256}+){5,10}+";
    private static final String REGEX_IDENTIFIER = "(?<identifier>([0-9]{3,64}+|([A-Z]{1,64}+(-|_|\\.)?[0-9]{2,64}+))((-|_|\\.)?[A-Z0-9]{1,64}+){0,64}+)";
    private static final String REGEX_ORGANIZATION;
    private static final String REGEX_STANDARD;
    private static final String REGEX_STANDARD_TYPE = "(\\s(?i:Publication|Standard))";

    static {
        String organzationsRegex = StandardOrganizations.getOrganzationsRegex();
        REGEX_ORGANIZATION = organzationsRegex;
        REGEX_STANDARD = ".*" + organzationsRegex + ".+" + organzationsRegex + "?.*";
    }

    public static ArrayList<StandardReference> extractStandardReferences(String str, double d) {
        return findStandards(str, findHeaders(str), d);
    }

    private static Map<Integer, String> findHeaders(String str) {
        TreeMap treeMap = new TreeMap();
        Matcher matcher = Pattern.compile(REGEX_HEADER).matcher(str);
        while (matcher.find()) {
            treeMap.put(Integer.valueOf(matcher.start()), matcher.group());
        }
        return treeMap;
    }

    private static ArrayList<StandardReference> findStandards(String str, Map<Integer, String> map, double d) {
        ArrayList<StandardReference> arrayList = new ArrayList<>();
        Matcher matcher = Pattern.compile(REGEX_FALLBACK).matcher(str);
        while (matcher.find()) {
            StandardReference.StandardReferenceBuilder secondOrganization = new StandardReference.StandardReferenceBuilder(matcher.group("mainOrganization"), matcher.group("identifier")).setSecondOrganization(matcher.group("separator"), matcher.group("secondOrganization"));
            double d2 = matcher.group().matches(REGEX_STANDARD) ? 0.5d : 0.25d;
            if (matcher.group().matches(".*(\\s(?i:Publication|Standard)).*")) {
                d2 += 0.25d;
            }
            Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
            boolean z = false;
            int i = 0;
            int i2 = 0;
            while (it.hasNext() && !z) {
                int iIntValue = it.next().getKey().intValue();
                if (iIntValue > matcher.start()) {
                    z = true;
                }
                int i3 = i2;
                i2 = iIntValue;
                i = i3;
            }
            if (map.get(Integer.valueOf(i)) != null && map.get(Integer.valueOf(i)).matches(REGEX_APPLICABLE_DOCUMENTS)) {
                d2 += 0.25d;
            }
            secondOrganization.setScore(d2);
            if (d2 >= d) {
                arrayList.add(secondOrganization.build());
            }
        }
        return arrayList;
    }
}
