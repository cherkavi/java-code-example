import org.apache.commons.lang3.text.StrSubstitutor;

Map<String, String> values = new HashMap<String, String>();
values.put("value", "simple value");
StrSubstitutor sub = new StrSubstitutor(values, "%(", ")%"); // ${}
String result = sub.replace("There's an value '%(value)%' from parameter");
print result

