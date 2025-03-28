package cn.oyzh.common.test;

import cn.oyzh.common.json.JSONUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JsonTest2 {

    @Test
    public void test2() {
        JsonBean1 jb = new JsonBean1();
        jb.param1 = "1";
        jb.param2 = true;
        jb.param3 = 111;
        jb.param4 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JsonBean2 jb2 = new JsonBean2();
            jb2.param1 = "jsb" + i;
            jb2.param3 = i;
            jb.param4.add(jb2);
        }
        String json = JSONUtil.toJson(jb);
        String json1 = JSONUtil.toPretty(json);
        System.out.println(json);
        System.out.println(json1);
    }

    @Test
    public void test3() {
        String xx = """
                {
                "a":1,
                "b":"2",
                "c":{
                    "c1":1,
                    "c2":"2"
                },
                "d":[
                   {
                   "d1":1
                   },
                   {
                    "d1":1
                   }  
                ]
                }
                """;
        String o = JSONUtil.toPretty(xx);
        System.out.println(o);
    }

    @Test
    public void test4() {
        String json = """
                {
                   	"param1": "1",
                   	"param2": true,
                   	"param3": 111,
                   	"param4": [
                   		{
                   			"param1": "jsb0",
                   			"param2": false,
                   			"param3": 0
                   		},
                   		{
                   			"param1": "jsb1",
                   			"param2": false,
                   			"param3": 1
                   		},
                   		{
                   			"param1": "jsb2",
                   			"param2": false,
                   			"param3": 2
                   		},
                   		{
                   			"param1": "jsb3",
                   			"param2": false,
                   			"param3": 3
                   		},
                   		{
                   			"param1": "jsb4",
                   			"param2": false,
                   			"param3": 4
                   		},
                   		{
                   			"param1": "jsb5",
                   			"param2": false,
                   			"param3": 5
                   		},
                   		{
                   			"param1": "jsb6",
                   			"param2": false,
                   			"param3": 6
                   		},
                   		{
                   			"param1": "jsb7",
                   			"param2": false,
                   			"param3": 7
                   		},
                   		{
                   			"param1": "jsb8",
                   			"param2": false,
                   			"param3": 8
                   		},
                   		{
                   			"param1": "jsb9",
                   			"param2": false,
                   			"param3": 9
                   		}
                   	]
                   }
                """;
        JsonBean1 o = JSONUtil.toBean(json, JsonBean1.class);
        System.out.println(o);
    }

    public static class JsonBean1 {
        private String param1;
        private boolean param2;
        private int param3;
        private List<JsonBean2> param4;
    }

    public static class JsonBean2 {
        private String param1;
        private boolean param2;
        private int param3;
    }
}
