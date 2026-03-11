package zxf.springboot.pa.apitest.support.json;

import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

public class JSONComparatorFactory {
    public static JSONComparator buildPAResponseJSONComparator() {
        Customization timestamp = Customization.customization("timestamp",
                new RegularExpressionValueMatcher<>("[\\d T:.+-]+"));
        Customization downstream = Customization.customization("**.downstream.value",
                new RegularExpressionValueMatcher<>("\\d+"));
        Customization currentTimeMillis = Customization.customization("currentTimeMillis",
                new RegularExpressionValueMatcher<>("\\d+"));
        return new CustomComparator(JSONCompareMode.STRICT,
                timestamp, downstream, currentTimeMillis);
    }
}
