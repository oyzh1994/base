package cn.oyzh.common.util;

import java.util.regex.Pattern;

/**
 * 正则辅助类
 *
 * @author oyzh
 * @since 2024/7/5
 */
public class RegexHelper {

    /**
     * json正则模式
     */
    private static Pattern Json_Pattern;

    public static Pattern jsonPattern() {
        if (Json_Pattern == null) {
            String regex =
                    // 1. 字符串（最高优先级，确保包含:的字符串被完整匹配）
                    "(?<string>\"(\\\\\"|\\\\\\\\|\\\\n|\\\\r|[^\"\\\\])*\")|" +
                            // 2. 结构符号（优先级低于字符串）
                            "(?<braceOpen>\\{)|(?<braceClose>\\})|" +
                            "(?<bracketOpen>\\[)|(?<bracketClose>\\])|" +
                            "(?<comma>,)|(?<colon>:)|" +
                            // 3. 关键字和数字
                            "(?<keyword>true|false|null)|" +
                            "(?<number>-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?)";
            Json_Pattern = Pattern.compile(regex);
        }
        return Json_Pattern;
    }

    /**
     * json符号正则模式
     */
    private static Pattern Json_Symbol_Pattern;

    public static Pattern jsonSymbolPattern() {
        if (Json_Symbol_Pattern == null) {
            Json_Symbol_Pattern = Pattern.compile("[{}|\\[\\]]");
        }
        return Json_Symbol_Pattern;
    }

    /**
     * json键正则模式
     */
    private static Pattern Json_Key_Pattern;

    public static Pattern jsonKeyPattern() {
        if (Json_Key_Pattern == null) {
//            Json_Key_Pattern = Pattern.compile("\"(.*?)\"(?=\\s*:)");
            Json_Key_Pattern = Pattern.compile("\"([a-zA-Z0-9-_.]+[\\w\\s]*[\\w]+)\":");
        }
        return Json_Key_Pattern;
    }

    /**
     * json值正则模式
     */
    private static Pattern Json_Value_Pattern;

    public static Pattern jsonValuePattern() {
        if (Json_Value_Pattern == null) {
//            String regex = "(\"[^\"]*\"|\\d+\\.?\\d*|true|false|null|\\{.*?\\}|\\[.*?\\])";
//            Json_Value_Pattern = Pattern.compile(regex);
            Json_Value_Pattern = Pattern.compile("(?<=:)\\s*(\"[^\"]*\"|\\d+|true|false|\\[.*?]|\\{.*?})");
        }
        return Json_Value_Pattern;
    }

//    /**
//     * json正则模式
//     */
//    private static Pattern Json_Pattern;
//
//    public static Pattern jsonPattern() {
//        if (Json_Pattern == null) {
//            String regex = "\"([^\"]+)\":\\s*(\\[(?:(?:\"(?:\\\\.|[^\"\\\\])*\"|[0-9]+|true|false|null|\\{[^{}]*+(?:\\{[^{}]*+\\}[^{}]*+)*+\\})(?:,\\s*(?:\"(?:\\\\.|[^\"\\\\])*\"|[0-9]+|true|false|null|\\{[^{}]*+(?:\\{[^{}]*+\\}[^{}]*+)*+\\}))*)?\\]|\\{[^{}]*+(?:\\{[^{}]*+\\}[^{}]*+)*+\\}|\"(?:\\\\.|[^\"\\\\])*\"|[0-9]+|true|false|null)|([{}\\[\\],:])";
//            Json_Pattern = Pattern.compile(regex);
//        }
//        return Json_Pattern;
//    }

    /**
     * xml标签正则模式
     */
    private static Pattern Xml_Pattern;

    public static Pattern xmlPattern() {
        if (Xml_Pattern == null) {
            Xml_Pattern = Pattern.compile("<(/?[a-zA-Z0-9_\\-\\.]+)([^>]*)>");
        }
        return Xml_Pattern;
    }

    /**
     * xml注释正则模式
     */
    private static Pattern Xml_Comment_Pattern;

    public static Pattern xmlCommentPattern() {
        if (Xml_Comment_Pattern == null) {
            Xml_Comment_Pattern = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
        }
        return Xml_Comment_Pattern;
    }

    /**
     * xml属性正则模式
     */
    private static Pattern Xml_Attribute_Pattern;

    public static Pattern xmlAttributePattern() {
        if (Xml_Attribute_Pattern == null) {
            String regex = "(\\s+)([a-zA-Z_:][a-zA-Z0-9_:.-]*)(\\s*=\\s*)(?:\"([^\"]*)\"|'([^']*)')";
            Xml_Attribute_Pattern = Pattern.compile(regex);
        }
        return Xml_Attribute_Pattern;
    }

    /**
     * html标签正则模式
     */
    private static Pattern Html_Pattern;

    public static Pattern htmlPattern() {
        if (Html_Pattern == null) {
            Html_Pattern = Pattern.compile("<(/?[a-zA-Z0-9_\\-\\.]+)([^>]*)>");
        }
        return Html_Pattern;
    }

    /**
     * html注释正则模式
     */
    private static Pattern Html_Comment_Pattern;

    public static Pattern htmlCommentPattern() {
        if (Html_Comment_Pattern == null) {
            Html_Comment_Pattern = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
        }
        return Html_Comment_Pattern;
    }

    /**
     * html属性正则模式
     */
    private static Pattern Html_Attribute_Pattern;

    public static Pattern htmlAttributePattern() {
        if (Html_Attribute_Pattern == null) {
            String regex = "(\\s+)([a-zA-Z_][a-zA-Z0-9_-]*)(\\s*=\\s*)(?:\"([^\"]*)\"|'([^']*)'|([^\"'\\s>]+))";
            Html_Attribute_Pattern = Pattern.compile(regex);
        }
        return Html_Attribute_Pattern;
    }

    /**
     * yaml正则模式
     */
    private static Pattern YAML_Pattern;

    public static Pattern yamlPattern() {
        if (YAML_Pattern == null) {
            String regex = "^\\s*(#.*)|(\\s*)(([^:#\\s]+):\\s*(.*?)(?:\\s*#(.*))?)$";
            YAML_Pattern = Pattern.compile(regex, Pattern.MULTILINE);
        }
        return YAML_Pattern;
    }

    /**
     * properties正则模式
     */
    private static Pattern Properties_Pattern;

    public static Pattern propertiesPattern() {
        if (Properties_Pattern == null) {
            String regex = "^\\s*" +
                    "(#.*)|" +  // 整行注释
                    "([\\w.]+)\\s*=\\s*" +  // 键名（允许字母、数字、下划线和点号）
                    "(.*?)\\s*(?:#.*)?$" +  // 值（支持所有字符，直到行尾或注释）
                    "\\s*([#;!].*)?$";  // 行尾注释
            Properties_Pattern = Pattern.compile(regex, Pattern.MULTILINE);
        }
        return Properties_Pattern;
    }

    /**
     * css正则模式
     */
    private static Pattern CSS_Pattern;

    public static Pattern cssPattern() {
        if (CSS_Pattern == null) {
            // 按优先级排序的CSS语法正则表达式
            String regex =
                    // 1. 注释（匹配所有/*开头的注释，包括/*! ... */）
                    "(?<comment>/\\*[!\\s\\S]*?\\*/)|" +
                            // 2. 字符串（双引号/单引号，含转义）
                            "(?<string>\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*')|" +
                            // 3. @规则
                            "(?<atRule>@[a-zA-Z-]+\\s+(?:[^;{]+|\\{[\\s\\S]*?\\})*?;?)|" +
                            // 4. 选择器（严格排除注释内容）
                            "(?<selector>(?:[^/{}\"']|(?<!/)\\/[^*])*?(?<!/)\\{)|" +
                            // 5. 属性名
                            "(?<propName>[a-zA-Z-]+)\\s*:|" +
                            // 6. URL值
                            "(?<url>url\\(\\s*[\"']?[^\"')]+[\"']?\\s*\\))|" +
                            // 7. 函数值
                            "(?<function>[a-zA-Z-]+\\([^)]*\\))|" +
                            // 8. 无引号值
                            "(?<value>[^;{}'\"()\\s]+(?:\\s+[^;{}'\"()\\s]+)*)|" +
                            // 9. 块结束符
                            "(?<blockEnd>\\s*;?\\s*\\})|" +
                            // 10. 属性结束符
                            "(?<propEnd>;)";
            CSS_Pattern = Pattern.compile(regex);
        }
        return CSS_Pattern;
    }

    /**
     * java正则模式
     */
    private static Pattern JAVA_Pattern;

    public static Pattern javaPattern() {
        if (JAVA_Pattern == null) {
            String regex = "(?x)" +
                    "(\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b)|" +  // 关键字
                    "(\\b(byte|short|int|long|float|double|char|boolean)\\b)|" +  // 数据类型
                    "(\"(?:\\\\.|[^\"\\\\])*\")|" +  // 字符串
                    "('(?:\\\\.|[^'\\\\])*')|" +  // 字符
                    "(//[^\\n]*)|" +  // 单行注释
                    "(/\\*[\\s\\S]*?\\*/)|" +  // 多行注释
                    "(\\b(0[xX][0-9a-fA-F]+|0[0-7]*|[0-9]+(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?[fFdD]?|true|false|null)\\b)|" +  // 数字
                    "(\\b[a-zA-Z_$][a-zA-Z0-9_$]*\\s*\\()|" +  // 方法调用
                    "(\\b[a-zA-Z_$][a-zA-Z0-9_$]*\\b)|" +  // 标识符
                    "([+\\-*/%=<>!&|^~?:,;()\\[\\]{}])";  // 符号
            JAVA_Pattern = Pattern.compile(regex, Pattern.MULTILINE);
        }
        return JAVA_Pattern;
    }

    /**
     * python正则模式
     */
    private static Pattern PYTHON_Pattern;

    public static Pattern pythonPattern() {
        if (PYTHON_Pattern == null) {

            // Python关键字（保留字）
            String keywords = "\\b(False|None|True|and|as|assert|async|await|break|class|continue|def|del|elif|else|except|finally|for|from|global|if|import|in|is|lambda|nonlocal|not|or|pass|raise|return|try|while|with|yield)\\b";

            // 数据类型和内置函数
            String builtins = "\\b(bool|bytearray|bytes|complex|dict|float|frozenset|int|list|memoryview|object|range|set|str|tuple|type|print|input|len|open)\\b";

            // 字符串匹配（单/双引号，支持三引号）
            String strings = "(\"\"\"|'''|\"|')(?:(?!\\1).|\\n)*\\1";

            // 数字（整数/浮点数/复数）
            String numbers = "\\b\\d+\\.?\\d*(?:[eE][+-]?\\d+)?[jJ]?\\b";

            // 注释（单行和多行）
            String comments = "(#.*$|(\"\"\"|''')[\\s\\S]*?\\2)";

            // 运算符和分隔符
            String operators = "([-+*/%&|^~<>!=]=?|\\*\\*|//|<<|>>|\\b(?:in|is|not)\\b|[:;,.()\\[\\]{}])";

            // 组合最终正则表达式
            String regex = String.join("|",
                    "(?<KEYWORD>" + keywords + ")",
                    "(?<BUILTIN>" + builtins + ")",
                    "(?<STRING>" + strings + ")",
                    "(?<NUMBER>" + numbers + ")",
                    "(?<COMMENT>" + comments + ")",
                    "(?<OPERATOR>" + operators + ")"
            );
            PYTHON_Pattern = Pattern.compile(regex, Pattern.MULTILINE);
        }
        return PYTHON_Pattern;
    }

    /**
     * javascript正则模式
     */
    private static Pattern JAVASRIPT_Pattern;

    public static Pattern javascriptPattern() {
        if (JAVASRIPT_Pattern == null) {
            String regex = "(?x)" +
                    // 关键字
                    "(\\b(abstract|arguments|await|boolean|break|byte|case|catch|char|class|const|continue|debugger|default|delete|do|double|else|enum|eval|export|extends|false|final|finally|float|for|function|goto|if|implements|import|in|instanceof|int|interface|let|long|native|new|null|package|private|protected|public|return|short|static|super|switch|synchronized|this|throw|throws|transient|true|try|typeof|var|void|volatile|while|with|yield)\\b)|" +
                    // 数据类型和内置对象
                    "(\\b(Array|Boolean|Date|Error|Function|Map|Math|Number|Object|Promise|RegExp|Set|String|Symbol|TypeError|undefined|console|window|document)\\b)|" +
                    // 字符串（单引号、双引号、模板字符串）
                    "(`[^`]*`|\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*')|" +
                    // 注释
                    "(//[^\n]*|/\\*[\\s\\S]*?\\*/)|" +
                    // 数字（整数、浮点数、十六进制、二进制、八进制）
                    "(\\b(0[xX][0-9a-fA-F]+|0[bB][01]+|0[oO][0-7]+|\\d+(?:\\.\\d+)?(?:e[+-]?\\d+)?|\\d+\\.\\d*|\\.\\d+)\\b)|" +
                    // 函数定义
                    "(\\bfunction\\s+([a-zA-Z_$][a-zA-Z0-9_$]*)\\s*\\()|" +
                    // 类定义
                    "(\\bclass\\s+([a-zA-Z_$][a-zA-Z0-9_$]*)\\s*[:{])|" +
                    // 箭头函数
                    "((?:\\b|\\))\\s*\\([^)]*\\)\\s*=>)|" +
                    // 装饰器
                    "(^[ \\t]*@[a-zA-Z_$][a-zA-Z0-9_$]*\\b)|" +
                    // 对象属性访问
                    "((?:\\b[a-zA-Z_$][a-zA-Z0-9_$]*\\s*\\.\\s*)[a-zA-Z_$][a-zA-Z0-9_$]*\\b)|" +
                    // 标识符
                    "(\\b[a-zA-Z_$][a-zA-Z0-9_$]*\\b)|" +
                    // 特殊符号
                    "([+\\-*/%=<>!&|^~?:,;()\\[\\]{}])";
            JAVASRIPT_Pattern = Pattern.compile(regex, Pattern.MULTILINE);
        }
        return JAVASRIPT_Pattern;
    }
}
