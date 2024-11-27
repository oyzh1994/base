// package cn.oyzh.common.json;
//
//
// import com.google.gson.JsonArray;
// import com.google.gson.JsonElement;
//
// import java.util.Collections;
// import java.util.Iterator;
// import java.util.List;
//
// /**
//  * @author oyzh
//  * @since 2024-11-18
//  */
// public class JSONArray1 implements Iterable<JsonElement> {
//
//     private final JsonArray array;
//
//     public JSONArray1(JsonArray array) {
//         this.array = array;
//     }
//
//     public int size() {
//         return this.array.size();
//     }
//
//     public JSONObject getJSONObject(int index) {
//         JsonElement element = this.array.get(index);
//         if (element.isJsonObject()) {
//             return new JSONObject(element.getAsJsonObject());
//         }
//         return null;
//     }
//
//     public <T> List<T> toBeanList(Class<T> beanClass) {
//         try {
//             return JSONUtil.toBeanList(this.array, beanClass);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return Collections.emptyList();
//     }
//
//     @Override
//     public Iterator<JsonElement> iterator() {
//         return array.iterator();
//     }
// }
